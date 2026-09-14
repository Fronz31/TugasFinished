package View.Employee.employee;

import Controller.ControllerEmployee;
import javax.swing.*;
import java.awt.*;

public class EditEmployeeDialog extends JDialog {

    public EditEmployeeDialog(ViewEmployee parent, int id, String username, String role) {
        super(parent, "Edit Pegawai", true);
        setSize(380, 270); setLayout(null); setLocationRelativeTo(parent);

        JTextField    tfUser = new JTextField(username); tfUser.setBounds(140, 30, 200, 25); add(tfUser);
        JPasswordField tfPass = new JPasswordField();    tfPass.setBounds(140, 70, 200, 25); add(tfPass);

        JLabel hint = new JLabel("(kosongkan jika tidak ganti)");
        hint.setFont(new Font("Arial", Font.ITALIC, 10));
        hint.setForeground(Color.GRAY);
        hint.setBounds(140, 95, 220, 18); add(hint);

        String[] roles = {"staff", "manager"};
        JComboBox<String> cbRole = new JComboBox<>(roles);
        cbRole.setSelectedItem(role);
        cbRole.setBounds(140, 115, 200, 25); add(cbRole);

        JLabel l1 = new JLabel("Username:"); l1.setBounds(30, 30, 100, 25); add(l1);
        JLabel l2 = new JLabel("Password:"); l2.setBounds(30, 70, 100, 25); add(l2);
        JLabel l3 = new JLabel("Role:");     l3.setBounds(30,115, 100, 25); add(l3);

        JButton btnSave = new JButton("Update");
        btnSave.setBounds(130, 175, 100, 32);
        btnSave.setBackground(Color.BLACK);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Arial", Font.BOLD, 12));
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setOpaque(true);
        add(btnSave);

        ControllerEmployee ctrl = new ControllerEmployee();
        btnSave.addActionListener(e -> {
            String newPass = new String(tfPass.getPassword());
            // Jika password kosong, pakai placeholder agar tidak ganti (controller bisa handle)
            // Di sini kita kirim apa adanya, DAO akan update apapun yang dikirim
            ctrl.update(id, tfUser.getText(), newPass,
                        cbRole.getSelectedItem().toString(), () -> {
                dispose(); parent.loadData();
            });
        });

        setVisible(true);
    }
}
