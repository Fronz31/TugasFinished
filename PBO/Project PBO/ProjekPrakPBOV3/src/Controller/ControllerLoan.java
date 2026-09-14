package Controller;

import Model.Loan.*;

import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class ControllerLoan {

    private static final int MAX_PINJAM = 3;
    private final InterfaceDAOLoan dao = new DAOLoan();

    public void pinjam(int idMember, int idBook, String judulBook, Runnable onSuccess) {
        new Thread(() -> {
            try {
                int aktif = dao.countAktifByMember(idMember);
                if (aktif >= MAX_PINJAM) {
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null,
                            "Batas maksimal peminjaman (" + MAX_PINJAM + " buku) telah tercapai!\n"
                            + "Kembalikan buku terlebih dahulu.",
                            "Batas Pinjam", JOptionPane.WARNING_MESSAGE));
                    return;
                }

                int opt = JOptionPane.showConfirmDialog(null,
                    "Pinjam buku \"" + judulBook + "\"?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (opt != JOptionPane.YES_OPTION) return;

                dao.pinjam(idMember, idBook);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Buku berhasil dipinjam!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal meminjam buku: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    public void kembalikan(int idLoan, int idBook, String judulBook, Runnable onSuccess) {
        int opt = JOptionPane.showConfirmDialog(null,
            "Kembalikan buku \"" + judulBook + "\"?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        new Thread(() -> {
            try {
                dao.kembalikan(idLoan, idBook);
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null, "Buku berhasil dikembalikan!");
                    onSuccess.run();
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal mengembalikan: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    /** Load semua loan aktif (untuk tampilan employee - semua member) */
    public void loadAllAktif(JTable table) {
        new Thread(() -> {
            try {
                List<ModelLoan> list = dao.getAllAktif();
                ModelTableLoan model = new ModelTableLoan(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat data: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }

    /** Load loan milik satu member (untuk tampilan member) */
    public void loadByMember(int idMember, JTable table) {
        new Thread(() -> {
            try {
                List<ModelLoan> list = dao.getByMember(idMember);
                ModelTableLoan model = new ModelTableLoan(list);
                SwingUtilities.invokeLater(() -> {
                    table.setModel(model);
                    table.setRowSorter(new TableRowSorter<>(model));
                });
            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(null,
                        "Gagal memuat: " + ex.getMessage(),
                        "DB Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }
}
