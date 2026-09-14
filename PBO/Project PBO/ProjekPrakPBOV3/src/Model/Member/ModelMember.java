package Model.Member;

import Model.BaseModel;

public class ModelMember extends BaseModel {
    private int    idMember;
    private String name;
    private String email;
    private String password;
    private String status;

    public ModelMember(int idMember, String name, String email, String password, String status) {
        this.idMember = idMember;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.status   = status;
    }

    // Constructor untuk registrasi baru (tanpa id)
    public ModelMember(String name, String email, String password) {
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.status   = "aktif";
    }

    @Override public int    getId()          { return idMember; }
    @Override public String getDisplayName() { return name + " <" + email + ">"; }

    public int    getIdMember() { return idMember; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPassword() { return password; }
    public String getStatus()   { return status; }

    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setPassword(String pw)     { this.password = pw; }
    public void setStatus(String status)   { this.status = status; }

    public boolean isAktif() { return "aktif".equalsIgnoreCase(status); }
}
