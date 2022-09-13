package lib.main;

import java.sql.SQLException;

public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }


    public int getAdres_id() {
        return adres_id;
    }

    public void setAdres_id(int adres_id) {
        this.adres_id = adres_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(Integer reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return adres_id == adres.adres_id && reiziger_id == adres.reiziger_id && postcode.equals(adres.postcode) && huisnummer.equals(adres.huisnummer) && straat.equals(adres.straat) && woonplaats.equals(adres.woonplaats);
    }

    public String toString() {
        ReizigerDAOPsql dao = new ReizigerDAOPsql();
        try {
            Reiziger bewoner = dao.findById(getAdres_id());
            return "Reiziger: " + bewoner.getNaam() + " met ID: " + bewoner.getId()  + ", Adres = " + straat + " " + huisnummer + " " + postcode + " " + woonplaats + " met adres ID #" + adres_id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
