package Controller;

import Model.Employee.ModelEmployee;
import Model.Member.ModelMember;

/**
 * SessionManager (Singleton - Encapsulation pilar OOP).
 * Menyimpan state user yang sedang login agar bisa diakses dari view mana pun.
 */
public class SessionManager {
    private static SessionManager instance;

    private ModelEmployee loggedEmployee;
    private ModelMember   loggedMember;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setEmployee(ModelEmployee e) { this.loggedEmployee = e; this.loggedMember = null; }
    public void setMember(ModelMember m)     { this.loggedMember = m; this.loggedEmployee = null; }

    public ModelEmployee getEmployee() { return loggedEmployee; }
    public ModelMember   getMember()   { return loggedMember; }

    public boolean isEmployee() { return loggedEmployee != null; }
    public boolean isMember()   { return loggedMember != null; }
    public boolean isManager()  { return isEmployee() && loggedEmployee.isManager(); }

    public void logout() { loggedEmployee = null; loggedMember = null; }
}
