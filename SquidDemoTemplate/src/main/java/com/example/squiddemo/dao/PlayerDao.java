package com.example.squiddemo.dao;

import com.example.squiddemo.model.Player;
import com.example.squiddemo.utility.JDBCConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDao implements daoInterface<Player>{
    @Override
    public int addData(Player data) {
        int result=0;
        try {
            Connection con;
            con= JDBCConnection.getConnection();
            con.setAutoCommit(false);
            String query="INSERT INTO player (id,Nama,Umur,Keahlian) VALUES (?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1,data.getId());
            ps.setString(2,data.getNama());
            ps.setInt(3,data.getUmur());
            ps.setString(4,data.getKeahlian());
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
    public int delData(Player data) {
        int result=0;
        try {
            Connection con;
            con= JDBCConnection.getConnection();
            con.setAutoCommit(false);
            String query="DELETE FROM player where id=?";
            String queryhutang="DELETE FROM hutang where Player_id=?";
            PreparedStatement ps=con.prepareStatement(queryhutang);
            ps.setInt(1,data.getId());
            result=ps.executeUpdate();
            if (result !=0){
                con.commit();
                ps=con.prepareStatement(query);
                ps.setInt(1,data.getId());
                result=ps.executeUpdate();
                if (result!=0){
                    con.commit();
                }else{
                    con.rollback();
                }
            }else{
                con.commit();
                ps=con.prepareStatement(query);
                ps.setInt(1,data.getId());
                result=ps.executeUpdate();
                if (result!=0){
                    con.commit();
                }else{
                    con.rollback();
                }
            }

        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int updateData(Player data) {
        int result=0;
        try {
            Connection con;
            con= JDBCConnection.getConnection();
            con.setAutoCommit(false);
            String query="UPDATE player SET Nama=?,Umur=?,Keahlian=?WHERE id=?";
            PreparedStatement ps=con.prepareStatement(query);
            ps.setString(1,data.getNama());
            ps.setInt(2,data.getUmur());
            ps.setString(3,data.getKeahlian());
            ps.setInt(4,data.getId());
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
    public ObservableList<Player> showData() {
        ObservableList<Player> pList = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM player";
            PreparedStatement ps;
            ps = JDBCConnection.getConnection().prepareStatement(query);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("Nama");
                int umur=res.getInt("Umur");
                String keahlian = res.getString("Keahlian");
                pList.add(new Player(id,name,umur,keahlian));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return pList;
    }
}
