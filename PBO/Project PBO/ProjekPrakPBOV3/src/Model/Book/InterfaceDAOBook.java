package Model.Book;

import Model.BaseDAO;
import java.sql.SQLException;
import java.util.List;

public interface InterfaceDAOBook extends BaseDAO<ModelBook> {
    List<ModelBook> getByRack(int idRack) throws SQLException;
    List<ModelBook> searchGlobal(String keyword) throws SQLException; // lintas rack
}
