package Model.Book;

import Model.BaseModel;

public class ModelBook extends BaseModel {
    private int    idBook;
    private String title;
    private String author;
    private int    year;
    private int    stock;
    private int    idRack;
    private String rackName;

    public ModelBook(int idBook, String title, String author, int year, int stock, int idRack) {
        this.idBook    = idBook;
        this.title     = title;
        this.author    = author;
        this.year      = year;
        this.stock     = stock;
        this.idRack    = idRack;
        this.rackName  = "";
    }

    public ModelBook(String title, String author, int year, int stock, int idRack) {
        this.title    = title;
        this.author   = author;
        this.year     = year;
        this.stock    = stock;
        this.idRack   = idRack;
        this.rackName = "";
    }

    @Override public int    getId()          { return idBook; }
    @Override public String getDisplayName() { return title + " - " + author; }

    public int    getIdBook()   { return idBook; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public int    getYear()     { return year; }
    public int    getStock()    { return stock; }
    public int    getIdRack()   { return idRack; }
    public String getRackName() { return rackName; }

    public void setIdBook(int id)          { this.idBook    = id; }
    public void setTitle(String title)     { this.title     = title; }
    public void setAuthor(String author)   { this.author    = author; }
    public void setYear(int year)          { this.year      = year; }
    public void setStock(int stock)        { this.stock     = stock; }
    public void setIdRack(int idRack)      { this.idRack    = idRack; }
    public void setRackName(String name)   { this.rackName  = name; }
}
