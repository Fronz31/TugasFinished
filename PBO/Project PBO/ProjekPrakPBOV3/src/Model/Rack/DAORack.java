package Model.Rack;

import Model.Connector;
import java.sql.*;
import java.util.*;

public class DAORack implements InterfaceDAORack {

    @Override
    public void insert(ModelRack r) throws SQLException {
        String q = "INSERT INTO rack (rack_name, category, location) VALUES (?, ?, ?)";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, r.getRackName());
            s.setString(2, r.getCategory());
            s.setString(3, r.getLocation());
            s.executeUpdate();
        }
    }

    @Override
    public void update(ModelRack r) throws SQLException {
        String q = "UPDATE rack SET rack_name = ?, category = ?, location = ? WHERE id_rack = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, r.getRackName());
            s.setString(2, r.getCategory());
            s.setString(3, r.getLocation());
            s.setInt(4, r.getIdRack());
            s.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String q = "DELETE FROM rack WHERE id_rack = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }

    @Override
    public List<ModelRack> getAll() throws SQLException {
        List<ModelRack> list = new ArrayList<>();
        String q = "SELECT * FROM rack";
        try (Connection c = Connector.connection();
             PreparedStatement s = c.prepareStatement(q);
             ResultSet rs = s.executeQuery()) {
            while (rs.next()) list.add(new ModelRack(
                rs.getInt("id_rack"), rs.getString("rack_name"),
                rs.getString("category"), rs.getString("location")));
        }
        return list;
    }
}
