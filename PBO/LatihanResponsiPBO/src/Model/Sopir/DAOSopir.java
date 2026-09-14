/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Sopir;

import Model.Connector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOSopir implements InterfaceDAOSopir{
    @Override
    public void insert(ModelSopir sopir) {
       try {
            // Perintah query disimpan ke dalam variabel "query"
            String query = "INSERT INTO sopir (nama, no_sim, no_hp) VALUES (?, ?, ?);";
            
            /* 
              Memasukkan nama dan nim dari input user ke dalam query untuk 
              mengisi bagian "?, ?" (dalam hal ini berarti nama dan nim)
            */
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, sopir.getNama());
            statement.setString(2, sopir.getSIM());
            statement.setString(3, sopir.getHP());
            
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
    public void update(ModelSopir sopir) {
        try {
            // Perintah query disimpan ke dalam variabel "query"
            String query = "UPDATE sopir SET nama=?, no_sim=?, no_hp=? WHERE id=?;";
            
            /* 
              Memasukkan nama dan nim dari input user 
              beserta id yang didapat dari data yang mau diubah ke dalam query 
              untuk mengisi bagian "?".
            */
            PreparedStatement statement;
            statement = Connector.Connect().prepareStatement(query);
            statement.setString(1, sopir.getNama());
            statement.setString(2, sopir.getSIM());
            statement.setString(3, sopir.getHP());
            statement.setInt(4, sopir.getidSopir());
            
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
            String query = "DELETE FROM sopir WHERE id=?;";
            
            /* 
              Memasukkan id berdasarkan data yang mau dihapus ke dalam query 
              untuk mengisi bagian "?".
            */
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
    public List<ModelSopir> search(String keyword) {

        List<ModelSopir> listSopir = new ArrayList<>();
        try {
            String query = "SELECT * FROM sopir WHERE nama LIKE ? OR sim LIKE ?";

            PreparedStatement statement =
            Connector.Connect().prepareStatement(query);

            statement.setString(1, "%" + keyword + "%");
            statement.setString(2, "%" + keyword + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                ModelSopir spr = new ModelSopir();

                spr.setidSopir(resultSet.getInt("id"));
                spr.setNama(resultSet.getString("nama"));
                spr.setSIM(resultSet.getString("sim"));
                spr.setHP(resultSet.getString("hp"));

                listSopir.add(spr);
            }

            statement.close();

        } catch (SQLException e) {
            System.out.println("Search Failed: " + e.getMessage());
        }
        return listSopir;
    }

    @Override
    public List<ModelSopir> getAll() {
         List<ModelSopir> listSopir = null;

        try {
            listSopir = new ArrayList<>();
            
            // Membuat objek statement yang digunakan untuk melakukan query.
            Statement statement = Connector.Connect().createStatement();
            
            String query = "SELECT * FROM sopir;";
            
             // Mengeksekusi query dan menyimpannya ke dalam variabel "resultSet".
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
                // Membuat sebuah objek "Mahasiswa" untuk menyimpan data tiap-tiap mahasiswa
                ModelSopir spr = new ModelSopir();
                
                // Memasukkan hasil query ke objek mahasiswa
                spr.setidSopir(resultSet.getInt("id"));
                spr.setNama(resultSet.getString("nama"));
                spr.setSIM(resultSet.getString("no_sim"));
                spr.setHP(resultSet.getString("no_hp"));

                listSopir.add(spr);
            }
            
            // Menutup koneksi untuk menghemat penggunaan memory.
            statement.close();
        } catch (SQLException e) {
            // Menampilkan pesan error ketika gagal mengambil data.
            System.out.println("Error: " + e.getLocalizedMessage());
        }
        return listSopir;
    }
}
