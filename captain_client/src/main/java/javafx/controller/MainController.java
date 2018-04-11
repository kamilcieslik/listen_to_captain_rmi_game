package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import rmi.Server;
import rmi.impl.CaptainImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private CaptainImpl captain;

    @FXML
    private Label labelCaptainPanel, labelPanelName, labelCaptainCommand, labelTimeToEndOfRound, labelRoundStatus,
            labelConnectionStatus;

    @FXML
    private TableView<?> tableViewPlayers;

    @FXML
    private TableColumn<?, String> tableColumnPlayers_Nickname;

    @FXML
    private TableColumn<?, Integer> tableColumnPlayers_Points;

    @FXML
    private TableColumn<?, String> tableColumnPlayers_Type;

    @FXML
    private TextArea textAreaPlayerResults;

    @FXML
    private TextField textFieldPlayerPointsForRound, textFieldRoundTime, textFieldPlayer1Command,
            textFieldPlayer2Command, textFieldPlayer3Command;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        labelCaptainPanel.setText("Panel kapitana - " + Main.captainNickname);
    }

    @FXML
    void buttonEndGame_onAction() {

    }

    @FXML
    void buttonPlayerPointsForRound_onAction() {

    }

    @FXML
    void buttonStartRound_onAction() {

    }

    @FXML
    void tableViewPlayers_onMouseClicked() {

    }

    public void exitFromApplication() {
        Platform.runLater(() -> {
            Main.server = null;
            labelConnectionStatus.setText("Status połączenia: rozłączono z serwerem.");
            customMessageBox.showMessageBox(Alert.AlertType.ERROR, "BŁĄD KRYTYCZNY",
                    "Gra została przerwana.",
                    "Powód: utracono połączenie z serwerem.").showAndWait();
        });
    }
}
