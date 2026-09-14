/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Sopir;

import Controller.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class InputSopir extends JFrame{
    // Membuat sebuah instance bernama controller dari class "ControllerMahasiswa".
    ControllerSopir controller;

    JLabel header = new JLabel("Input Sopir");
    JLabel labelInputNama = new JLabel("Nama Sopir");
    JLabel labelInputSIM = new JLabel("No SIM");
    JLabel labelInputHP = new JLabel("No HP");
    JTextField inputNama = new JTextField();
    JTextField inputSIM = new JTextField();
    JTextField inputHP = new JTextField();
    JButton tombolTambah = new JButton("Tambah");
    JButton tombolKembali = new JButton("Kembali");

    public InputSopir() {
        setTitle("Input Sopir");
        setLayout(null);
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        add(header);
        add(labelInputNama);
        add(labelInputSIM);
        add(labelInputHP);
        add(inputNama);
        add(inputSIM);
        add(inputHP);
        add(tombolTambah);
        add(tombolKembali);

        header.setBounds(20, 10, 440, 30);

        // nama sopir
        labelInputNama.setBounds(20, 50, 440, 20);
        inputNama.setBounds(20, 75, 440, 35);

        // no sim
        labelInputSIM.setBounds(20, 120, 440, 20);
        inputSIM.setBounds(20, 145, 440, 35);

        // no hp
        labelInputHP.setBounds(20, 190, 440, 20);
        inputHP.setBounds(20, 215, 440, 35);

        // tombol
        tombolKembali.setBounds(20, 265, 215, 35);
        tombolTambah.setBounds(245, 265, 215, 35);
        
        controller = new ControllerSopir(this);

        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewSopir();
            }
        });

        tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.insertSopir();
            }
        });
    }

    public String getInputNama() {
        return inputNama.getText();
    }
    public String getInputSIM() {
        return inputSIM.getText();
    }
    public String getInputHP() {
        return inputHP.getText();
    }
}
