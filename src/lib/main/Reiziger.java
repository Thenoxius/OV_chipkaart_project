package lib.main;

import java.sql.SQLException;
import java.util.Date;

public class Reiziger {
    private int id;
    private String voorletters;

    private String tussenvoegsel;
    private String achternaam;

    private Date geboortedatum;

    public Reiziger(int id,String vl, String tv, String ach, Date gb){
        this.id = id;
        this.voorletters = vl;
        this.tussenvoegsel = tv;
        this.achternaam = ach;
        this.geboortedatum = gb;
    }

    public int getId(){
        return id;
    }

    public void setId(int nieuwId){
        id = nieuwId;
    }
    public String getNaam(){
        return voorletters + " " + tussenvoegsel + " " + achternaam;
    }
    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }
    public Date getGeboortedatum() {
        return geboortedatum;
    }
    public String toString(){
        AdresDAOPsql dao = new AdresDAOPsql();
        Adres woning = dao.findByReiziger(this);
        return "Reiziger: " + this.getNaam() + " met ID: " + this.getId()  + ", Adres = " + woning.getStraat() +
                " " + woning.getHuisnummer() + " " + woning.getPostcode() + " " +
                woning.getWoonplaats() + " met adres ID #" + woning.getAdres_id();
        }

    public boolean equals(Object andereObject){
        boolean gelijkeObjecten = false;
        if (andereObject instanceof Reiziger) {
            Reiziger anderReiziger = (Reiziger) andereObject;
            if (this.id == anderReiziger.id &&  this.voorletters.equals(anderReiziger.voorletters) && this.tussenvoegsel.equals(anderReiziger.tussenvoegsel)
                    && this.achternaam.equals(anderReiziger.achternaam) && this.geboortedatum.equals(anderReiziger.geboortedatum)){
                gelijkeObjecten = true;}
        }
        return gelijkeObjecten;
    }


}
