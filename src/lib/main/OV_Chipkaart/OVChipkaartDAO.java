package lib.main.OV_Chipkaart;

import lib.main.adres.Adres;
import lib.main.reiziger.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart chipkaart) throws SQLException;
    public boolean update(OVChipkaart chipkaart);
    public boolean delete(OVChipkaart chipkaart);
    public OVChipkaart findByReiziger(Reiziger reiziger);
    public List<OVChipkaart> findAll() throws SQLException;
}
