package Model.Member;

import Model.Connector;
import java.sql.*;
import java.util.*;

public class DAOMember implements InterfaceDAOMember {

    @Override
    public ModelMember login(String email, String password) throws SQLException {
        String q = "SELECT * FROM member WHERE email = ? AND password = ? AND status = 'aktif'";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, email);
            s.setString(2, password);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public void insert(ModelMember m) throws SQLException {
        String q = "INSERT INTO member (name, email, password, status) VALUES (?, ?, ?, 'aktif')";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, m.getName());
            s.setString(2, m.getEmail());
            s.setString(3, m.getPassword());
            s.executeUpdate();
        }
    }

    @Override
    public void update(ModelMember m) throws SQLException {
        // Jika password kosong, tidak ikut diupdate
        String q = m.getPassword() == null || m.getPassword().isBlank()
            ? "UPDATE member SET name = ?, email = ?, status = ? WHERE id_member = ?"
            : "UPDATE member SET name = ?, email = ?, password = ?, status = ? WHERE id_member = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            if (m.getPassword() == null || m.getPassword().isBlank()) {
                s.setString(1, m.getName());
                s.setString(2, m.getEmail());
                s.setString(3, m.getStatus());
                s.setInt(4, m.getIdMember());
            } else {
                s.setString(1, m.getName());
                s.setString(2, m.getEmail());
                s.setString(3, m.getPassword());
                s.setString(4, m.getStatus());
                s.setInt(5, m.getIdMember());
            }
            s.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String q = "DELETE FROM member WHERE id_member = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }

    @Override
    public void setNonAktif(int idMember) throws SQLException {
        String q = "UPDATE member SET status = 'non-aktif' WHERE id_member = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, idMember);
            s.executeUpdate();
        }
    }

    @Override
    public List<ModelMember> getAll() throws SQLException {
        List<ModelMember> list = new ArrayList<>();
        String q = "SELECT * FROM member";
        try (Connection c = Connector.connection();
             PreparedStatement s = c.prepareStatement(q);
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    private ModelMember mapRow(ResultSet rs) throws SQLException {
        return new ModelMember(
            rs.getInt("id_member"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("status")
        );
    }
}
