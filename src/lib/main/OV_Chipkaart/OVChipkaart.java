package lib.main.OV_Chipkaart;

import lib.main.product.Product;
import lib.main.reiziger.Reiziger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class OVChipkaart {
    private int kaartnummer;
    private Date geldig_tot;
    private int klasse;
    private long saldo;
    private Reiziger mijnReiziger;
    private List<Product> mijnProducten = new ArrayList<>();
    public void addToProducten(Product product){
        if (!mijnProducten.contains(product)){
            mijnProducten.add(product);
            product.addOvChipkaart(this);
        }
    }
    public void removeProducten(Product product){
        if (mijnProducten.contains(product)){
            mijnProducten.remove(product);
            product.removeOvChipkaart(this);
        }
    }

    public List<Product> getMijnProducten(){
        return mijnProducten;
    }
    public OVChipkaart(int kaartnummer, Date geldig_tot, int klasse, long saldo, Reiziger reiziger){
        this.kaartnummer=kaartnummer;
        this.geldig_tot=geldig_tot;
        this.klasse=klasse;
        this.saldo=saldo;
        this.mijnReiziger=reiziger;
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
    public void setKlasse(int klasse){
        this.klasse=klasse;
    }


    public long getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return mijnReiziger;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaartnummer == that.kaartnummer && klasse == that.klasse && saldo == that.saldo && Objects.equals(geldig_tot, that.geldig_tot) && Objects.equals(mijnReiziger, that.mijnReiziger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kaartnummer, geldig_tot, klasse, saldo, mijnReiziger);
    }

    public String toString(){
        return kaartnummer + " is geldig tot " + geldig_tot + " en bevat een saldo van €" + saldo + " en is te grbuiken voor klasse " + klasse + " met producten:\n " + mijnProducten + "\n van reiziger: " + mijnReiziger + "\n" + mijnReiziger.getMijnAdres() + "\n";
    }

}
