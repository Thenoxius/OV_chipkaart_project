import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.OV_Chipkaart.OVChipkaartDAOPsql;
import lib.main.adres.Adres;
import lib.main.adres.AdresDAOPsql;
import lib.main.reiziger.Reiziger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class OVChipkaartDAOPsqlTest {

    private OVChipkaart testChipkaart;
    private OVChipkaartDAOPsql dao;

    private static Connection connection;
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
        dao = new OVChipkaartDAOPsql(getConnection());
        String gbdatum2 = "1991-04-22";
        Reiziger johan = new Reiziger(24, "J", "van", "der Berg", java.sql.Date.valueOf(gbdatum2));
        String datum = "2024-04-22";
        testChipkaart = new OVChipkaart(9999999,  java.sql.Date.valueOf(datum), 1, 50, johan);

    }

    // aangezien er daadwerkelijk een object aangemaakt wordt, moeten alle testen samen uitgevoerd worden
    @Test
    void saveTest() throws SQLException {
        assertTrue(dao.save(testChipkaart));
    }

    @Test
    void updateTest() {
        testChipkaart.setKlasse(2);
        assertTrue(dao.update(testChipkaart));
        testChipkaart.setKlasse(1);
        assertTrue(dao.update(testChipkaart));
    }

    @Test
    void deleteTest() {
        assertTrue(dao.delete(testChipkaart));
    }

}
