package lib.main.adres;

import lib.main.reiziger.Reiziger;

import java.util.Objects;

public class Adres {
    private int adres_id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Reiziger reiziger;

    public Adres(int adres_id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger) {
        this.adres_id = adres_id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
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
    public Reiziger getReiziger(){
        return reiziger;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adres adres = (Adres) o;
        return adres_id == adres.adres_id && Objects.equals(postcode, adres.postcode) && Objects.equals(huisnummer, adres.huisnummer) && Objects.equals(straat, adres.straat) && Objects.equals(woonplaats, adres.woonplaats) && Objects.equals(reiziger, adres.reiziger);
    }

    public String toString() {
        return ", Adres = " + straat + " " + huisnummer + " " + postcode + " " + woonplaats + " met adres ID #" + adres_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adres_id, postcode, huisnummer, straat, woonplaats, reiziger);
    }
}

