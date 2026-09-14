/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Sopir;

import java.util.List;

public interface InterfaceDAOSopir {
    // Method untuk memasukkan suatu data
    public void insert(ModelSopir sopir);
    // Method untuk mengupdate (mengedit) suatu data
    public void update(ModelSopir sopir);
    // Method untuk menghapus suatu data
    public void delete(int id);
    // Method untuk mengambil data
    public List<ModelSopir> getAll();
    // Method untuk mencari data
    public List<ModelSopir> search(String keyword);
}
