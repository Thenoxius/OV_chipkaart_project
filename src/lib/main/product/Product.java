package lib.main.product;

import java.util.Objects;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private long prijs;
    public Product(int pnr, String nm, String bsv, long prs){
        this.product_nummer=pnr;
        this.naam=nm;
        this.beschrijving=bsv;
        this.prijs=prs;
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
}
