/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Kendaraan;

import Model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOKendaraan implements InterfaceDAOKendaraan{
    @Override
    public void insert(ModelKendaraan kendaraan) {
       try {
            // Perintah query disimpan ke dalam variabel "query"
            String query = "INSERT INTO kendaraan (plat_nomor, jenis, merk) VALUES (?, ?, ?);";
            
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, kendaraan.getPlat());
            statement.setString(2, kendaraan.getJenis());
            statement.setString(3, kendaraan.getMerk());
            // Menjalankan query untuk memasukkan data mahasiswa baru
            statement.executeUpdate();
            // Menutup koneksi untuk menghemat penggunaan memory.
            statement.close();
        } catch (SQLException e) {
            // Menampilkan pesan error ketika gagal input data.
            System.out.println("Input Failed: " + e.getLocalizedMessage());
        } 
    }

    @Override
    public void update(ModelKendaraan kendaraan) {
        try {
            // Perintah query disimpan ke dalam variabel "query"
            String query = "UPDATE kendaraan SET plat_nomor=?, jenis=?, merk=? WHERE id=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, kendaraan.getPlat());
            statement.setString(2, kendaraan.getJenis());
            statement.setString(3, kendaraan.getMerk());
            statement.setInt(4, kendaraan.getidKendaraan());
            // Menjalankan query untuk menghapus data mahasiswa yang dipilih
            statement.executeUpdate();
            // Menutup koneksi untuk menghemat penggunaan memory.
            statement.close();
        } catch (SQLException e) {
            // Menampilkan pesan error ketika gagal edit data.
            System.out.println("update Failed! (" + e.getMessage() + ")");
        }
    }

    @Override
    public void delete(int id) {
        try {
            // Perintah query disimpan ke dalam variabel "query"
            String query = "DELETE FROM kendaraan WHERE id=?;";

            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setInt(1, id);            
            // Menjalankan query untuk menghapus data mahasiswa yang dipilih
            statement.executeUpdate();           
            // Menutup koneksi untuk menghemat penggunaan memory.
            statement.close();
        } catch (SQLException e) {
            // Menampilkan pesan error ketika gagal hapus data.
            System.out.println("Delete Failed: " + e.getLocalizedMessage());
        }
    }
    
    @Override
    public List<ModelKendaraan> search(String keyword) {
        List<ModelKendaraan> listKendaraan = new ArrayList<>();

        try {

            String query = "SELECT * FROM kendaraan WHERE plat_nomor LIKE ? OR merk LIKE ?";
            PreparedStatement statement =
            Connector.Connect().prepareStatement(query);
            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ModelKendaraan kdr = new ModelKendaraan();

                kdr.setidKendaraan(resultSet.getInt("id"));
                kdr.setPlat(resultSet.getString("plat_nomor"));
                kdr.setJenis(resultSet.getString("jenis"));
                kdr.setMerk(resultSet.getString("merk"));

                listKendaraan.add(kdr);
            }

            statement.close();

        } catch (SQLException e) {
            System.out.println("Search Failed: " + e.getMessage());
        }

        return listKendaraan;
    }

    @Override
    public List<ModelKendaraan> getAll() {
         List<ModelKendaraan> listKendaraan = null;
        try {
            listKendaraan = new ArrayList<>();
            
            // Membuat objek statement yang digunakan untuk melakukan query.
            Statement statement = Connector.Connect().createStatement();

            String query = "SELECT * FROM kendaraan;";
            
             // Mengeksekusi query dan menyimpannya ke dalam variabel "resultSet".
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                // Membuat sebuah objek "Mahasiswa" untuk menyimpan data tiap-tiap mahasiswa
                ModelKendaraan kdr = new ModelKendaraan();    
                // Memasukkan hasil query ke objek mahasiswa
                kdr.setidKendaraan(resultSet.getInt("id"));
                kdr.setPlat(resultSet.getString("plat_nomor"));
                kdr.setJenis(resultSet.getString("jenis"));
                kdr.setMerk(resultSet.getString("merk"));

                listKendaraan.add(kdr);
            }            
            // Menutup koneksi untuk menghemat penggunaan memory.
            statement.close();
        } catch (SQLException e) {
            // Menampilkan pesan error ketika gagal mengambil data.
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return listKendaraan;
    }
}