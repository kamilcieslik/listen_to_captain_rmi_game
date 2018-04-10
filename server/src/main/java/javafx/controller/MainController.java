package javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label labelServerStatus;

    @FXML
    private TableView<?> tableViewPlayers;

    @FXML
    private TableColumn<?, String> tableColumnPlayers_Nickname;

    @FXML
    private TableColumn<?, String> tableColumnPlayers_Captain;

    @FXML
    private TableView<?> tableViewCaptains;

    @FXML
    private TableColumn<?, String> tableColumnCaptains_Nickname;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void buttonKickCaptain_onAction() {

    }

    @FXML
    void buttonKickPlayer_onAction() {

    }

    @FXML
    void buttonRefreshCaptains_onAction() {

    }

    @FXML
    void buttonRefreshPlayers_onAction() {

    }

    @FXML
    void buttonStart_onAction() {

    }

    @FXML
    void buttonStop_onAction() {

    }
}
