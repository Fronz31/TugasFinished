package Controller;

import Model.Employee.*;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ControllerEmployee {

    private final InterfaceDAOEmployee dao = new DAOEmployee();

    public void loadAll(JTable table) {
        new Thread(() -> {
            try {
                List<ModelEmployee> list = dao.getAll();
                ModelTableEmployee  model = new ModelTableEmployee(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat pegawai: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void insert(String username, String password, String role, Runnable onSuccess) {
        if (!validate(username, password, role)) return;
        new Thread(() -> {
            try {
                dao.insert(new ModelEmployee(0, username.trim(), password.trim(), role.trim()));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Pegawai berhasil ditambahkan!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal simpan: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void update(int id, String username, String password, String role, Runnable onSuccess) {
        if (username.isBlank() || role.isBlank()) {
            JOptionPane.showMessageDialog(null, "Username dan role tidak boleh kosong!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new Thread(() -> {
            try {
                dao.update(new ModelEmployee(id, username.trim(), password.trim(), role.trim()));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Pegawai berhasil diupdate!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal update: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void delete(int id, String username, Runnable onSuccess) {
        int opt = JOptionPane.showConfirmDialog(null,
            "Hapus pegawai \"" + username + "\"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        new Thread(() -> {
            try {
                dao.delete(id);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Pegawai berhasil dihapus!");
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

    private boolean validate(String username, String password, String role) {
        if (username.isBlank() || password.isBlank() || role.isBlank()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
