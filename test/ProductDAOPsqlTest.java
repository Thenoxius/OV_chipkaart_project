import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.OV_Chipkaart.OVChipkaartDAOPsql;
import lib.main.product.Product;
import lib.main.product.ProductDAOPsql;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOPsqlTest {

    private OVChipkaart testChipkaart;
    private Product testProduct;
    private ReizigerDAOPsql rdao;
    private OVChipkaartDAOPsql ovdao;
    private ProductDAOPsql pdao;

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
        rdao = new ReizigerDAOPsql(getConnection());
        ovdao = new OVChipkaartDAOPsql(getConnection());
        pdao = new ProductDAOPsql(getConnection());
        rdao.setOvdao(ovdao);
        rdao.setPdao(pdao);
        String gbdatum2 = "1991-04-22";
        Reiziger johan = new Reiziger(24, "J", "van", "der Berg", java.sql.Date.valueOf(gbdatum2));
        String datum = "2024-04-22";
        testChipkaart = new OVChipkaart(9999999,  java.sql.Date.valueOf(datum), 1, 50, johan);
        testProduct = new Product(12345, "Test product", "een test van het opslagmechaniek van product", 15);
    }

    @Test
    void save() throws SQLException {
        assertTrue(pdao.save(testProduct));
    }

    @Test
    void update() throws SQLException {
        //creeer een test update met andere waarden maar hetzelde productnummer
        Product updateTest = testProduct;
        updateTest.setNaam("een ander test product");
        updateTest.setPrijs(50);
        //kijk of de update werkt
        assertTrue(pdao.update(updateTest));
        //zet de waarden terug
        pdao.update(testProduct);

    }

    @Test
    void delete() throws SQLException {
        assertTrue(pdao.delete(testProduct));
    }
}
