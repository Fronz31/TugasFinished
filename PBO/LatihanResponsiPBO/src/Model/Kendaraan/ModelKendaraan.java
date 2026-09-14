/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Kendaraan;

public class ModelKendaraan {
    private int idKendaraan;
    private String plat_nomor;
    private String jenis;
    private String merk;

    public int getidKendaraan() {
        return idKendaraan;
    }
    public void setidKendaraan(int idKendaraan) {
        this.idKendaraan = idKendaraan;
    }
    public String getPlat() {
        return plat_nomor;
    }
    public void setPlat(String plat) {
        this.plat_nomor = plat;
    }
    public String getJenis() {
        return jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getMerk() {
        return merk;
    }
    public void setMerk(String merk) {
        this.merk = merk;
    }
}