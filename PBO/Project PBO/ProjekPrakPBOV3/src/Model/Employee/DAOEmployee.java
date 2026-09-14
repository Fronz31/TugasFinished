package Model.Employee;

import Model.Connector;
import java.sql.*;
import java.util.*;

public class DAOEmployee implements InterfaceDAOEmployee {

    @Override
    public ModelEmployee login(String username, String password) throws SQLException {
        String q = "SELECT * FROM employee WHERE username = ? AND password = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, username);
            s.setString(2, password);
            try (ResultSet rs = s.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    @Override
    public void insert(ModelEmployee e) throws SQLException {
        String q = "INSERT INTO employee (username, password, role) VALUES (?, ?, ?)";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, e.getUsername());
            s.setString(2, e.getPassword());
            s.setString(3, e.getRole());
            s.executeUpdate();
        }
    }

    @Override
    public void update(ModelEmployee e) throws SQLException {
        // Jika password kosong, tidak ikut diupdate
        String q = e.getPassword() == null || e.getPassword().isBlank()
            ? "UPDATE employee SET username = ?, role = ? WHERE id_employee = ?"
            : "UPDATE employee SET username = ?, password = ?, role = ? WHERE id_employee = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            if (e.getPassword() == null || e.getPassword().isBlank()) {
                s.setString(1, e.getUsername());
                s.setString(2, e.getRole());
                s.setInt(3, e.getIdEmployee());
            } else {
                s.setString(1, e.getUsername());
                s.setString(2, e.getPassword());
                s.setString(3, e.getRole());
                s.setInt(4, e.getIdEmployee());
            }
            s.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String q = "DELETE FROM employee WHERE id_employee = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }

    @Override
    public List<ModelEmployee> getAll() throws SQLException {
        List<ModelEmployee> list = new ArrayList<>();
        String q = "SELECT * FROM employee";
        try (Connection c = Connector.connection();
             PreparedStatement s = c.prepareStatement(q);
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    private ModelEmployee mapRow(ResultSet rs) throws SQLException {
        return new ModelEmployee(
            rs.getInt("id_employee"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("role")
        );
    }
}
