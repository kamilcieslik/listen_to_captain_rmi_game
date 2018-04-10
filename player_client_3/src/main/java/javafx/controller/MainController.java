package javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import player_fx_bean.PlayerClientBean;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    PlayerClientBean playerClientBean;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
