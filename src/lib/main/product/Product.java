package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private long prijs;
    private Status status;
    public String getStatus(){
        return status.toString();
    }

    public Product(int pnr, String nm, String bsv, long prs){
        this.product_nummer=pnr;
        this.naam=nm;
        this.beschrijving=bsv;
        this.prijs=prs;
    }
    private List<OVChipkaart> ovKaartenMetProduct = new ArrayList<>();
    public List<OVChipkaart> getChipkaarten(){
        return ovKaartenMetProduct;
    }
    public void addOvChipkaart(OVChipkaart ovChipkaart){
        if (!ovKaartenMetProduct.contains(ovChipkaart)){
            ovKaartenMetProduct.add(ovChipkaart);
            ovChipkaart.addToProducten(this);
        }
    }
    public void removeOvChipkaart(OVChipkaart ovChipkaart){
        if (ovKaartenMetProduct.contains(ovChipkaart)){
            ovKaartenMetProduct.remove(ovChipkaart);
            ovChipkaart.removeProducten(this);
        }
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public long getPrijs() {
        return prijs;
    }

    public void setPrijs(long prijs) {
        this.prijs = prijs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return product_nummer == product.product_nummer && prijs == product.prijs && Objects.equals(naam, product.naam) && Objects.equals(beschrijving, product.beschrijving);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_nummer, naam, beschrijving, prijs);
    }

    public String toString(){
        return "Product: #" + product_nummer + " naam: " + naam + "\n" + beschrijving + "\nPrijs: €" + prijs+ ".-\n";
    }
}
