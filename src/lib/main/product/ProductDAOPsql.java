package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.OV_Chipkaart.OVChipkaartDAO;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    public ProductDAOPsql(Connection connection){this.connection = connection;}
    private Connection connection;
    private ReizigerDAO rdao;

    @Override
    public boolean save(Product product) throws SQLException {
        try{
            List<Product> alleproducten = findAll();
            if (alleproducten.contains(product)){
                return false;
            }
            PreparedStatement ps = connection.prepareStatement("INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)");
            ps.setInt(1, product.getProduct_nummer());
            ps.setString(2, product.getNaam());
            ps.setString(3, product.getBeschrijving());
            ps.setLong(4, product.getPrijs());
            ps.executeUpdate();
            OVChipkaartDAO ovdao = rdao.getOvdao();
            for (OVChipkaart chip : product.getChipkaarten()){
                ovdao.save(chip);
            }
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) throws SQLException {
        try{
            List<Product> resultlijst = findAll();
            if (!resultlijst.contains(product)){
                PreparedStatement ps = connection.prepareStatement("UPDATE product SET naam=?, beschrijving=?, prijs=? WHERE product_nummer=?");
                ps.setString(1, product.getNaam());
                ps.setString(2, product.getBeschrijving());
                ps.setLong(3, product.getPrijs());
                ps.setInt(4, product.getProduct_nummer());
                OVChipkaartDAO ovdao = rdao.getOvdao();
                for (OVChipkaart chip : product.getChipkaarten()){
                    if (chipkaartInGebruik(chip, product) == true){
                        PreparedStatement ps2 = connection.prepareStatement("UPDATE ov_chipkaart_product SET last_update=?, status=? WHERE product_nummer=? AND kaart_nummer=?");
                        Date date = Date.valueOf(LocalDate.now());
                        ps2.setDate(1, date);
                        ps2.setString(2, product.getStatus());
                        ps2.setInt(3, product.getProduct_nummer());
                        ps2.setInt(4, chip.getKaartnummer());
                    }
                    if (!chip.equals(ovdao.findByKaartNummer(chip))){
                        ovdao.update(chip);
                    }
                }
                ps.executeUpdate();
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(Product product) throws SQLException {
        if (productInGebruik(product) == true){
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
            ps.setInt(1, product.getProduct_nummer());
            ps.executeUpdate();
        }
        PreparedStatement ps2 = connection.prepareStatement("DELETE FROM product WHERE product_nummer = ?");
        ps2.setInt(1, product.getProduct_nummer());
        ps2.executeUpdate();
        return true;
    }

    public boolean productInGebruik(Product product) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT product_nummer FROM product");
        ResultSet myRs = ps.executeQuery();
        while (myRs.next()){
            int product_nummer = myRs.getInt("product_nummer");
            if (product.getProduct_nummer() == product_nummer){
                return true;
            }
        }
        return false;
    }

    public boolean chipkaartInGebruik(OVChipkaart chipkaart, Product product) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT product_nummer, kaart_nummer FROM ov_chipkaart_product");
        ResultSet myRs = ps.executeQuery();
        while (myRs.next()){
            int product_nummer = myRs.getInt("product_nummer");
            int kaart_nummer = myRs.getInt("kaart_nummer");
            if (product.getProduct_nummer() == product_nummer && chipkaart.getKaartnummer() == kaart_nummer){
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        List<Integer> productnummers = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT product_nummer FROM ov_chipkaart_product WHERE kaart_nummer = ?");
        ps.setInt(1, ovChipkaart.getKaartnummer());
        ResultSet myRs = ps.executeQuery();
        while (myRs.next()) {
            int product_nummer = myRs.getInt("product_nummer");
            productnummers.add(product_nummer);
        }
        List<Product> results = new ArrayList<>();
        PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM product WHERE product_nummer =?");
        for (Integer i : productnummers){
            ps2.setInt(1, i);
            ResultSet myRs2 = ps.executeQuery();
            while (myRs2.next()) {
                int product_nummer = myRs2.getInt("product_nummer");
                String product_naam = myRs2.getString("naam");
                String beschrijving = myRs2.getString("beschrijving");
                Long prijs = myRs2.getLong("prijs");
                Product result_Product = new Product(product_nummer, product_naam, beschrijving, prijs);
                results.add(result_Product);
            }
        }
        return results;
    }

    public List<Product> findAll() throws SQLException{
        List<Product> results = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM product");
        ResultSet myRs = ps.executeQuery();
        while (myRs.next()){
            int product_nummer = myRs.getInt("product_nummer");
            String product_naam = myRs.getString("naam");
            String beschrijving = myRs.getString("beschrijving");
            Long prijs = myRs.getLong("prijs");
            Product result_Product = new Product(product_nummer, product_naam, beschrijving, prijs);
            PreparedStatement psproducten = connection.prepareStatement("SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer = ?");
            psproducten.setInt(1, result_Product.getProduct_nummer());
            ResultSet myRsproduct = psproducten.executeQuery();
            List<Integer> ovkaartnummers = new ArrayList<>();
            while (myRsproduct.next()){
                int ovkaart_nummer = myRs.getInt("kaart_nummer");
                ovkaartnummers.add(ovkaart_nummer);
            }
            for (Integer e : ovkaartnummers){
                PreparedStatement ovophalen = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
                ovophalen.setInt(1, e);
                ResultSet ovchipkaartresultaten = ovophalen.executeQuery();
                while (ovchipkaartresultaten.next()){
                    int kaartNummer = myRs.getInt("kaart_nummer");
                    int reisid = myRs.getInt("reiziger_id");
                    int klassint = myRs.getInt("klasse");
                    Long saldolong = myRs.getLong("saldo");
                    Reiziger resultreiziger = rdao.findById(reisid);
                    OVChipkaart resultOV = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, resultreiziger);
                    result_Product.addOvChipkaart(resultOV);
                }
            }
            results.add(result_Product);
        }

        return results;
    }

    @Override
    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }
}
