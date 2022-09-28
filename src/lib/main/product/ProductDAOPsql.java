package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql {
    public ProductDAOPsql(Connection connection){this.connection = connection;}
    private Connection connection;
    private ReizigerDAOPsql rdao;
    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }
    public boolean save(OVChipkaart chipkaart) throws SQLException {return true;}
    public boolean update(OVChipkaart chipkaart){return true;}
    public boolean delete(OVChipkaart chipkaart){return true;}
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List results = new ArrayList();
        return results;
    }
    public List<OVChipkaart> findAll() throws SQLException{
        List results = new ArrayList();
        return results;
    }
}
