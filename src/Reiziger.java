import java.util.Date;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    public Reiziger(int id, String vl, String ach, Date gb){
        this.id = id;
        this.voorletters = vl;
        this.achternaam = ach;
        this.geboortedatum = gb;
    }
    public Reiziger(int id,String vl, String ach, Date gb, String tv){
        this.id = id;
        this.voorletters = vl;
        this.achternaam = ach;
        this.geboortedatum = gb;
        this.tussenvoegsel = tv;
    }

    public int getId(){
        return id;
    }

    public void setId(int nieuwId){
        id = nieuwId;
    }
    public String getNaam(){
        if (tussenvoegsel != null){
            return voorletters + " " + tussenvoegsel + " " + achternaam;
        }
        else {
            return voorletters + " " + achternaam;
        }
    }


}
