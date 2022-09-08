import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String [] args){
        try {
            //maak verbinding met database
            Connection mycon = getConnection();
            //String url = "jdbc:postgresql://localhost/ovchip";
            //Properties props = new Properties();
            //props.setProperty("user","postgres");
            //props.setProperty("password","oldschool4");
            //Connection mycon = DriverManager.getConnection(url, props);
            //maken een statement

            Statement myStat = mycon.createStatement();
            //voer een query uit
            ResultSet myRs = myStat.executeQuery("select * from reiziger");
            //verwerk resultaten
            while(myRs.next()){
                String s = "#" + myRs.getInt("reiziger_id") + ": " + myRs.getString("voorletters") + " ";
                if (myRs.getString("tussenvoegsel") != null){
                    s+= myRs.getString("tussenvoegsel") + " " ;
                }
                s += myRs.getString("achternaam") + " (" + myRs.getDate("geboortedatum") + ")";
                System.out.println(s);
            }
            mycon.close();
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
