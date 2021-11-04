package com.example.squiddemo.controller;

import com.example.squiddemo.dao.PlayerDao;
import com.example.squiddemo.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class StageModalController {
    public TextField txtId;
    public TextField txtNama;
    public TextField txtUmar;
    public TextField txtKeahlian;
    private SquidController squidController;
    private String query="add";
    private PlayerDao playerDao=new PlayerDao();

    public void setSquidController(SquidController squidController) {
        this.squidController = squidController;
        if (!squidController.add){
            if (!squidController.getSelected().getNama().equals("")){
                txtId.setDisable(true);
                txtId.setText(String.valueOf(squidController.getSelected().getId()));
                txtNama.setText(String.valueOf(squidController.getSelected().getNama()));
                txtUmar.setText(String.valueOf(squidController.getSelected().getUmur()));
                txtKeahlian.setText(String.valueOf(squidController.getSelected().getKeahlian()));
                query="update";
            }
        }

    }
    @FXML
    private void action(){
        Player p=new Player(Integer.parseInt(txtId.getText()),txtNama.getText(),Integer.parseInt(txtUmar.getText()),txtKeahlian.getText());
        if (query.equals("update")){
            squidController.getPlayerDao().updateData(p);
            txtId.getScene().getWindow().hide();
            squidController.tablePemain.getItems().clear();
            squidController.tablePemain.setItems(squidController.getPlayerDao().showData());
            squidController.tablePemain.refresh();
        }else{
            squidController.getPlayerDao().addData(p);
            squidController.tablePemain.getItems().clear();
            squidController.tablePemain.setItems(squidController.getPlayerDao().showData());
            squidController.tablePemain.refresh();
            txtId.getScene().getWindow().hide();
        }
    }
    @FXML
    private void cancel(){
        txtId.getScene().getWindow().hide();
    }
}
