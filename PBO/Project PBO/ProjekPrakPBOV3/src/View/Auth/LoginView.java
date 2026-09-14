package View.Auth;

import Controller.ControllerLogin;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {

    private final JTextField     tfInput1 = new JTextField();
    private final JPasswordField tfInput2 = new JPasswordField();
    private final JButton        btnLogin = new JButton("Login");
    private final JButton        btnRegis = new JButton("Daftar sebagai Member");

    public LoginView() {
        setTitle("Perpustakaan - Login");
        setSize(420, 410);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("SISTEM MANAJEMEN PERPUSTAKAAN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setBounds(0, 20, 420, 25);

        JLabel lblSub = new JLabel("Login sebagai Employee (username) atau Member (email)", SwingConstants.CENTER);
        lblSub.setFont(new Font("Arial", Font.ITALIC, 10));
        lblSub.setForeground(Color.GRAY);
        lblSub.setBounds(0, 48, 420, 20);

        JLabel lInput1 = new JLabel("Username / Email:");
        lInput1.setBounds(60, 90, 130, 25);
        tfInput1.setBounds(200, 90, 160, 25);

        JLabel lInput2 = new JLabel("Password:");
        lInput2.setBounds(60, 130, 130, 25);
        tfInput2.setBounds(200, 130, 160, 25);

        btnLogin.setBounds(100, 185, 100, 32);
        btnLogin.setBackground(Color.BLACK);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 12));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);

        JSeparator sep = new JSeparator();
        sep.setBounds(60, 233, 290, 2);

        JLabel lblOr = new JLabel("Belum punya akun member?", SwingConstants.CENTER);
        lblOr.setFont(new Font("Arial", Font.PLAIN, 11));
        lblOr.setForeground(Color.DARK_GRAY);
        lblOr.setBounds(0, 240, 420, 18);

        btnRegis.setBounds(110, 262, 200, 30);
        btnRegis.setBackground(Color.WHITE);
        btnRegis.setForeground(Color.BLACK);
        btnRegis.setFont(new Font("Arial", Font.BOLD, 11));
        btnRegis.setFocusPainted(false);
        btnRegis.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(lblTitle); add(lblSub);
        add(lInput1); add(tfInput1);
        add(lInput2); add(tfInput2);
        add(btnLogin); add(sep); add(lblOr); add(btnRegis);

        ControllerLogin ctrl = new ControllerLogin(this);
        btnLogin.addActionListener(e -> ctrl.login());
        getRootPane().setDefaultButton(btnLogin);

        btnRegis.addActionListener(e -> new RegisterView());

        setVisible(true);
    }

    public String getInput1() { return tfInput1.getText(); }
    public String getInput2() { return new String(tfInput2.getPassword()); }
}
