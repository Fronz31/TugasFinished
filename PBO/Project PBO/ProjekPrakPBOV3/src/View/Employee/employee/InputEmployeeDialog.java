package View.Employee.employee;

import Controller.ControllerEmployee;
import javax.swing.*;
import java.awt.*;

public class InputEmployeeDialog extends JDialog {

    public InputEmployeeDialog(ViewEmployee parent) {
        super(parent, "Tambah Pegawai", true);
        setSize(380, 270); setLayout(null); setLocationRelativeTo(parent);

        JTextField tfUser = new JTextField(); tfUser.setBounds(140, 30, 200, 25); add(tfUser);
        JPasswordField tfPass = new JPasswordField(); tfPass.setBounds(140, 70, 200, 25); add(tfPass);

        // Role dropdown
        String[] roles = {"staff", "manager"};
        JComboBox<String> cbRole = new JComboBox<>(roles);
        cbRole.setBounds(140, 110, 200, 25); add(cbRole);

        JLabel l1 = new JLabel("Username:"); l1.setBounds(30, 30, 100, 25); add(l1);
        JLabel l2 = new JLabel("Password:"); l2.setBounds(30, 70, 100, 25); add(l2);
        JLabel l3 = new JLabel("Role:");     l3.setBounds(30,110, 100, 25); add(l3);

        JButton btnSave = new JButton("Simpan");
        btnSave.setBounds(130, 165, 100, 32);
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 12));
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setOpaque(true);
        add(btnSave);

        ControllerEmployee ctrl = new ControllerEmployee();
        btnSave.addActionListener(e ->
            ctrl.insert(tfUser.getText(), new String(tfPass.getPassword()),
                        cbRole.getSelectedItem().toString(), () -> {
                dispose(); parent.loadData();
            })
        );

        setVisible(true);
    }
}
