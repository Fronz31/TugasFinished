package Controller;

import Model.Book.*;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ControllerBook {

    private final InterfaceDAOBook dao = new DAOBook();

    /** Load buku per rack secara asinkron */
    public void loadByRack(int idRack, JTable table) {
        new Thread(() -> {
            try {
                List<ModelBook> list = dao.getByRack(idRack);
                ModelTableBook  model = new ModelTableBook(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat buku: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    /** Search global lintas rack */
    public void searchGlobal(String keyword, JTable table) {
        new Thread(() -> {
            try {
                List<ModelBook> list = dao.searchGlobal(keyword);
                // Gunakan ModelTableBookGlobal yang menampilkan kolom RackID
                ModelTableBookGlobal model = new ModelTableBookGlobal(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal search: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void insert(String title, String author, String yearStr, String stockStr,
                       int idRack, Runnable onSuccess) {
        try {
            int year  = validateYear(yearStr);
            int stock = validateStock(stockStr);
            if (title.isBlank())  throw new Exception("Judul tidak boleh kosong!");
            if (author.isBlank()) throw new Exception("Pengarang tidak boleh kosong!");

            new Thread(() -> {
                try {
                    dao.insert(new ModelBook(title.trim(), author.trim(), year, stock, idRack));
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Buku berhasil ditambahkan!");
                        onSuccess.run();
                    });
                } catch (SQLException ex) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null,
                            "Gagal simpan: " + ex.getMessage(),
                            "DB Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void update(int idBook, String title, String author, String yearStr, String stockStr,
                       int idRack, Runnable onSuccess) {
        try {
            int year  = validateYear(yearStr);
            int stock = validateStock(stockStr);
            if (title.isBlank())  throw new Exception("Judul tidak boleh kosong!");
            if (author.isBlank()) throw new Exception("Pengarang tidak boleh kosong!");

            new Thread(() -> {
                try {
                    dao.update(new ModelBook(idBook, title.trim(), author.trim(), year, stock, idRack));
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "Buku berhasil diupdate!");
                        onSuccess.run();
                    });
                } catch (SQLException ex) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null,
                            "Gagal update: " + ex.getMessage(),
                            "DB Error", JOptionPane.ERROR_MESSAGE));
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validasi", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void delete(int idBook, String judul, Runnable onSuccess) {
        int opt = JOptionPane.showConfirmDialog(null,
            "Hapus buku \"" + judul + "\"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        new Thread(() -> {
            try {
                dao.delete(idBook);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Buku berhasil dihapus!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal hapus: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    private int validateYear(String s) throws Exception {
        try {
            int y = Integer.parseInt(s.trim());
            if (y < 1000 || y > 9999) throw new Exception("Tahun tidak valid!");
            return y;
        } catch (NumberFormatException e) { throw new Exception("Tahun harus angka!"); }
    }

    private int validateStock(String s) throws Exception {
        try {
            int st = Integer.parseInt(s.trim());
            if (st < 0) throw new Exception("Stok tidak boleh negatif!");
            return st;
        } catch (NumberFormatException e) { throw new Exception("Stok harus angka!"); }
    }
}
