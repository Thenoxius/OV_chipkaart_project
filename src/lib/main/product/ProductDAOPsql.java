package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.reiziger.Reiziger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql {
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