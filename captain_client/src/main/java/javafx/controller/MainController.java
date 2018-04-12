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
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private CaptainImpl captain;
    private Boolean playersType1 = false;
    private Boolean playersType2 = false;
    private Boolean playersType3 = false;
    private Integer currentNumberOfPlayers;

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

    public void addPlayerRoundAnswers(String answers, String playerNickname) {
        Main.playerObservableList.stream().filter(player -> player.getNickname().equals(playerNickname)).findFirst()
                .ifPresent(player -> player.setRoundAnswers(answers));
    }

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
        if (Main.server != null) {
            if (Main.playerObservableList.size() >= 2) {
                if (!playersType1 && !playersType2 && !playersType3)
                    Main.playerObservableList.forEach(player -> {
                        if (player.getType().equals("Sterownia silnika"))
                            playersType1 = true;
                        if (player.getType().equals("Laboratorium paliw rakietowych"))
                            playersType2 = true;
                        if (player.getType().equals("Działko bojowe"))
                            playersType3 = true;
                    });

                try {
                    if (playersType1)
                        Main.server.broadcastCommand("Sterownia silnika", textFieldPlayer1Command.getText(), Main.captainNickname);
                    if (playersType2)
                        Main.server.broadcastCommand("Laboratorium paliw rakietowych", textFieldPlayer2Command.getText(), Main.captainNickname);
                    if (playersType3)
                        Main.server.broadcastCommand("Działko bojowe", textFieldPlayer3Command.getText(), Main.captainNickname);

                    Main.server.startRound(Integer.parseInt(labelTimeToEndOfRound.getText()), Main.captainNickname);

                    Main.playerObservableList.forEach(player -> player.setRoundAnswers(""));
                } catch (RemoteException e) {
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Nie możesz rozpocząć rundy.",
                            "Powód: błąd połączenia z serwerem.").showAndWait();
                }
            } else
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Nie możesz rozpocząć rundy.",
                        "Powód: gra wymaga co najmniej dwóch graczy.").showAndWait();
        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Nie możesz rozpocząć rundy.",
                    "Powód: brak połączenia z serwerem.").showAndWait();
        }
    }

    @FXML
    void tableViewPlayers_onMouseClicked() {
        if (tableViewPlayers.getSelectionModel().getSelectedItem() != null)
            textAreaPlayerResults.setText(tableViewPlayers.getSelectionModel().getSelectedItem().getRoundAnswers());
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
                labelRoundStatus.setText("Status rundy - nie rozpoczęto gry");
                timeToEndOfRound.setValue(0);
                break;
            case "beforeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                labelRoundStatus.setText("Status rundy - nie rozpoczęto (wydaj rozkazy)");
                timeToEndOfRound.setValue(0);
                break;
            case "activeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(true);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
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
