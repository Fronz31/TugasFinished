package Model;

import java.sql.*;

public class Connector {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NAMA_DB     = "manajemen_perpustakaan_db";
    private static final String URL_DB      = "jdbc:mysql://localhost:3307/" + NAMA_DB
                                              + "?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME_DB = "root";
    private static final String PASSWORD_DB = "";

    public static Connection connection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver tidak ditemukan: " + e.getMessage());
        }
        return DriverManager.getConnection(URL_DB, USERNAME_DB, PASSWORD_DB);
    }
}
