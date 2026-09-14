/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Sopir;

import Controller.*;
import Model.Session;
import Model.Sopir.*;
import View.ViewMenu;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewSopir extends JFrame{
    Integer baris;

    // Membuat sebuah instance bernama controller dari class "ControllerMahasiswa".
    ControllerSopir controller;

    // Menginisiasi komponen
    JLabel header = new JLabel("Kelola Data Sopir");
    JButton tombolTambah = new JButton("Tambah");
    JButton tombolEdit = new JButton("Edit");
    JButton tombolHapus = new JButton("Hapus");
    JButton tombolKembali = new JButton("Kembali");
    JTextField inputCari = new JTextField();
    JButton tombolCari = new JButton("Cari");

    JTable table;
    DefaultTableModel tableModel;
    JScrollPane scrollPane;

    String namaKolom[] = {"ID", "Nama Sopir", "No SIM", "No HP"};

    public ViewSopir() {
        tableModel = new DefaultTableModel(namaKolom, 0);
        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);

        setTitle("Table Sopir");
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
        scrollPane.setBounds(20, 36, 512, 320);
        inputCari.setBounds(20, 370, 390, 40);
        
        tombolCari.setBounds(420, 370, 110, 40);
        tombolTambah.setBounds(20, 420, 120, 40);
        tombolEdit.setBounds(150, 420, 120, 40);
        tombolHapus.setBounds(280, 420, 120, 40);
        tombolKembali.setBounds(410, 420, 120, 40);

        controller = new ControllerSopir(this);
        controller.showAllSopir();

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
                new InputSopir();
            }
        });
        
        tombolKembali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewMenu(Session.username);;
            }
        });

        // Memberikan event handling ketika tombol "Edit Mahasiswa" diklik
        tombolEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengecek apakah ada baris di dalam tabel yang dipilih atau tidak
                if (baris != null) {

                    ModelSopir sopirTerpilih = new ModelSopir();
                    
                    // Mengambil id dan nama berdasarkan baris yang dipilih
                    Integer id = (int) table.getValueAt(baris, 0);
                    String nama = table.getValueAt(baris, 1).toString();
                    String sim = table.getValueAt(baris, 2).toString();
                    String hp = table.getValueAt(baris, 3).toString();

                    sopirTerpilih.setidSopir(id);
                    sopirTerpilih.setNama(nama);
                    sopirTerpilih.setSIM(sim);
                    sopirTerpilih.setHP(hp);

                    dispose();

                    new EditSopir(sopirTerpilih);
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
                controller.searchSopir();
            }
        });
    }

    public JTable getTableSopir() {
        return table;
    }
    public String getInputCari() {
        return inputCari.getText();
    }
}
