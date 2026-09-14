/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.*;
import Model.Session;
import View.Kendaraan.*;
import View.Sopir.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ViewMenu extends JFrame implements ActionListener{
    // atribut
    private JLabel labelWelcome;
    private JButton tombolKendaraan;
    private JButton tombolSopir;
    private JButton tombolLogout;

    // constructor
    public ViewMenu(String username) {
        // setting frame
        setTitle("Menu Utama");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // =========================
        // PANEL JUDUL
        // =========================
        JPanel panelAtas = new JPanel();
        panelAtas.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelAtas.setLayout(new GridLayout(2,1));

        JLabel labelJudul = new JLabel("Sistem Manajemen Transportasi");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 22));
        labelJudul.setHorizontalAlignment(SwingConstants.CENTER);

        labelWelcome = new JLabel("Selamat Datang, " + Session.username + "!");
        labelWelcome.setFont(new Font("Arial", Font.PLAIN, 16));
        labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);

        panelAtas.add(labelJudul);
        panelAtas.add(labelWelcome);

        add(panelAtas, BorderLayout.NORTH);

        // =========================
        // PANEL MENU
        // =========================
        JPanel panelMenu = new JPanel();
        panelMenu.setBorder(new EmptyBorder(20, 50, 20, 50));
        panelMenu.setLayout(new GridLayout(3,1,15,15));

        tombolKendaraan = new JButton("Data Kendaraan");
        tombolSopir = new JButton("Data Sopir");
        tombolLogout = new JButton("Logout");

        tombolKendaraan.setFont(new Font("Arial", Font.BOLD, 14));
        tombolSopir.setFont(new Font("Arial", Font.BOLD, 14));
        tombolLogout.setFont(new Font("Arial", Font.BOLD, 14));

        panelMenu.add(tombolKendaraan);
        panelMenu.add(tombolSopir);
        panelMenu.add(tombolLogout);

        add(panelMenu, BorderLayout.CENTER);

        // action listener
        tombolKendaraan.addActionListener(this);
        tombolSopir.addActionListener(this);
        tombolLogout.addActionListener(this);

        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // tombol kendaraan
        if(e.getSource() == tombolKendaraan) {
            JOptionPane.showMessageDialog(null, "Menu Kendaraan Dibuka");
        
            dispose();
            new ViewKendaraan();
        }
        // tombol sopir
        else if(e.getSource() == tombolSopir) {
            JOptionPane.showMessageDialog(null, "Menu Sopir Dibuka");

            dispose();
            new ViewSopir();
        }
        // tombol logout
        else if(e.getSource() == tombolLogout) {

            int pilih = JOptionPane.showConfirmDialog(null, "Yakin ingin logout?", "Konfirmasi Logout",JOptionPane.YES_NO_OPTION);

            if(pilih == JOptionPane.YES_OPTION) {
                Session.username = null;
                dispose();
                new ViewLogin();
            }
        }
    }
}
