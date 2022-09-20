package lib.main.OV_Chipkaart;

import java.util.Date;
import java.util.Objects;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig_tot;
    private int klasse;
    private long saldo;
    private int reiziger_id;
    public OVChipkaart(int kaartnummer, Date geldig_tot, int klasse, long saldo, int reiziger_id){
        this.kaartnummer=kaartnummer;
        this.geldig_tot=geldig_tot;
        this.klasse=klasse;
        this.saldo=saldo;
        this.reiziger_id=reiziger_id;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }


    public java.sql.Date getGeldig_tot() {
        return (java.sql.Date)  geldig_tot;
    }


    public int getKlasse() {
        return klasse;
    }


    public long getSaldo() {
        return saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaartnummer == that.kaartnummer && klasse == that.klasse && saldo == that.saldo && reiziger_id == that.reiziger_id && Objects.equals(geldig_tot, that.geldig_tot);
    }
    public String toString(){
        return kaartnummer + " is geldig tot " + geldig_tot + " en bevat een saldo van €" + saldo + " en is te grbuiken voor klasse " + klasse;
    }

}
