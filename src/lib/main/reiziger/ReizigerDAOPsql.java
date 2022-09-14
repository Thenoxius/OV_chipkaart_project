package lib.main.reiziger;

import lib.main.adres.Adres;

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
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM adres WHERE reiziger_id= ?");
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
            System.out.println(addToReult);
            results.add(addToReult);
        }
        return results;
    }
}
