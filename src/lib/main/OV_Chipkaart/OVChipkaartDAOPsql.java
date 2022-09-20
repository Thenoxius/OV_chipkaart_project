package lib.main.OV_Chipkaart;

import lib.main.adres.Adres;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lib.main.Main.closeConnection;
import static lib.main.Main.getConnection;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    public OVChipkaartDAOPsql(Connection connection){this.connection = connection;}
    private Connection connection;
    private ReizigerDAOPsql rdao;

    public ReizigerDAOPsql getRdao() {
        return rdao;
    }
    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }

    public boolean reizigerCheck(int ID) throws SQLException {
        ReizigerDAOPsql dao = new ReizigerDAOPsql(getConnection());
        if (dao.findById(ID) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean save(OVChipkaart chipkaart) throws SQLException {
        if (reizigerCheck(chipkaart.getReiziger_id()) == false){
            System.out.println("er moet een reiziger met dit ID bestaan");
            return false;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.setDate(2, chipkaart.getGeldig_tot());
            ps.setInt(3, chipkaart.getKlasse());
            ps.setLong(4, chipkaart.getSaldo());
            ps.setInt(5, chipkaart.getReiziger_id());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart chipkaart) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?");
            ps.setDate(1, chipkaart.getGeldig_tot());
            ps.setInt(2, chipkaart.getKlasse());
            ps.setLong(3, chipkaart.getSaldo());
            ps.setInt(4, chipkaart.getReiziger_id());
            ps.setInt(5, chipkaart.getKaartnummer());
            ps.executeUpdate();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart chipkaart) {
        try{
            PreparedStatement ps = connection.prepareStatement("DELETE FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OVChipkaart findByReiziger(Reiziger reiziger) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ResultSet myRs = ps.executeQuery();
            while(myRs.next()){
                int kaartNummer = myRs.getInt("kaart_nummer");
                int reisid = myRs.getInt("reiziger_id");
                int klassint = myRs.getInt("klasse");
                Long saldolong = myRs.getLong("saldo");
                OVChipkaart result = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, reisid);
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> results = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart");
        ResultSet myRs = ps.executeQuery();
        while (myRs.next()){
            int kaartNummer = myRs.getInt("kaart_nummer");
            int reisid = myRs.getInt("reiziger_id");
            int klassint = myRs.getInt("klasse");
            Long saldolong = myRs.getLong("saldo");
            OVChipkaart resultOV = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, reisid);
            Reiziger resultR = rdao.findById(reisid);
            System.out.println(resultR);
            System.out.println(resultOV);
            results.add(resultOV);
        }
        return results;
    }
}
