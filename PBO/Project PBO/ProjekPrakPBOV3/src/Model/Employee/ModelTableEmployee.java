package Model.Employee;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableEmployee extends AbstractTableModel {
    private final List<ModelEmployee> list;
    private final String[] cols = {"ID", "Username", "Role"};

    public ModelTableEmployee(List<ModelEmployee> list) { this.list = list; }

    @Override public int    getRowCount()    { return list.size(); }
    @Override public int    getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Class<?> getColumnClass(int c) {
        return c == 0 ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int r, int c) {
        ModelEmployee e = list.get(r);
        switch (c) {
            case 0: return e.getIdEmployee();
            case 1: return e.getUsername();
            case 2: return e.getRole();
            default: return null;
        }
    }
}
