package javafx.controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import player_fx_bean.PlayerClientBean;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private PlayerClientBean playerBeanType_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerBeanType_1.initPlayerAndCaptainNicknames(Main.nickname, Main.captainNickname, "Działko bojowe");
    }
}
