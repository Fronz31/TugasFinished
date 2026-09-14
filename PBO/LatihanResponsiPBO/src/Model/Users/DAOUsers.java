/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Users;

import Model.Connector;
import java.sql.*;

public class DAOUsers implements InterfaceDAOUsers{
    Connection conn;
    public DAOUsers() {
        conn = Connector.Connect();
    }

    @Override
    public boolean login(String username, String password) {
        try {
            // validasi input kosong
            if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {

                throw new IllegalArgumentException("Username dan password tidak boleh kosong");
            }
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

             if (rs.next()) {
                rs.close();
                stmt.close();
                return true;
             } else {
                throw new IllegalArgumentException("Username atau password salah");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
