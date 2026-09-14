/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Kendaraan;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableKendaraan extends AbstractTableModel{
    // Berfungsi untuk menyimpan daftar mahasiswa
    List<ModelKendaraan> daftarKendaraan;

    String kolom[] = {"ID", "Plat Nomor", "Jenis", "Merk"};

    public ModelTableKendaraan(List<ModelKendaraan> daftarKendaraan) {
        this.daftarKendaraan = daftarKendaraan;
    }

    // Method untuk mengambil jumlah baris dari tabel
    @Override
    public int getRowCount() {
        return daftarKendaraan.size();
    }

    // Method untuk mengambil jumlah kolom dari tabel
    @Override
    public int getColumnCount() {
        return kolom.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return daftarKendaraan.get(rowIndex).getidKendaraan();
            case 1:
                return daftarKendaraan.get(rowIndex).getPlat();
            case 2:
                return daftarKendaraan.get(rowIndex).getJenis();
            case 3:
                return daftarKendaraan.get(rowIndex).getMerk();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int columnIndex) {
        return kolom[columnIndex];
    }
}