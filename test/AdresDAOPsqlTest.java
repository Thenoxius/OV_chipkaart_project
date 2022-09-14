import lib.main.adres.Adres;
import lib.main.adres.AdresDAOPsql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AdresDAOPsqlTest {
    private Adres nieuwAdres;
    private AdresDAOPsql dao;

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
        dao = new AdresDAOPsql(getConnection());
        nieuwAdres = new Adres(17, "2801NL", "10B", "Keizerstraat", "Gouda", 24);
    }

    @Test
    public void testSaveAdres() throws SQLException {
        assertTrue(dao.save(nieuwAdres));
    }
    @Test
    public void testUpdateAdres(){
        nieuwAdres.setPostcode("2802DL");
        assertTrue(dao.update(nieuwAdres));
        nieuwAdres.setPostcode("2801NL");
        dao.update(nieuwAdres);
    }

    @Test
    public void testDeleteAdres(){
        assertTrue(dao.delete(nieuwAdres));
    }
}
