/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Sopir.*;
import View.Sopir.*;
import java.util.List;
import javax.swing.JOptionPane;

public class ControllerSopir {
    ViewSopir halamanTable;
    InputSopir halamanInput;
    EditSopir halamanEdit;
    
    InterfaceDAOSopir daoSopir;

    // Membuat variabel "daftarMahasiswa" untuk menyimpan data mahasiswa yg diambil dari DB.
    List<ModelSopir> daftarSopir;

    public ControllerSopir(ViewSopir halamanTable) {
        this.halamanTable = halamanTable;
        this.daoSopir = new DAOSopir();
    }
    
    public ControllerSopir(InputSopir halamanInput) {
        this.halamanInput = halamanInput;
        this.daoSopir = new DAOSopir();
    }
    
    public ControllerSopir(EditSopir halamanEdit) {
        this.halamanEdit = halamanEdit;
        this.daoSopir= new DAOSopir();
    }

    public void showAllSopir() {
        daftarSopir = daoSopir.getAll();

        ModelTableSopir table = new ModelTableSopir(daftarSopir);

        // Mengisi tabel yang berada pada halaman Table Mahasiswa
        halamanTable.getTableSopir().setModel(table);
    }

    public void insertSopir() {
        try {
            // Membuat "mahasiswa baru" yang isinya masih kosong
            ModelSopir sopirBaru = new ModelSopir();

            String nama = halamanInput.getInputNama();
            String sim = halamanInput.getInputSIM();
            String hp = halamanInput.getInputHP();

            if ("".equals(nama) || "".equals(sim) || "".equals(hp)) {
                throw new Exception("Input tidak boleh kosong!");
            }
            
            // Mengisi nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            sopirBaru.setNama(nama);
            sopirBaru.setSIM(sim);
            sopirBaru.setHP(hp);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoSopir.insert(sopirBaru);
            
            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Sopir baru berhasil ditambahkan.");
            
            // Terakhir, program akan pindah ke halaman Table Mahasiswa()
            halamanInput.dispose();
            new ViewSopir();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    public void editSopir(int id) {
        try {

            ModelSopir sopirEdit = new ModelSopir();

            String nama = halamanEdit.getInputNama();
            String sim = halamanEdit.getInputSIM();
            String hp = halamanEdit.getInputHP();

            if ("".equals(nama) || "".equals(sim) || "".equals(hp)) {
                throw new Exception("Input tidak boleh kosong!");
            }
            
            // Mengisi id, nama dan nim dari "mahasiswa baru" yang dibuat tadi.
            sopirEdit.setidSopir(id);
            sopirEdit.setNama(nama);
            sopirEdit.setSIM(sim);
            sopirEdit.setHP(hp);
            
            // Memasukkan "mahasiswa baru" ke dalam database.
            daoSopir.update(sopirEdit);

            // Menampilkan pop-up ketika berhasil mengedit data
            JOptionPane.showMessageDialog(null, "Data sopir berhasil diubah.");

            // Terakhir, program akan pindah ke halaman Table Mahasiswa()
            halamanEdit.dispose();
            new ViewSopir();
        } catch (Exception e) {
            // Menampilkan pop-up ketika terjadi error
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public void deleteKendaraan(Integer baris) {
        // Mengambil id dan nama berdasarkan baris yang dipilih
        Integer id = (int) halamanTable.getTableSopir().getValueAt(baris, 0);
        String nama = halamanTable.getTableSopir().getValueAt(baris, 1).toString();
        String sim = halamanTable.getTableSopir().getValueAt(baris, 2).toString();
        String hp = halamanTable.getTableSopir().getValueAt(baris, 3).toString();

        // Membuat Pop-Up untuk mengonfirmasi apakah ingin menghapus data
        int input = JOptionPane.showConfirmDialog(
                null,
                "Hapus Sopir" + "?",
                "Hapus Data Sopir",
                JOptionPane.YES_NO_OPTION
        );

        // Jika user memilih opsi "yes", maka hapus data.
        if (input == 0) {

            daoSopir.delete(id);
            
            // Menampilkan pop-up jika berhasil menghapus.
            JOptionPane.showMessageDialog(null, "Berhasil menghapus data.");

            // Memanggil method "showAllMahasiswa()" untuk merefresh table.
            showAllSopir();
        }
    }
    
    public void searchSopir() {
        String keyword = halamanTable.getInputCari();

        daftarSopir = daoSopir.search(keyword);
        ModelTableSopir table = new ModelTableSopir(daftarSopir);

        halamanTable.getTableSopir().setModel(table);
    }
}
