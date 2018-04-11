package javafx.controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import player_fx_bean.PlayerClientBean;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private PlayerClientBean playerBeanType_2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerBeanType_2.initPlayerAndCaptainNicknames(Main.nickname, Main.captainNickname, "Laboratorium paliw rakietowych");
        playerBeanType_2.setIntegerPropertyNumberOfPoints(42);
    }
}
