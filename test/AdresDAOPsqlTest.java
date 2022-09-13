import lib.main.Adres;
import lib.main.AdresDAOPsql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AdresDAOPsqlTest {
    private Adres nieuwAdres;
    private AdresDAOPsql dao;

    @BeforeEach
    public void init(){
        dao = new AdresDAOPsql();
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
