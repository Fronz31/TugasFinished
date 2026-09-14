package Main;

import View.Auth.LoginView;
import javax.swing.*;

/**
 * Entry point aplikasi Sistem Manajemen Perpustakaan.
 *
 * Implementasi 4 Pilar OOP:
 *  1. ENCAPSULATION  : semua field model bersifat private, diakses via getter/setter
 *  2. INHERITANCE    : ModelEmployee, ModelMember, ModelBook, ModelRack, ModelLoan
 *                      semua extends BaseModel
 *  3. ABSTRACTION    : BaseModel (abstract class) + BaseDAO (interface) memaksa
 *                      implementasi getId(), getDisplayName(), insert/update/delete/getAll
 *  4. POLYMORPHISM   : BaseDAO<T> diimplementasi oleh DAOEmployee, DAOMember,
 *                      DAOBook, DAORack dengan perilaku berbeda-beda
 *
 * Multithread:
 *  - Setiap operasi database dijalankan di thread terpisah (new Thread(...).start())
 *  - Pembaruan UI selalu dikembalikan ke EDT via SwingUtilities.invokeLater(...)
 *
 * MVC:
 *  - Model  : package Model.* (POJO + DAO + TableModel)
 *  - View   : package View.*  (Swing GUI)
 *  - Controller : package Controller.* (logika bisnis, mediator Model ↔ View)
 *
 * JDBC:
 *  - Model/Connector.java → koneksi MySQL via JDBC
 *  - Semua DAO menggunakan PreparedStatement untuk keamanan query
 */
public class Main {
    public static void main(String[] args) {
        // Set Look and Feel bawaan sistem
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Jalankan di EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(LoginView::new);
    }
}
