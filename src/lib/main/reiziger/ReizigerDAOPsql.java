package lib.main.reiziger;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.OV_Chipkaart.OVChipkaartDAO;
import lib.main.OV_Chipkaart.OVChipkaartDAOPsql;
import lib.main.adres.Adres;
import lib.main.adres.AdresDAO;
import lib.main.adres.AdresDAOPsql;
import lib.main.product.ProductDAO;
import lib.main.product.ProductDAOPsql;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static lib.main.Main.closeConnection;
import static lib.main.Main.getConnection;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;
    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }
    private AdresDAO adao;
    private OVChipkaartDAO ovdao;
    private ProductDAO pdao;
    public void setAdao(AdresDAO adao){
        this.adao = adao;
        adao.setRdao(this);
    }
    public void setOvdao(OVChipkaartDAO ovdao){
        this.ovdao = ovdao;
        ovdao.setRdao(this);
    }
    public void setPdao(ProductDAO pdao){
        this.pdao = pdao;
        pdao.setRdao(this);
    }
    public ProductDAO getPdao(){
        return pdao;
    }
    public OVChipkaartDAO getOvdao(){
        return ovdao;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO  reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES(?, ?, ?, ?, ?, ?) ");
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());
            ps.executeUpdate();
            if (reiziger.getMijnAdres() != null){
                if (adao.findByReiziger(reiziger) == null){
                    adao.save(reiziger.getMijnAdres());
                }
            }
            if (reiziger.getMijnChipkaarten() != null){
                for (OVChipkaart chip : ovdao.findByReiziger(reiziger)){
                    if (!reiziger.getMijnChipkaarten().contains(chip)){
                        ovdao.save(chip);
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());
            ps.executeUpdate();
            if (reiziger.getMijnAdres() != null){
                if (adao.findByReiziger(reiziger) == null){
                    adao.save(reiziger.getMijnAdres());
                } else {
                    Adres adrescheck = adao.findByReiziger(reiziger);
                    if (!adrescheck.equals(reiziger.getMijnAdres())){
                        adao.update(reiziger.getMijnAdres());
                    }
                }
            }
            if (reiziger.getMijnChipkaarten() != null){
                for (OVChipkaart chip : ovdao.findByReiziger(reiziger)){
                    for (OVChipkaart chipuitlijst : reiziger.getMijnChipkaarten()){
                        if (chip.getKaartnummer() == chipuitlijst.getKaartnummer()){
                            if (!chip.equals(chipuitlijst)){
                                ovdao.update(chipuitlijst);
                            }
                        }
                    }
                }
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger){
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id= ?");
            if (reiziger.getMijnAdres() != null){
                adao.delete(reiziger.getMijnAdres());
            }
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM adres WHERE reiziger_id= ?");
            if (!reiziger.getMijnChipkaarten().isEmpty()){
                for (OVChipkaart chip : reiziger.getMijnChipkaarten()){
                    ovdao.delete(chip);
                }
            }
            ps.setInt(1, reiziger.getId());
            ps2.setInt(1, reiziger.getId());
            ps2.executeUpdate();
            ps.executeUpdate();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from reiziger WHERE reiziger_id = ?");
        ps.setInt(1, id);
        ResultSet myRs = ps.executeQuery();
        while(myRs.next()){
            int reisid = myRs.getInt("reiziger_id");
            String voornaam= myRs.getString("voorletters");
            String tussenvoegsel = "";
            if (myRs.getString("tussenvoegsel") != null){
                tussenvoegsel+= myRs.getString("tussenvoegsel");
            }
            String achternaam = myRs.getString("achternaam");
            Reiziger findbyid = new Reiziger(reisid, voornaam, tussenvoegsel, achternaam, myRs.getDate("geboortedatum"));
            Adres adresvanreiziger = adao.findByReiziger(findbyid);
            findbyid.setAdres(adresvanreiziger);
            return findbyid;
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> results = new ArrayList<>();
        Date geboortedaum = java.sql.Date.valueOf(datum);
        PreparedStatement ps = connection.prepareStatement("select * from reiziger WHERE geboortedatum = ?");
        ps.setDate(1, geboortedaum);
        return getReizigers(ps, results);
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from reiziger");
        List<Reiziger> results = new ArrayList<>();
        return getReizigers(ps, results);
    }


    public List<OVChipkaart> findReizigerKaarten(Reiziger reiziger){
        return ovdao.findByReiziger(reiziger);
    }

    private List<Reiziger> getReizigers(PreparedStatement ps, List<Reiziger> results) throws SQLException {
        ResultSet myRs = ps.executeQuery();
        while(myRs.next()){
            int reisid = myRs.getInt("reiziger_id");
            String voornaam= myRs.getString("voorletters");
            String tussenvoegsel = "";
            if (myRs.getString("tussenvoegsel") != null){
                tussenvoegsel+= myRs.getString("tussenvoegsel");
            }
            String achternaam = myRs.getString("achternaam");
            Reiziger addToReult = new Reiziger(reisid, voornaam, tussenvoegsel, achternaam, myRs.getDate("geboortedatum"));
            Adres reizigeradres = adao.findByReiziger(addToReult);
            addToReult.setAdres(reizigeradres);
            results.add(addToReult);
        }
        return results;
    }
}
