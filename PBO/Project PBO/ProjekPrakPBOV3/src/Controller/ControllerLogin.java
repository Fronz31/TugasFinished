package Controller;

import Model.Employee.DAOEmployee;
import Model.Employee.ModelEmployee;
import Model.Member.DAOMember;
import Model.Member.ModelMember;
import View.Auth.LoginView;
import View.Employee.EmployeeDashboard;
import View.Member.MemberDashboard;

import java.sql.SQLException;
import javax.swing.*;

public class ControllerLogin {

    private final LoginView view;
    private final DAOEmployee daoEmployee = new DAOEmployee();
    private final DAOMember   daoMember   = new DAOMember();

    public ControllerLogin(LoginView view) {
        this.view = view;
    }

    public void login() {
        String input1 = view.getInput1().trim(); // username atau email
        String input2 = view.getInput2().trim(); // password

        if (input1.isEmpty() || input2.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Input tidak boleh kosong!",
                "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Jalankan di thread terpisah (Multithread)
        new Thread(() -> {
            try {
                // Coba login sebagai Employee dulu
                ModelEmployee emp = daoEmployee.login(input1, input2);
                if (emp != null) {
                    SessionManager.getInstance().setEmployee(emp);
                    SwingUtilities.invokeLater(() -> {
                        view.dispose();
                        new EmployeeDashboard();
                    });
                    return;
                }

                // Coba login sebagai Member (pakai email)
                ModelMember member = daoMember.login(input1, input2);
                if (member != null) {
                    SessionManager.getInstance().setMember(member);
                    SwingUtilities.invokeLater(() -> {
                        view.dispose();
                        new MemberDashboard();
                    });
                    return;
                }

                // Keduanya gagal
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view,
                        "Username/Email atau Password salah!\n(Pastikan akun member berstatus aktif)",
                        "Login Gagal", JOptionPane.WARNING_MESSAGE));

            } catch (SQLException ex) {
                SwingUtilities.invokeLater(() ->
                    JOptionPane.showMessageDialog(view,
                        "Koneksi database gagal:\n" + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE));
            }
        }).start();
    }
}
