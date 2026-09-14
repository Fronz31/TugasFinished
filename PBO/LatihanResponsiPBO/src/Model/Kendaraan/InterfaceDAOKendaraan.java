/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Kendaraan;

import java.util.List;

public interface InterfaceDAOKendaraan {
    // Method untuk memasukkan suatu data
    public void insert(ModelKendaraan kendaraan);
    // Method untuk mengupdate (mengedit) suatu data
    public void update(ModelKendaraan kendaraan);
    // Method untuk menghapus suatu data
    public void delete(int id);
    // Method untuk mengambil data
    public List<ModelKendaraan> getAll();
    // Method untuk mencari data
    public List<ModelKendaraan> search(String keyword);
}