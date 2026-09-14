package Controller;

import Model.Rack.*;
import View.Employee.rack.*;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ControllerRack {

    private final InterfaceDAORack dao = new DAORack();

    /** Load semua rack ke tabel secara asinkron (Multithread) */
    public void loadAll(JTable table, Runnable onSorterReady) {
        new Thread(() -> {
            try {
                List<ModelRack> list = dao.getAll();
                ModelTableRack model = new ModelTableRack(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    TableRowSorter<ModelTableRack> sorter = new TableRowSorter<>(model);
                    table.setRowSorter(sorter);
                    if (onSorterReady != null) onSorterReady.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat rack: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void insert(String rackName, String category, String location, Runnable onSuccess) {
        if (!validateFields(rackName, category, location)) return;
        new Thread(() -> {
            try {
                dao.insert(new ModelRack(rackName.trim(), category.trim(), location.trim()));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Rak berhasil ditambahkan!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal menyimpan rak: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void update(int id, String rackName, String category, String location, Runnable onSuccess) {
        if (!validateFields(rackName, category, location)) return;
        new Thread(() -> {
            try {
                dao.update(new ModelRack(id, rackName.trim(), category.trim(), location.trim()));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Rak berhasil diupdate!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal update rak: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void delete(int id, String rackName, Runnable onSuccess) {
        int opt = JOptionPane.showConfirmDialog(null,
            "Hapus rak \"" + rackName + "\"?\nSemua buku di dalamnya akan terhapus!",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        new Thread(() -> {
            try {
                dao.delete(id);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Rak berhasil dihapus!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal hapus rak: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    private boolean validateFields(String... fields) {
        for (String f : fields) {
            if (f == null || f.isBlank()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
