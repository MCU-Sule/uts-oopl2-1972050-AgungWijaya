package com.example.squiddemo.model;

import java.text.DecimalFormat;

public class Hutang {
    private int id;
    private String PemberiUtang;
    private String jumlah;
    private Player player;
    private double jumlahTampil;

    public double getJumlahTampil() {
        return jumlahTampil;
    }

    public Hutang() {
    }

    public void setJumlahTampil(double jumlahTampil) {
        this.jumlahTampil = jumlahTampil;
    }

    public Hutang(int id, String pemberiUtang, Double jumlah, Player player) {
        this.id = id;
        PemberiUtang = pemberiUtang;
        this.jumlah = "₩ " +new DecimalFormat("#,000").format(jumlah);
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPemberiUtang() {
        return PemberiUtang;
    }

    public void setPemberiUtang(String pemberiUtang) {
        PemberiUtang = pemberiUtang;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
