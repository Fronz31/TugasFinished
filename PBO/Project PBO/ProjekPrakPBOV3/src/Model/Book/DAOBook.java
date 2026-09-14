package Model.Book;

import Model.Connector;
import java.sql.*;
import java.util.*;

public class DAOBook implements InterfaceDAOBook {

    @Override
    public void insert(ModelBook b) throws SQLException {
        String q = "INSERT INTO book (title, author, year, stock, id_rack) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, b.getTitle());
            s.setString(2, b.getAuthor());
            s.setInt(3, b.getYear());
            s.setInt(4, b.getStock());
            s.setInt(5, b.getIdRack());
            s.executeUpdate();
        }
    }

    @Override
    public void update(ModelBook b) throws SQLException {
        String q = "UPDATE book SET title = ?, author = ?, year = ?, stock = ? WHERE id_book = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setString(1, b.getTitle());
            s.setString(2, b.getAuthor());
            s.setInt(3, b.getYear());
            s.setInt(4, b.getStock());
            s.setInt(5, b.getIdBook());
            s.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String q = "DELETE FROM book WHERE id_book = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, id);
            s.executeUpdate();
        }
    }

    @Override
    public List<ModelBook> getAll() throws SQLException {
        return getByRack(-1);
    }

    @Override
    public List<ModelBook> getByRack(int idRack) throws SQLException {
        List<ModelBook> list = new ArrayList<>();
        String q = "SELECT * FROM book WHERE id_rack = ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            s.setInt(1, idRack);
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs, false));
            }
        }
        return list;
    }

    @Override
    public List<ModelBook> searchGlobal(String keyword) throws SQLException {
        List<ModelBook> list = new ArrayList<>();
        String q = "SELECT b.*, r.rack_name FROM book b JOIN rack r ON b.id_rack = r.id_rack "
                 + "WHERE b.title LIKE ? OR b.author LIKE ?";
        try (Connection c = Connector.connection(); PreparedStatement s = c.prepareStatement(q)) {
            String kw = "%" + keyword + "%";
            s.setString(1, kw);
            s.setString(2, kw);
            try (ResultSet rs = s.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs, true));
            }
        }
        return list;
    }

    private ModelBook mapRow(ResultSet rs, boolean withRackName) throws SQLException {
        ModelBook b = new ModelBook(
            rs.getInt("id_book"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getInt("year"),
            rs.getInt("stock"),
            rs.getInt("id_rack")
        );
        if (withRackName) {
            b.setRackName(rs.getString("rack_name"));
        }
        return b;
    }
}
