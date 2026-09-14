package Model.Loan;

import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAOLoan {
    void             pinjam(int idMember, int idBook) throws SQLException;
    void             kembalikan(int idLoan, int idBook) throws SQLException;
    List<ModelLoan>  getByMember(int idMember)        throws SQLException;
    List<ModelLoan>  getAllAktif()                     throws SQLException; // untuk employee
    int              countAktifByMember(int idMember) throws SQLException;
}
