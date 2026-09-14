/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Session;
import Model.Users.*;
import View.*;
import javax.swing.JOptionPane;

public class ControllerUser {
    ViewLogin login;
    InterfaceDAOUsers daoUsers;

    public ControllerUser(ViewLogin login) {
        this.login = login;
        this.daoUsers = new DAOUsers();
    }

    public void login() {
        try {
            String username = login.getInputUsername();
            String password = login.getInputPassword();

            if ("".equals(username) || "".equals(password)) {
                throw new Exception(
                "Username atau Password tidak boleh kosong!"
                );
            }

            boolean status = daoUsers.login(username, password);

            if (status) {
                JOptionPane.showMessageDialog(null, "Login Berhasil");
                login.dispose();
                
                Session.username = username;
                new ViewMenu(Session.username);

            } else {
                JOptionPane.showMessageDialog(null,"Username atau Password salah!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
}
