package View.Employee.employee;

import Controller.ControllerEmployee;
import Model.Employee.ModelTableEmployee;
import View.Employee.EmployeeDashboard;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.regex.Pattern;

public class ViewEmployee extends JFrame {

    private final JTable     table    = new JTable();
    private final JTextField tfSearch = new JTextField();
    private final JButton btnTambah   = new JButton("+ Tambah");
    private final JButton btnEdit     = new JButton("Edit");
    private final JButton btnHapus    = new JButton("Hapus");
    private final JButton btnBack     = new JButton("Kembali");

    private final ControllerEmployee ctrl = new ControllerEmployee();
    private TableRowSorter<ModelTableEmployee> sorter;

    public ViewEmployee() {
        setTitle("Manajemen Pegawai");
        setSize(740, 530);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(248, 248, 252));

        JLabel lblTitle = new JLabel("Daftar Pegawai");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitle.setBounds(20, 12, 300, 28);

        JLabel lblFilter = new JLabel("Filter:"); lblFilter.setBounds(20, 50, 40, 25);
        tfSearch.setBounds(62, 50, 250, 25);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 85, 690, 340);
        table.setRowHeight(24);

        btnTambah.setBounds(20,  445, 110, 32);
        btnEdit  .setBounds(145, 445, 100, 32);
        btnHapus .setBounds(258, 445, 100, 32);
        btnBack  .setBounds(570, 445, 110, 32);

        styleBtn(btnTambah);
        styleBtn(btnEdit);
        styleBtn(btnHapus);
        styleBtn(btnBack);

        add(lblTitle); add(lblFilter); add(tfSearch);
        add(scroll); add(btnTambah); add(btnEdit); add(btnHapus); add(btnBack);

        loadData();

        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { applyFilter(); }
            public void removeUpdate(DocumentEvent e)  { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        btnTambah.addActionListener(e -> new InputEmployeeDialog(this));

        btnEdit.addActionListener(e -> {
            int row = getSelectedModelRow(); if (row < 0) return;
            int    id   = (int) table.getModel().getValueAt(row, 0);
            String uname=       table.getModel().getValueAt(row, 1).toString();
            String role =       table.getModel().getValueAt(row, 2).toString();
            new EditEmployeeDialog(this, id, uname, role);
        });

        btnHapus.addActionListener(e -> {
            int row = getSelectedModelRow(); if (row < 0) return;
            int    id   = (int) table.getModel().getValueAt(row, 0);
            String uname=       table.getModel().getValueAt(row, 1).toString();
            ctrl.delete(id, uname, this::loadData);
        });

        btnBack.addActionListener(e -> { dispose(); new EmployeeDashboard(); });

        setVisible(true);
    }

    public void loadData() {
        ctrl.loadAll(table);
        SwingUtilities.invokeLater(() -> {
            if (table.getRowSorter() != null)
                sorter = (TableRowSorter<ModelTableEmployee>) table.getRowSorter();
        });
    }

    private void applyFilter() {
        if (sorter == null) return;
        String t = tfSearch.getText().trim();
        sorter.setRowFilter(t.isEmpty() ? null :
            RowFilter.regexFilter("(?i)" + Pattern.quote(t)));
    }

    private int getSelectedModelRow() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pegawai terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        return table.convertRowIndexToModel(row);
    }

    private void styleBtn(JButton b) {
        b.setBackground(Color.BLACK);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
    }
}
