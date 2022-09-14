import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ReizigerDAOTest {
    private static Connection connection;
    private ReizigerDAOPsql dao;

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

    @BeforeEach
    public void init() throws SQLException {
        dao = new ReizigerDAOPsql(getConnection());
    }

    @Test
    public void deleteJacopDeReiziger(){
        String gbdatum2 = "1991-04-22";
        Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
        dao.delete(jacop);
    }

    @Test
    public void testGevondenKlantIDEnDaarnaDelete(){
        try {
            String gbdatum2 = "1991-04-22";
            Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            dao.save(jacop);
            Reiziger gevonden = dao.findById(88);
            assertEquals("Reiziger: J van den Berg met ID: 88", gevonden.toString(), "reiziger niet gevonden");
            dao.delete(jacop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testGevondenKlantGbdEnDaarnaDelete(){
        try {
            String gbdatum2 = "1991-04-22";
            Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            dao.save(jacop);
            ArrayList<Reiziger> gevondenReizigers = new ArrayList();
            for (Reiziger reiziger :dao.findByGbdatum(gbdatum2)){
                gevondenReizigers.add(reiziger);
            }
            for (Reiziger reiziger : gevondenReizigers){
                System.out.println(reiziger);;
            }
            dao.delete(jacop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void updateDeTestReizigerEnDelete(){
        try {
            String gbdatum2 = "1991-04-22";
            Reiziger jacop = new Reiziger(77, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            dao.save(jacop);
            System.out.println(jacop);
            Reiziger jacopMetNieuweAchternaam =  new Reiziger(77, "J", "van het", "hof", java.sql.Date.valueOf(gbdatum2));
            dao.update(jacopMetNieuweAchternaam);
            System.out.println("nu halen we de nieuwe jacop uit de DB");
            Reiziger jacopuitdatabase = dao.findById(77);
            System.out.println(jacopuitdatabase);
            assertTrue(jacopuitdatabase.equals(jacopMetNieuweAchternaam), "dit zijn niet dezelfde objecten");
            assertFalse(jacop.equals(jacopMetNieuweAchternaam), "de achternaam is niet verandert");
            dao.delete(jacopMetNieuweAchternaam);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        String gbdatum2 = "1991-04-22";
        Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        rdao.save(jacop);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

}
