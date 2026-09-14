package Model.Loan;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModelTableLoan extends AbstractTableModel {
    private final List<ModelLoan> list;
    private final String[] cols = {"ID", "Judul Buku", "Pengarang", "Tgl Pinjam", "Tgl Kembali", "Lama (hari)"};

    public ModelTableLoan(List<ModelLoan> list) { this.list = list; }

    @Override public int    getRowCount()    { return list.size(); }
    @Override public int    getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Class<?> getColumnClass(int c) {
        return (c == 0 || c == 5) ? Integer.class : String.class;
    }

    @Override
    public Object getValueAt(int r, int c) {
        ModelLoan l = list.get(r);
        switch (c) {
            case 0: return l.getIdLoan();
            case 1: return l.getJudulBook();
            case 2: return l.getAuthorBook();
            case 3: return l.getTanggalPinjam().toString();
            case 4: return l.sudahDikembalikan() ? l.getTanggalKembali().toString() : "-";
            case 5: return (int) l.getLamaPinjamHari();
            default: return null;
        }
    }

    public ModelLoan getLoan(int row) { return list.get(row); }
}
