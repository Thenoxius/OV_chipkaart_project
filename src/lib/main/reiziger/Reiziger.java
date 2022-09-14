package lib.main.reiziger;

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
    public java.sql.Date getGeboortedatum() {
        return (java.sql.Date) geboortedatum;
    }
    public String toString(){
        return "Reiziger: " + this.getNaam() + " met ID: " + this.getId();
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
