package Controller;

import Model.Loan.*;
import Model.Member.*;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ControllerMember {

    private final InterfaceDAOMember daoMember = new DAOMember();
    private final InterfaceDAOLoan   daoLoan   = new DAOLoan();

    public void loadAll(JTable table) {
        new Thread(() -> {
            try {
                List<ModelMember> list = daoMember.getAll();
                ModelTableMember model = new ModelTableMember(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat member: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void insert(String name, String email, String password, Runnable onSuccess) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new Thread(() -> {
            try {
                daoMember.insert(new ModelMember(name.trim(), email.trim(), password.trim()));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Member berhasil didaftarkan!");
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

    public void update(int id, String name, String email, String password, String status, Runnable onSuccess) {
        if (name.isBlank() || email.isBlank()) {
            JOptionPane.showMessageDialog(null, "Nama dan email tidak boleh kosong!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        new Thread(() -> {
            try {
                daoMember.update(new ModelMember(id, name.trim(), email.trim(), password.trim(), status));
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Member berhasil diupdate!");
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

    public void delete(int id, String name, Runnable onSuccess) {
        int opt = JOptionPane.showConfirmDialog(null,
            "Hapus member \"" + name + "\"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;
        new Thread(() -> {
            try {
                daoMember.delete(id);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Member dihapus!");
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

    /** Load loan detail per member ke tabel */
    public void loadLoanByMember(int idMember, JTable table) {
        new Thread(() -> {
            try {
                List<ModelLoan> list = daoLoan.getByMember(idMember);
                ModelTableLoan model = new ModelTableLoan(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat riwayat: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }
}
