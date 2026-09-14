package Model.Employee;

import Model.BaseModel;

/**
 * Model Employee (extends BaseModel - Inheritance pilar OOP).
 */
public class ModelEmployee extends BaseModel {
    private int    idEmployee;
    private String username;
    private String password;
    private String role;

    public ModelEmployee(int idEmployee, String username, String password, String role) {
        this.idEmployee = idEmployee;
        this.username   = username;
        this.password   = password;
        this.role       = role;
    }

    @Override public int    getId()          { return idEmployee; }
    @Override public String getDisplayName() { return username + " (" + role + ")"; }

    public int    getIdEmployee() { return idEmployee; }
    public String getUsername()   { return username; }
    public String getPassword()   { return password; }
    public String getRole()       { return role; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }

    public boolean isManager() { return "manager".equalsIgnoreCase(role); }
}
