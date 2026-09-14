package Model.Rack;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableRack extends AbstractTableModel {
    private final List<ModelRack> list;
    private final String[] cols = {"ID", "Nama Rak", "Kategori", "Lokasi"};

    public ModelTableRack(List<ModelRack> list) { this.list = list; }

    @Override public int    getRowCount()    { return list.size(); }
    @Override public int    getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }
    @Override public Class<?> getColumnClass(int c) { return c == 0 ? Integer.class : String.class; }

    @Override
    public Object getValueAt(int r, int c) {
        ModelRack rack = list.get(r);
        switch (c) {
            case 0: return rack.getIdRack();
            case 1: return rack.getRackName();
            case 2: return rack.getCategory();
            case 3: return rack.getLocation();
            default: return null;
        }
    }
}
