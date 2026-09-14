/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Kendaraan;

import Controller.*;
import Model.Kendaraan.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EditKendaraan extends JFrame{
    ControllerKendaraan controller;
    
    JLabel header = new JLabel("Input Kendaraan");
    JLabel labelInputPlat = new JLabel("Plat");
    JLabel labelInputJenis = new JLabel("Jenis");
    JLabel labelInputMerk = new JLabel("Merk");
    JTextField inputPlat = new JTextField();
    JTextField inputJenis = new JTextField();
    JTextField inputMerk = new JTextField();
    JButton tombolEdit = new JButton("Edit");
    JButton tombolKembali = new JButton("Kembali");
    
    public EditKendaraan(ModelKendaraan kendaraan) {
        setTitle("Input Kendaraan");
        setLayout(null);
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        add(header);
        add(labelInputPlat);
        add(labelInputJenis);
        add(labelInputMerk);
        add(inputPlat);
        add(inputJenis);
        add(inputMerk);
        add(tombolEdit);
        add(tombolKembali);

        header.setBounds(20, 10, 440, 30);

        // plat nomor
        labelInputPlat.setBounds(20, 50, 440, 20);
        inputPlat.setBounds(20, 75, 440, 35);
        // jenis kendaraan
        labelInputJenis.setBounds(20, 120, 440, 20);
        inputJenis.setBounds(20, 145, 440, 35);
        // merk kendaraan
        labelInputMerk.setBounds(20, 190, 440, 20);
        inputMerk.setBounds(20, 215, 440, 35);
        // tombol
        tombolKembali.setBounds(20, 265, 215, 35);
        tombolEdit.setBounds(245, 265, 215, 35);        
        // Masukkin Plat, Jenis, dan Merk yang didapat dari halaman sebelumnya.
        inputPlat.setText(kendaraan.getPlat());
        inputJenis.setText(kendaraan.getJenis());
        inputMerk.setText(kendaraan.getMerk());
        
        controller = new ControllerKendaraan(this);

        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewKendaraan();
            }
        });
        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.editKendaraan(kendaraan.getidKendaraan());
            }
        });
    }
    
    public String getInputPlat() {
        return inputPlat.getText();
    }
    public String getInputJenis() {
        return inputJenis.getText();
    }
    public String getInputMerk() {
        return inputMerk.getText();
    }
}
