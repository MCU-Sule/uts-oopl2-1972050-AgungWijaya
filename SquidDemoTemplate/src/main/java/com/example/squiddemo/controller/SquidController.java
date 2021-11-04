package com.example.squiddemo.controller;

import com.example.squiddemo.SquidApplication;
import com.example.squiddemo.dao.HutangDao;
import com.example.squiddemo.dao.PlayerDao;
import com.example.squiddemo.model.Hutang;
import com.example.squiddemo.model.Player;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class SquidController {
    public TableView<Hutang> tableHutang;
    public TableView<Player> tablePemain;
    public TableColumn<String, Player> column1;
    public TableColumn<String, Player> column2;
    public TableColumn<String, Player> column3;
    public TableColumn<String, Player> column4;

    public TableColumn<String, Hutang> columnH1;
    public TableColumn<String, Hutang> columnH2;
    public TableColumn<String, Hutang> columnH3;

    private final Player selected=new Player();
    private Hutang selectedH=new Hutang();

    public Player getSelected() {
        return selected;
    }
    public boolean add=false;
    public ComboBox<Player> cmbPeserta;
    public TextField txtPemberiUtang;
    public TextField txtJumlah;
    private final PlayerDao playerDao=new PlayerDao();
    private final HutangDao hutangDao=new HutangDao();

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public HutangDao getHutangDao() {
        return hutangDao;
    }

    public void initialize(){
        tablePemain.setItems(playerDao.showData());
        tableHutang.setItems(hutangDao.showData());
        cmbPeserta.setItems(playerDao.showData());
        cmbPeserta.getSelectionModel().select(0);
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2.setCellValueFactory(new PropertyValueFactory<>("nama"));
        column3.setCellValueFactory(new PropertyValueFactory<>("umur"));
        column4.setCellValueFactory(new PropertyValueFactory<>("Keahlian"));
        columnH1.setCellValueFactory(new PropertyValueFactory<>("player"));
        columnH2.setCellValueFactory(new PropertyValueFactory<>("PemberiUtang"));
        columnH3.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
        tablePemain.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (tablePemain.getSelectionModel().getSelectedItem() != null) {
                    selected.setId(tablePemain.getSelectionModel().getSelectedItem().getId());
                    selected.setNama(tablePemain.getSelectionModel().getSelectedItem().getNama());
                    selected.setUmur(tablePemain.getSelectionModel().getSelectedItem().getUmur());
                    selected.setKeahlian(tablePemain.getSelectionModel().getSelectedItem().getKeahlian());
                }
            }
        });
        tableHutang.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                if (tableHutang.getSelectionModel().getSelectedItem() != null) {
                    selectedH.setId(tableHutang.getSelectionModel().getSelectedItem().getId());
                }
            }
        });
    }
    @FXML
    private void addPemain() throws IOException {
        add=true;
        Stage new_stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(SquidApplication.class.getResource("ModalPage.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        StageModalController sc =fxmlLoader.getController();
        sc.setSquidController(this);
        new_stage.initModality(Modality.WINDOW_MODAL);
        new_stage.initOwner(tablePemain.getScene().getWindow());
        new_stage.setScene(scene);
        new_stage.show();
    }
    @FXML
    private void hapusPemain(){
        playerDao.delData(selected);
        tablePemain.getItems().clear();
        tablePemain.setItems(playerDao.showData());
        tablePemain.refresh();

    }
    @FXML
    private void editPemain() throws IOException {
        add=false;
        if (!selected.getKeahlian().equals("")){
            Stage new_stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(SquidApplication.class.getResource("ModalPage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            StageModalController sc =fxmlLoader.getController();
            sc.setSquidController(this);
            new_stage.initModality(Modality.WINDOW_MODAL);
            new_stage.initOwner(tablePemain.getScene().getWindow());
            new_stage.setScene(scene);
            new_stage.show();
        }else{
            showAlert("Silahkan Pilih Pemain yang akan di edit terlebih dahulu");
        }

    }
    @FXML
    private void addHutang(){
        Hutang h=new Hutang();
        h.setJumlahTampil(Double.parseDouble(txtJumlah.getText()));
        h.setPemberiUtang(txtPemberiUtang.getText());
        h.setPlayer(cmbPeserta.getSelectionModel().getSelectedItem());
        hutangDao.addData(h);
        tableHutang.getItems().clear();
        tableHutang.setItems(hutangDao.showData());
        tableHutang.refresh();
    }
    @FXML
    private void hapusHutang(){
        hutangDao.delData(selectedH);
        tableHutang.getItems().clear();
        tableHutang.setItems(hutangDao.showData());
        tableHutang.refresh();

    }
    public void showAlert(String kalimat){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Information");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.show();
        alert.setContentText(kalimat);
    }
}
