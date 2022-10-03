package lib.main.OV_Chipkaart;

import lib.main.product.Product;
import lib.main.product.ProductDAO;
import lib.main.reiziger.Reiziger;
import lib.main.reiziger.ReizigerDAO;
import lib.main.reiziger.ReizigerDAOPsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lib.main.Main.getConnection;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    public OVChipkaartDAOPsql(Connection connection){this.connection = connection;}
    private Connection connection;
    private ReizigerDAO rdao;

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public boolean reizigerCheck(Reiziger reiziger) throws SQLException {
        if (rdao.findById(reiziger.getId()) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean save(OVChipkaart chipkaart) throws SQLException {
        if (reizigerCheck(chipkaart.getReiziger()) == false){
            System.out.println("er moet een reiziger met dit ID bestaan");
            return false;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.setDate(2, chipkaart.getGeldig_tot());
            ps.setInt(3, chipkaart.getKlasse());
            ps.setLong(4, chipkaart.getSaldo());
            ps.setInt(5, chipkaart.getReiziger().getId());
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
            ps.setInt(4, chipkaart.getReiziger().getId());
            ps.setInt(5, chipkaart.getKaartnummer());
            for (Product product : chipkaart.getMijnProducten()){
                ProductDAO pdao = rdao.getPdao();
                pdao.update(product);
            }
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
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<OVChipkaart> results = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
            ps.setInt(1, reiziger.getId());
            ResultSet myRs = ps.executeQuery();
            while(myRs.next()){
                int kaartNummer = myRs.getInt("kaart_nummer");
                int reisid = myRs.getInt("reiziger_id");
                int klassint = myRs.getInt("klasse");
                Long saldolong = myRs.getLong("saldo");
                Reiziger resultreiziger = rdao.findById(reisid);
                OVChipkaart ovresult = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, resultreiziger);
                results.add(ovresult);
            }
            return results;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public OVChipkaart findByKaartNummer(OVChipkaart ovChipkaart) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM ov_chipkaart WHERE kaart_nummer = ?");
            ps.setInt(1, ovChipkaart.getKaartnummer());
            ResultSet myRs = ps.executeQuery();
            while(myRs.next()){
                int kaartNummer = myRs.getInt("kaart_nummer");
                int reisid = myRs.getInt("reiziger_id");
                int klassint = myRs.getInt("klasse");
                Long saldolong = myRs.getLong("saldo");
                Reiziger resultreiziger = rdao.findById(reisid);
                OVChipkaart ovresult = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, resultreiziger);
                return ovresult;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
            Reiziger resultreiziger = rdao.findById(reisid);
            OVChipkaart resultOV = new OVChipkaart(kaartNummer, myRs.getDate("geldig_tot"), klassint, saldolong, resultreiziger);
            Reiziger resultR = rdao.findById(reisid);
            System.out.println(resultR);
            System.out.println(resultOV);
            results.add(resultOV);
        }
        return results;
    }
}
