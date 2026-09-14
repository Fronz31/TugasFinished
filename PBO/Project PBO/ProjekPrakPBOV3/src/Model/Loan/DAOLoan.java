package Model.Loan;

import Model.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOLoan implements InterfaceDAOLoan {

    @Override
    public void pinjam(int idMember, int idBook) throws SQLException {
        Connection conn = null;
        try {
            conn = Connector.connection();
            conn.setAutoCommit(false);

            // Kurangi stok
            String qStok = "UPDATE book SET stock = stock - 1 WHERE id_book = ? AND stock > 0";
            try (PreparedStatement s = conn.prepareStatement(qStok)) {
                s.setInt(1, idBook);
                int updated = s.executeUpdate();
                if (updated == 0) throw new SQLException("Stok buku habis!");
            }

            // Insert loan
            String qLoan = "INSERT INTO loan (id_member, id_book, tanggal_pinjam) VALUES (?, ?, ?)";
            try (PreparedStatement s = conn.prepareStatement(qLoan)) {
                s.setInt(1, idMember);
                s.setInt(2, idBook);
                s.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                s.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public void kembalikan(int idLoan, int idBook) throws SQLException {
        Connection conn = null;
        try {
            conn = Connector.connection();
            conn.setAutoCommit(false);

            // Update tanggal kembali di loan
            String qLoan = "UPDATE loan SET tanggal_kembali = ? WHERE id_loan = ?";
            try (PreparedStatement s = conn.prepareStatement(qLoan)) {
                s.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                s.setInt(2, idLoan);
                s.executeUpdate();
            }

            // Tambah stok kembali
            String qStok = "UPDATE book SET stock = stock + 1 WHERE id_book = ?";
            try (PreparedStatement s = conn.prepareStatement(qStok)) {
                s.setInt(1, idBook);
                s.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    @Override
    public List<ModelLoan> getByMember(int idMember) throws SQLException {
        List<ModelLoan> list = new ArrayList<>();
        String q = "SELECT l.*, b.title, b.author FROM loan l "
                 + "JOIN book b ON l.id_book = b.id_book "
                 + "WHERE l.id_member = ? ORDER BY l.tanggal_pinjam DESC";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, idMember);
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    @Override
    public List<ModelLoan> getAllAktif() throws SQLException {
        List<ModelLoan> list = new ArrayList<>();
        String q = "SELECT l.*, b.title, b.author FROM loan l "
                 + "JOIN book b ON l.id_book = b.id_book "
                 + "WHERE l.tanggal_kembali IS NULL ORDER BY l.tanggal_pinjam DESC";
        try (Connection c = Connector.connection();
             PreparedStatement s = c.prepareStatement(q);
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    @Override
    public int countAktifByMember(int idMember) throws SQLException {
        String q = "SELECT COUNT(*) FROM loan WHERE id_member = ? AND tanggal_kembali IS NULL";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, idMember);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return 0;
    }

    private ModelLoan mapRow(ResultSet rs) throws SQLException {
        java.sql.Date tKembali = rs.getDate("tanggal_kembali");
        return new ModelLoan(
            rs.getInt("id_loan"),
            rs.getInt("id_member"),
            rs.getInt("id_book"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getDate("tanggal_pinjam").toLocalDate(),
            tKembali != null ? tKembali.toLocalDate() : null
        );
    }
}
