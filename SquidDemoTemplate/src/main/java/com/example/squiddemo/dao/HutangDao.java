package com.example.squiddemo.dao;

import com.example.squiddemo.model.Hutang;
import com.example.squiddemo.model.Player;
import com.example.squiddemo.utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HutangDao implements daoInterface<Hutang>{
    @Override
    public int addData(Hutang data) {
        int result=0;
        try {
            Connection con;
            con= JDBCConnection.getConnection();
            con.setAutoCommit(false);
            String query="INSERT INTO hutang (PemberiUtang,Jumlah,Player_id) VALUES (?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,data.getPemberiUtang());
            ps.setDouble(2,data.getJumlahTampil());
            ps.setInt(3,data.getPlayer().getId());
            result=ps.executeUpdate();
            if (result !=0){
                con.commit();
            }else{
                con.rollback();
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int delData(Hutang data) {
        int result=0;
        try {
            Connection con;
            con= JDBCConnection.getConnection();
            con.setAutoCommit(false);
            String query="DELETE FROM hutang where id=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,data.getId());
            result=ps.executeUpdate();
            if (result !=0){
                con.commit();
            }else{
                con.rollback();
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(Hutang data) {
        return 0;
    }

    @Override
    public ObservableList<Hutang> showData() {
        ObservableList<Hutang> hList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM hutang JOIN player on hutang.Player_id=player.id";
            PreparedStatement ps;
            ps = JDBCConnection.getConnection().prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String pemberiHutang = res.getString("PemberiUtang");
                Double jumlah=res.getDouble("Jumlah");
                Player p = new Player(res.getInt("Player_id"),res.getString("Nama"),res.getInt("Umur"),res.getString("Keahlian"));
                hList.add(new Hutang(id,pemberiHutang,jumlah,p));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return hList;
    }
}
