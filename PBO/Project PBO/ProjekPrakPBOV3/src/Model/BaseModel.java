package Model;

/**
 * Kelas abstrak sebagai dasar semua model (Abstraction - pilar OOP).
 * Memaksa setiap model memiliki metode getId() dan getDisplayName().
 */
public abstract class BaseModel {
    public abstract int    getId();
    public abstract String getDisplayName();

    @Override
    public String toString() {
        return getDisplayName();
    }
}
