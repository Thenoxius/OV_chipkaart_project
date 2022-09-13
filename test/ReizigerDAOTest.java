import lib.main.Reiziger;
import lib.main.ReizigerDAO;
import lib.main.ReizigerDAOPsql;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReizigerDAOTest {

    @Test
    public void testGevondenKlantIDEnDaarnaDelete(){
        ReizigerDAOPsql dao = new ReizigerDAOPsql();
        try {
            dao.getConnection();
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
        ReizigerDAOPsql dao = new ReizigerDAOPsql();
        try {
            dao.getConnection();
            String gbdatum2 = "1991-04-22";
            Reiziger jacop = new Reiziger(88, "J", "van den", "Berg", java.sql.Date.valueOf(gbdatum2));
            dao.save(jacop);
            Reiziger jacopMetNieuweAchternaam =  new Reiziger(88, "J", "van het", "hof", java.sql.Date.valueOf(gbdatum2));
            ArrayList<Reiziger> gevondenReizigers = new ArrayList();
            for (Reiziger reiziger :dao.findByGbdatum(gbdatum2)){
                gevondenReizigers.add(reiziger);
            }
            for (Reiziger reiziger : gevondenReizigers){
                assertEquals("Reiziger: J van den Berg met ID: 88", reiziger.toString(), "reiziger niet gevonden");
            }
            dao.delete(jacop);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void updateDeTestReizigerEnDelete(){
        ReizigerDAOPsql dao = new ReizigerDAOPsql();
        try {
            dao.getConnection();
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
