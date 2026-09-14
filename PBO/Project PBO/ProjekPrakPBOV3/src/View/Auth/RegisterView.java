package View.Auth;

import Controller.ControllerMember;
import javax.swing.*;
import java.awt.*;

public class RegisterView extends JFrame {

    public RegisterView() {
        setTitle("Daftar Member Baru");
        setSize(420, 320);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 250, 245));

        JLabel lblTitle = new JLabel("REGISTRASI MEMBER BARU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setBounds(0, 20, 420, 25);

        JLabel l1 = new JLabel("Nama Lengkap:");    l1.setBounds(60,  70, 120, 25); add(l1);
        JLabel l2 = new JLabel("Email:");           l2.setBounds(60, 110, 120, 25); add(l2);
        JLabel l3 = new JLabel("Password:");        l3.setBounds(60, 150, 120, 25); add(l3);
        JLabel l4 = new JLabel("Ulangi Password:"); l4.setBounds(60, 190, 120, 25); add(l4);

        JTextField     tfName  = new JTextField();     tfName .setBounds(190,  70, 175, 25); add(tfName);
        JTextField     tfEmail = new JTextField();     tfEmail.setBounds(190, 110, 175, 25); add(tfEmail);
        JPasswordField tfPass  = new JPasswordField(); tfPass .setBounds(190, 150, 175, 25); add(tfPass);
        JPasswordField tfPass2 = new JPasswordField(); tfPass2.setBounds(190, 190, 175, 25); add(tfPass2);

        JButton btnDaftar = new JButton("Daftar Sekarang");
        btnDaftar.setBounds(100, 245, 160, 34);
        btnDaftar.setBackground(Color.BLACK);
        btnDaftar.setForeground(Color.WHITE);
        btnDaftar.setFont(new Font("Arial", Font.BOLD, 12));
        btnDaftar.setFocusPainted(false);
        btnDaftar.setBorderPainted(false);
        add(btnDaftar);

        add(lblTitle);

        ControllerMember ctrl = new ControllerMember();
        btnDaftar.addActionListener(e -> {
            String name  = tfName .getText().trim();
            String email = tfEmail.getText().trim();
            String pass1 = new String(tfPass .getPassword());
            String pass2 = new String(tfPass2.getPassword());

            if (name.isEmpty() || email.isEmpty() || pass1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!pass1.equals(pass2)) {
                JOptionPane.showMessageDialog(this, "Password tidak cocok!",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ctrl.insert(name, email, pass1, () -> {
                JOptionPane.showMessageDialog(this,
                    "Registrasi berhasil!\nSilakan login dengan email dan password Anda.",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            });
        });

        setVisible(true);
    }
}
