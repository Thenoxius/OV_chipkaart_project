package lib.main.reiziger;

import lib.main.OV_Chipkaart.OVChipkaart;
import lib.main.OV_Chipkaart.OVChipkaartDAO;
import lib.main.OV_Chipkaart.OVChipkaartDAOPsql;
import lib.main.adres.AdresDAO;
import lib.main.adres.AdresDAOPsql;
import lib.main.product.ProductDAO;
import lib.main.product.ProductDAOPsql;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO{
    public boolean save(Reiziger reiziger) throws SQLException;
    public boolean update(Reiziger reiziger) throws SQLException;
    public boolean delete(Reiziger reiziger) throws SQLException;
    public Reiziger findById(int id) throws SQLException;
    public List<Reiziger> findByGbdatum(String datum) throws SQLException;
    public List<Reiziger> findAll() throws SQLException;

    void setAdao(AdresDAO adao);

    void setOvdao(OVChipkaartDAO ovdao);

    void setPdao(ProductDAO pdao);

    public List<OVChipkaart>  findReizigerKaarten(Reiziger reiziger);
}
