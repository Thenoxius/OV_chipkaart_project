package lib.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AdresDAOPsql implements AdresDAO{
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
    public AdresDAOPsql(){}

    @Override
    public boolean save(Adres adres) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO  adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES(?, ?, ?, ?, ?, ?) ");
            ps.setInt(1, adres.getAdres_id());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6,adres.getReiziger_id());
            ps.executeUpdate();
            closeConnection(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id WHERE adres_id = ?");
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, adres.getReiziger_id());
            ps.setInt(6, adres.getAdres_id());
            ps.executeUpdate();
            closeConnection(connection);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reiziger WHERE reiziger_id= ?");
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM adres WHERE reiziger_id= ?");
            ps.setInt(1, adres.getReiziger_id());
            ps2.setInt(1, adres.getReiziger_id());
            ps2.executeUpdate();
            ps.executeUpdate();
            closeConnection(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM adres WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ResultSet myRs = ps.executeQuery();
            while(myRs.next()){
                int adresId = myRs.getInt("adres_id");
                int reisid = myRs.getInt("reiziger_id");
                String postcodeString = myRs.getString("postcode");
                String huisnummerString = myRs.getString("huisnummer");
                String straatString = myRs.getString("straat");
                String woonplaatsString = myRs.getString("woonplaats");
                Adres result = new Adres(adresId, postcodeString, huisnummerString, straatString, woonplaatsString, reisid);
                closeConnection(connection);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public List<Adres> findAll() throws SQLException {
        getConnection();
        List<Adres> results = new ArrayList<>();
        Statement myStat = connection.createStatement();
        String s = "select * from adres";
        ResultSet myRs = myStat.executeQuery(s);
        while(myRs.next()){
            int adresId = myRs.getInt("adres_id");
            int reisid = myRs.getInt("reiziger_id");
            String postcodeString = myRs.getString("postcode");
            String huisnummerString = myRs.getString("huisnummer");
            String straatString = myRs.getString("straat");
            String woonplaatsString = myRs.getString("woonplaats");
            Adres result = new Adres(adresId, postcodeString, huisnummerString, straatString, woonplaatsString, reisid);
            System.out.println(result);
            results.add(result);
        }
        closeConnection(connection);
        return results;
    }
}
