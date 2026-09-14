package Model.Member;

import Model.BaseDAO;
import java.sql.SQLException;

public interface InterfaceDAOMember extends BaseDAO<ModelMember> {
    ModelMember login(String email, String password) throws SQLException;
    void setNonAktif(int idMember) throws SQLException;
}
