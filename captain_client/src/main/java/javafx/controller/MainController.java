package javafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label labelCaptainPanel, labelPanelName, labelCaptainCommand, labelTimeToEndOfRound, labelRoundStatus;

    @FXML
    private TableView<?> tableViewPlayers;

    @FXML
    private TableColumn<?, String> tableColumnPlayers_Nickname;

    @FXML
    private TableColumn<?, Integer> tableColumnPlayers_Points;

    @FXML
    private TextArea textAreaPlayerResults;

    @FXML
    private TextField textFieldPlayerPointsForRound, textFieldRoundTime, textFieldPlayer1Command,
            textFieldPlayer2Command, textFieldPlayer3Command;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
}
