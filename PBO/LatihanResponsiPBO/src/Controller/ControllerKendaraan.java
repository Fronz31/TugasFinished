/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Kendaraan.*;
import View.Kendaraan.*;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerKendaraan {
    ViewKendaraan halamanTable;
    InputKendaraan halamanInput;
    EditKendaraan halamanEdit;
    
    InterfaceDAOKendaraan daoKendaraan;

    // Membuat variabel "daftarMahasiswa" untuk menyimpan data mahasiswa yg diambil dari DB.
    List<ModelKendaraan> daftarKendaraan;

    public ControllerKendaraan(ViewKendaraan halamanTable) {
        this.halamanTable = halamanTable;
        this.daoKendaraan = new DAOKendaraan();
    }
    public ControllerKendaraan(InputKendaraan halamanInput) {
        this.halamanInput = halamanInput;
        this.daoKendaraan = new DAOKendaraan();
    }
    public ControllerKendaraan(EditKendaraan halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daoKendaraan = new DAOKendaraan();
    }

    public void showAllKendaraan() {
        daftarKendaraan = daoKendaraan.getAll();

        ModelTableKendaraan table = new ModelTableKendaraan(daftarKendaraan);

        // Mengisi tabel yang berada pada halaman Table Mahasiswa
        halamanTable.getTableKendaraan().setModel(table);
    }

    public void insertKendaraan() {
        try {
            // Membuat "mahasiswa baru" yang isinya masih kosong
            ModelKendaraan kendaraanBaru = new ModelKendaraan();

            String plat = halamanInput.getInputPlat();
            String jenis = halamanInput.getInputJenis();
            String merk = halamanInput.getInputMerk();

            if ("".equals(plat) || "".equals(jenis) || "".equals(merk)) {
                throw new Exception("Input tidak boleh kosong!");
            }
            
            // Mengisi nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            kendaraanBaru.setPlat(plat);
            kendaraanBaru.setJenis(jenis);
            kendaraanBaru.setMerk(merk);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoKendaraan.insert(kendaraanBaru);
            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Kendaraan baru berhasil ditambahkan.");
            
            // Terakhir, program akan pindah ke halaman Table Mahasiswa()
            halamanInput.dispose();
            new ViewKendaraan();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public void editKendaraan(int id) {
        try {

            ModelKendaraan kendaraanEdit = new ModelKendaraan();

            String plat = halamanEdit.getInputPlat();
            String jenis = halamanEdit.getInputJenis();
            String merk = halamanEdit.getInputMerk();

            if ("".equals(plat) || "".equals(jenis) || "".equals(merk)) {
                throw new Exception("Input tidak boleh kosong!");
            }
            
            // Mengisi id, nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            kendaraanEdit.setidKendaraan(id);
            kendaraanEdit.setPlat(plat);
            kendaraanEdit.setJenis(jenis);
            kendaraanEdit.setMerk(merk);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoKendaraan.update(kendaraanEdit);
            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Data kendaraan berhasil diubah.");

            // Terakhir, program akan pindah ke halaman Table Mahasiswa()
            halamanEdit.dispose();
            new ViewKendaraan();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void deleteKendaraan(Integer baris) {
        // Mengambil id dan nama berdasarkan baris yang dipilih
        Integer id = (int) halamanTable.getTableKendaraan().getValueAt(baris, 0);
        String plat = halamanTable.getTableKendaraan().getValueAt(baris, 1).toString();
        String jenis = halamanTable.getTableKendaraan().getValueAt(baris, 2).toString();
        String merk = halamanTable.getTableKendaraan().getValueAt(baris, 3).toString();

        // Membuat Pop-Up untuk mengonfirmasi apakah ingin menghapus data
        int input = JOptionPane.showConfirmDialog(
                null,
                "Hapus kendaraan" + "?",
                "Hapus Data Kendaraan",
                JOptionPane.YES_NO_OPTION
        );
        // Jika user memilih opsi "yes", maka hapus data.
        if (input == 0) {

            daoKendaraan.delete(id);
            // Menampilkan pop-up jika berhasil menghapus.
            JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");
            // Memanggil method "showAllMahasiswa()" untuk merefresh table.
            showAllKendaraan();
        }
    }
    
    public void searchKendaraan() {
        String keyword = halamanTable.getInputCari();

        daftarKendaraan = daoKendaraan.search(keyword);

        ModelTableKendaraan table = new ModelTableKendaraan(daftarKendaraan);
        halamanTable.getTableKendaraan().setModel(table);
    }
}
