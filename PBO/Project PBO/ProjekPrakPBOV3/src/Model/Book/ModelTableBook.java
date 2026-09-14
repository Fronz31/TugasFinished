package Model.Book;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableBook extends AbstractTableModel {
    private final List<ModelBook> list;
    private final String[] cols = {"ID", "Judul", "Pengarang", "Tahun", "Stok"};

    public ModelTableBook(List<ModelBook> list) { this.list = list; }

    @Override public int    getRowCount()    { return list.size(); }
    @Override public int    getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Class<?> getColumnClass(int c) {
        return (c == 0 || c == 3 || c == 4) ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int r, int c) {
        ModelBook b = list.get(r);
        switch (c) {
            case 0: return b.getIdBook();
            case 1: return b.getTitle();
            case 2: return b.getAuthor();
            case 3: return b.getYear();
            case 4: return b.getStock();
            default: return null;
        }
    }
}
