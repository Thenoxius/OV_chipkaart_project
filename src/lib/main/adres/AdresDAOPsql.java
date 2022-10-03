package lib.main.adres;

import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static lib.main.Main.closeConnection;
import static lib.main.Main.getConnection;

public class AdresDAOPsql implements AdresDAO{
    public AdresDAOPsql(Connection connection){
        this.connection = connection;
    }
    private Connection connection;

    public ReizigerDAO getRdao() {
        return rdao;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    private ReizigerDAO rdao;

    public boolean reizigerCheck(int ID) throws SQLException {
        ReizigerDAOPsql dao = new ReizigerDAOPsql(getConnection());
        if (dao.findById(ID) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        Reiziger deReiziger = adres.getReiziger();
        if (reizigerCheck(deReiziger.getId()) == false){
            System.out.println("er moet een reiziger met dit ID bestaan");
            return false;
        }
        try{
            PreparedStatement ps = connection.prepareStatement("INSERT INTO  adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES(?, ?, ?, ?, ?, ?) ");
            ps.setInt(1, adres.getAdres_id());
            ps.setString(2, adres.getPostcode());
            ps.setString(3, adres.getHuisnummer());
            ps.setString(4, adres.getStraat());
            ps.setString(5, adres.getWoonplaats());
            ps.setInt(6,deReiziger.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Adres adres) {
        Reiziger deReiziger = adres.getReiziger();
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?");
            ps.setString(1, adres.getPostcode());
            ps.setString(2, adres.getHuisnummer());
            ps.setString(3, adres.getStraat());
            ps.setString(4, adres.getWoonplaats());
            ps.setInt(5, deReiziger.getId());
            ps.setInt(6, adres.getAdres_id());
            ps.executeUpdate();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Adres adres) {
        Reiziger deReiziger = adres.getReiziger();
        try {
            PreparedStatement ps2 = connection.prepareStatement("DELETE FROM adres WHERE reiziger_id= ?");
            ps2.setInt(1, deReiziger.getId());
            ps2.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
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
                Adres result = new Adres(adresId, postcodeString, huisnummerString, straatString, woonplaatsString, reiziger);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public List<Adres> findAll() throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        List<Adres> results = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM adres");
        ResultSet myRs = ps.executeQuery();
        while(myRs.next()){
            int adresId = myRs.getInt("adres_id");
            int reisid = myRs.getInt("reiziger_id");
            String postcodeString = myRs.getString("postcode");
            String huisnummerString = myRs.getString("huisnummer");
            String straatString = myRs.getString("straat");
            String woonplaatsString = myRs.getString("woonplaats");
            Reiziger reiziger = rdao.findById(reisid);
            Adres resultA = new Adres(adresId, postcodeString, huisnummerString, straatString, woonplaatsString, reiziger);
            Reiziger resultR = rdao.findById(reisid);
            String resultString = resultR.toString();
            resultString += resultA.toString();
            System.out.println(resultString);
            results.add(resultA);
        }
        return results;
    }
}
