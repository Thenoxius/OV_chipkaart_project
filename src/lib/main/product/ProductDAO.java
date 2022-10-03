package lib.main.product;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    public boolean save(Product product) throws SQLException;
    public boolean update(Product product);
    public boolean delete(Product product);
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart);
    public List<Product> findAll() throws SQLException;
    public void setRdao(ReizigerDAO rdao);
}
