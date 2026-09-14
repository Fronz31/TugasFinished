package Model.Member;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableMember extends AbstractTableModel {
    private final List<ModelMember> list;
    private final String[] cols = {"ID", "Nama", "Email", "Status"};

    public ModelTableMember(List<ModelMember> list) { this.list = list; }

    @Override public int    getRowCount()    { return list.size(); }
    @Override public int    getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Class<?> getColumnClass(int c) {
        return c == 0 ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int r, int c) {
        ModelMember m = list.get(r);
        switch (c) {
            case 0: return m.getIdMember();
            case 1: return m.getName();
            case 2: return m.getEmail();
            case 3: return m.getStatus();
            default: return null;
        }
    }
}
