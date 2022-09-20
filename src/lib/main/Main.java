package lib.main;

import lib.main.OV_Chipkaart.OVChipkaartDAOPsql;
import lib.main.adres.Adres;
import lib.main.adres.AdresDAOPsql;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String [] args) throws SQLException {
        getConnection();
        ReizigerDAOPsql dao = new ReizigerDAOPsql(connection);
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
        Reiziger reiziger = dao.findById(2);
        System.out.println(dao.findReizigerKaarten(reiziger));
        closeConnection(connection);
    }
    public static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null){
            String url = "jdbc:postgresql://localhost/ovchip";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","oldschool4");
            connection = DriverManager.getConnection(url, props);
        }
        return connection;
    }
    public static void closeConnection(Connection mycon) throws SQLException {
        if (connection != null){
            connection.close();
            connection = null;
        }
    }
    public static void testAdresDAO(){
        try {
            //maak verbinding met database
            ReizigerDAOPsql dao = new ReizigerDAOPsql(getConnection());
            Reiziger één = dao.findById(1);
            AdresDAOPsql dao2 = new AdresDAOPsql(getConnection());
            Adres result = dao2.findByReiziger(één);
            System.out.println(result);
            String gbd = "1991-12-04";
            Reiziger Thomas = new Reiziger(24, "T", "van", "Rens", java.sql.Date.valueOf(gbd));
            dao.save(Thomas);
            Adres nieuwAdres = new Adres(17, "2801NL", "10B", "Keizerstraat", "Gouda", 24);
            //dao2.save(nieuwAdres);
            //dao.delete(Thomas);

        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }
}
