package lib.main;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String [] args){
        try {
            //maak verbinding met database
            Connection mycon = getConnection();
            Statement myStat = mycon.createStatement();
            //voer een query uit
            ResultSet myRs = myStat.executeQuery("select * from reiziger");
            //verwerk resultaten
            ReizigerDAOPsql dao = new ReizigerDAOPsql(mycon);
            String gbdatum2 = "1991-04-22";
            Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            Reiziger pierre = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            System.out.println(jacop.equals(pierre));
            Reiziger gevonden = dao.findById(88);
            dao.delete(jacop);
            System.out.println(gevonden);
        }
        catch (Exception exc){
            exc.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","oldschool4");
        Connection mycon = DriverManager.getConnection(url, props);
        return mycon;
    }
    public void closeConnection(Connection mycon) throws SQLException {
        mycon.close();
    }
}
