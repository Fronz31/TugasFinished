package Model.Rack;

import Model.BaseModel;

public class ModelRack extends BaseModel {
    private int    idRack;
    private String rackName;
    private String category;
    private String location;

    public ModelRack() {}

    public ModelRack(int idRack, String rackName, String category, String location) {
        this.idRack   = idRack;
        this.rackName = rackName;
        this.category = category;
        this.location = location;
    }

    public ModelRack(String rackName, String category, String location) {
        this.rackName = rackName;
        this.category = category;
        this.location = location;
    }

    @Override public int    getId()          { return idRack; }
    @Override public String getDisplayName() { return rackName + " - " + category; }

    public int    getIdRack()   { return idRack; }
    public String getRackName() { return rackName; }
    public String getCategory() { return category; }
    public String getLocation() { return location; }

    public void setIdRack(int id)          { this.idRack = id; }
    public void setRackName(String name)   { this.rackName = name; }
    public void setCategory(String cat)    { this.category = cat; }
    public void setLocation(String loc)    { this.location = loc; }
}
