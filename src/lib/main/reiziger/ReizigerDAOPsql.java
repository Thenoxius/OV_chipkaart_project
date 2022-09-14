package lib.main.reiziger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    public  void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","oldschool4");
        Connection mycon = DriverManager.getConnection(url, props);
        this.connection = mycon;
    }

    public void closeConnection(Connection mycon) throws SQLException {
        mycon.close();
    }
    public ReizigerDAOPsql(Connection connection){
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try{
            getConnection();
            Statement myStat = connection.createStatement();
            String s = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES(";
            s += "'" + reiziger.getId() + "', '" + reiziger.getVoorletters() + "', '" + reiziger.getTussenvoegsel() + "', '" +
                    reiziger.getAchternaam() + "', '" + reiziger.getGeboortedatum() + "')";
            System.out.println(s);
            myStat.executeUpdate(s);
            closeConnection(connection);
            return true;
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?");
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, (Date) reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());
            ps.executeUpdate();
            closeConnection(connection);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger){
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id= ?");
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM adres WHERE reiziger_id= ?");
            ps.setInt(1, reiziger.getId());
            ps2.setInt(1, reiziger.getId());
            ps2.executeUpdate();
            ps.executeUpdate();
            closeConnection(connection);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        getConnection();
        Statement myStat = connection.createStatement();
        String s = "select * from reiziger WHERE reiziger_id = " + id;
        ResultSet myRs = myStat.executeQuery(s);
        while(myRs.next()){
            int reisid = myRs.getInt("reiziger_id");
            String voornaam= myRs.getString("voorletters");
            String tussenvoegsel = "";
            if (myRs.getString("tussenvoegsel") != null){
                tussenvoegsel+= myRs.getString("tussenvoegsel");
            }
            String achternaam = myRs.getString("achternaam");
            Reiziger findbyid = new Reiziger(reisid, voornaam, tussenvoegsel, achternaam, myRs.getDate("geboortedatum"));
            closeConnection(connection);
            return findbyid;
        }
        closeConnection(connection);
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        getConnection();
        String gbd =  "'" + datum + "'";
        List<Reiziger> results = new ArrayList<>();
        Statement myStat = connection.createStatement();
        String s = "select * from reiziger WHERE geboortedatum = " + gbd;
        ResultSet myRs = myStat.executeQuery(s);
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
        closeConnection(connection);
        return results;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        getConnection();
        List<Reiziger> results = new ArrayList<>();
        Statement myStat = connection.createStatement();
        String s = "select * from reiziger";
        ResultSet myRs = myStat.executeQuery(s);
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
        closeConnection(connection);
        return results;
    }
}
