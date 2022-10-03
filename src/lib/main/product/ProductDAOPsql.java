package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    public ProductDAOPsql(Connection connection){this.connection = connection;}
    private Connection connection;
    private ReizigerDAO rdao;
    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }
    public boolean save(OVChipkaart chipkaart) throws SQLException {return true;}
    public boolean update(OVChipkaart chipkaart){return true;}
    public boolean delete(OVChipkaart chipkaart){return true;}
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List results = new ArrayList();
        return results;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean delete(Product product) {
        return false;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        return null;
    }

    public List<Product> findAll() throws SQLException{
        List results = new ArrayList();
        return results;
    }
}
