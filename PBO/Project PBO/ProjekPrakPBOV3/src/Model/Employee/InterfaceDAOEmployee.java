package Model.Employee;

import Model.BaseDAO;
import java.sql.SQLException;

public interface InterfaceDAOEmployee extends BaseDAO<ModelEmployee> {
    ModelEmployee login(String username, String password) throws SQLException;
}
