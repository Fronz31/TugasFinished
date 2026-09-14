/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.ControllerUser;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class ViewLogin extends JFrame implements ActionListener{
    //Atribut Class untuk Frame Login
    private JTextField inputNama;
    private JPasswordField inputPassword;
    private JButton tombolLogin;
    
    ControllerUser controller;
    
    public ViewLogin(){
        // memanggil controller
        controller = new ControllerUser(this);
        
        //Set Frame (Jendela Layar)
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        //============================
        //Set Layout dan login form
        //============================
        
        //Panel Selamat Datang
        setLayout(new BorderLayout());
        
        JLabel labelMasuk = new JLabel("Sistem Login");
        labelMasuk.setFont(new Font("Arial", Font.BOLD, 20));
        labelMasuk.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelJudul = new JPanel();
        panelJudul.setLayout(new GridLayout(2, 1));
        panelJudul.add(labelMasuk);
        add(panelJudul, BorderLayout.NORTH);
        
        //Panel Form
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridLayout(0, 1, 10, 10));
        panelForm.setBorder(new EmptyBorder(5, 20, 20, 20));
        add(panelForm, BorderLayout.CENTER);
        
        //Input Nama dan Password
        panelForm.add(new JLabel("Username"));
        inputNama = new JTextField();
        panelForm.add(inputNama);
        
        panelForm.add(new JLabel("Password"));
        inputPassword = new JPasswordField();
        panelForm.add(inputPassword);
        
        //Button Login
        tombolLogin = new JButton("Login");
        panelForm.add(tombolLogin);
        tombolLogin.addActionListener(this);
    
        setVisible(true);
    }
    
    // getter username
    public String getInputUsername() {
        return inputNama.getText();
    }

    // getter password
    public String getInputPassword() {
        return String.valueOf(inputPassword.getPassword());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == tombolLogin){
            controller.login();
        }
    }
}
