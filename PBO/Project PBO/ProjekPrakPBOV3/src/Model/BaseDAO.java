package Model;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface generik untuk semua DAO (Abstraction + Polymorphism - pilar OOP).
 */
public interface BaseDAO<T extends BaseModel> {
    void   insert(T obj)  throws SQLException;
    void   update(T obj)  throws SQLException;
    void   delete(int id) throws SQLException;
    List<T> getAll()      throws SQLException;
}
