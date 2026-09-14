/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Kendaraan;

import Controller.*;
import Model.Session;
import Model.Kendaraan.*;
import View.ViewMenu;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewKendaraan extends JFrame{
    Integer baris;
    // Membuat sebuah instance bernama controller dari class "ControllerMahasiswa".
    ControllerKendaraan controller;
    // Menginisiasi komponen
    JLabel header = new JLabel("Kelola Data Kendaraan");
    JButton tombolTambah = new JButton("Tambah");
    JButton tombolEdit = new JButton("Edit");
    JButton tombolHapus = new JButton("Hapus");
    JButton tombolKembali = new JButton("Kembali");
    JTextField inputCari = new JTextField();
    JButton tombolCari = new JButton("Cari");

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;
    String namaKolom[] = {"ID", "Plat Nomor", "Jenis", "Merk"};

    public ViewKendaraan() {
        tableModel = new DefaultTableModel(namaKolom, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setTitle("Table Kendaraan!");
        setLayout(null);
        setSize(552, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        add(header);
        add(scrollPane);
        add(tombolTambah);
        add(tombolEdit);
        add(tombolHapus);
        add(tombolKembali);
        add(inputCari);
        add(tombolCari);

        header.setBounds(20, 8, 440, 24);
        scrollPane.setBounds(20, 36, 512, 320);
        inputCari.setBounds(20, 370, 390, 40);

        tombolTambah.setBounds(20, 420, 120, 40);
        tombolEdit.setBounds(150, 420, 120, 40);
        tombolHapus.setBounds(280, 420, 120, 40);
        tombolKembali.setBounds(410, 420, 120, 40);
        tombolCari.setBounds(420, 370, 110, 40);

        controller = new ControllerKendaraan(this);
        controller.showAllKendaraan();

        // Menambahkan event handling ketika salah satu baris di tabel dipilih
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);              
                // Mengambil baris ke-n dari tabel
                baris = table.getSelectedRow();
            }
        });
        // Memberikan event handling ketika tombol "Tambah Mahasiswa" diklik
        tombolTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ketika tombol tambah diklik, maka program akan berpindah ke halaman InputKendaraan()
                dispose();
                new InputKendaraan();
            }
        });
        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewMenu(Session.username);
            }
        });
        // Memberikan event handling ketika tombol "Edit Mahasiswa" diklik
        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {

                    ModelKendaraan kendaraanTerpilih = new ModelKendaraan();          
                    // Mengambil id dan nama berdasarkan baris yang dipilih
                    Integer id = (int) table.getValueAt(baris, 0);
                    String plat = table.getValueAt(baris, 1).toString();
                    String jenis = table.getValueAt(baris, 2).toString();
                    String merk = table.getValueAt(baris, 3).toString();                   
                    // Menyimpan informasi id, nama, dan nim ke objek "mahasiswaTerpilih".
                    kendaraanTerpilih.setidKendaraan(id);
                    kendaraanTerpilih.setPlat(plat);
                    kendaraanTerpilih.setJenis(jenis);
                    kendaraanTerpilih.setMerk(merk);

                    dispose();
                    new EditKendaraan(kendaraanTerpilih);
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });
        // Memberikan event handling ketika tombol "Hapus Mahasiswa" diklik
        tombolHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {
                    controller.deleteKendaraan(baris);
                    baris = null;
                } else {
                    JOptionPane.showMessageDialog(null, "Data belum dipilih.");
                }
            }
        });
        tombolCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchKendaraan();
            }
        });
    }
    
    public JTable getTableKendaraan() {
        return table;
    }
    public String getInputCari() {
        return inputCari.getText();
    }
}