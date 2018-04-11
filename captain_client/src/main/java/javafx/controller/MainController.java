package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import rmi.impl.CaptainImpl;
import rmi.impl.PlayerImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private CaptainImpl captain;

    private IntegerProperty timeToEndOfRound = new SimpleIntegerProperty(0);

    @FXML
    private Label labelCaptainPanel, labelPanelName, labelCaptainCommand, labelTimeToEndOfRound, labelRoundStatus,
            labelConnectionStatus;

    @FXML
    private TableView<PlayerImpl> tableViewPlayers;

    @FXML
    private TableColumn<PlayerImpl, String> tableColumnPlayers_Nickname;

    @FXML
    private TableColumn<PlayerImpl, Integer> tableColumnPlayers_Points;

    @FXML
    private TableColumn<PlayerImpl, String> tableColumnPlayers_Type;

    @FXML
    private TextArea textAreaPlayerResults;

    @FXML
    private TextField textFieldPlayerPointsForRound, textFieldRoundTime, textFieldPlayer1Command,
            textFieldPlayer2Command, textFieldPlayer3Command;

    @FXML
    private Button buttonPlayerPointsForRound, buttonStartRound;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        labelCaptainPanel.setText("Panel kapitana - " + Main.captainNickname);
        initTableViews();

        labelTimeToEndOfRound.textProperty().bind(Bindings.format("%d s", timeToEndOfRound));
        prepareComponents("beforeFirstRound");
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

    private void initTableViews() {
        tableColumnPlayers_Nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tableColumnPlayers_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnPlayers_Points.setCellValueFactory(new PropertyValueFactory<>("numberOfPoints"));

        tableViewPlayers.setItems(Main.playerObservableList);
    }

    public void refreshTableView(List<PlayerImpl> playerList) {
        Main.playerObservableList.clear();
        Main.playerObservableList.addAll(playerList);
    }

    private void prepareComponents(String gameStatus) {
        switch (gameStatus) {
            case "beforeFirstRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                textFieldPlayer1Command.setText("");
                textFieldPlayer2Command.setText("");
                textFieldPlayer3Command.setText("");
                labelRoundStatus.setText("Status rundy - nie rozpoczęto gry");
                timeToEndOfRound.setValue(0);
                break;
            case "beforeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                textFieldPlayer1Command.setText("");
                textFieldPlayer2Command.setText("");
                textFieldPlayer3Command.setText("");
                labelRoundStatus.setText("Status rundy - nie rozpoczęto (wydaj rozkazy)");
                timeToEndOfRound.setValue(0);
                break;
            case "activeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(true);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                textFieldPlayer1Command.setText("");
                textFieldPlayer2Command.setText("");
                textFieldPlayer3Command.setText("");
                labelRoundStatus.setText("Status rundy - aktywna");
                timeToEndOfRound.setValue(Integer.valueOf(textFieldRoundTime.getText()));
                break;
            case "afterRound":
                buttonPlayerPointsForRound.setDisable(false);
                buttonStartRound.setDisable(true);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                textFieldPlayer1Command.setText("");
                textFieldPlayer2Command.setText("");
                textFieldPlayer3Command.setText("");
                labelRoundStatus.setText("Status rundy - przyznaj punkty");
                timeToEndOfRound.setValue(0);
                break;
        }
    }
}
