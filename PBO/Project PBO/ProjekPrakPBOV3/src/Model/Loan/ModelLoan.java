package Model.Loan;

import Model.BaseModel;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ModelLoan extends BaseModel {
    private int       idLoan;
    private int       idMember;
    private int       idBook;
    private String    judulBook;   // join dari tabel book
    private String    authorBook;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalKembali; // null = belum dikembalikan

    public ModelLoan(int idLoan, int idMember, int idBook,
                     String judulBook, String authorBook,
                     LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        this.idLoan        = idLoan;
        this.idMember      = idMember;
        this.idBook        = idBook;
        this.judulBook     = judulBook;
        this.authorBook    = authorBook;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    @Override public int    getId()          { return idLoan; }
    @Override public String getDisplayName() { return judulBook + " (pinjam: " + tanggalPinjam + ")"; }

    public int       getIdLoan()         { return idLoan; }
    public int       getIdMember()       { return idMember; }
    public int       getIdBook()         { return idBook; }
    public String    getJudulBook()      { return judulBook; }
    public String    getAuthorBook()     { return authorBook; }
    public LocalDate getTanggalPinjam()  { return tanggalPinjam; }
    public LocalDate getTanggalKembali() { return tanggalKembali; }

    public boolean sudahDikembalikan() { return tanggalKembali != null; }

    /** Hitung lama pinjam dalam hari (hingga hari ini atau tanggal kembali) */
    public long getLamaPinjamHari() {
        LocalDate akhir = tanggalKembali != null ? tanggalKembali : LocalDate.now();
        return ChronoUnit.DAYS.between(tanggalPinjam, akhir);
    }
}
