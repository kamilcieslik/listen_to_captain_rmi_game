package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import rmi.PlayerClient;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private Boolean playersType1 = false;
    private Boolean playersType2 = false;
    private Boolean playersType3 = false;
    private Boolean showPlayerAnswers = true;
    private Boolean gameHasBeenStarted = false;
    private Integer secondsToEndOfRound = 0;

    private IntegerProperty timeToEndOfRound = new SimpleIntegerProperty(0);

    @FXML
    private Label labelCaptainPanel, labelTimeToEndOfRound, labelRoundStatus, labelConnectionStatus;

    @FXML
    private TableView<PlayerClient> tableViewPlayers;

    @FXML
    private TableColumn<PlayerClient, String> tableColumnPlayers_Nickname;

    @FXML
    private TableColumn<PlayerClient, Integer> tableColumnPlayers_Points;

    @FXML
    private TableColumn<PlayerClient, String> tableColumnPlayers_Type;

    @FXML
    private TextArea textAreaPlayerResults;

    @FXML
    private TextField textFieldPlayerPointsForRound, textFieldRoundTime, textFieldPlayer1Command,
            textFieldPlayer2Command, textFieldPlayer3Command;

    @FXML
    private Button buttonPlayerPointsForRound, buttonStartRound, buttonEndGame;

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
        try {
            Main.server.finishTheGame(Main.captainNickname, Main.playerObservableList
                    .stream().sorted(Comparator.comparing(PlayerClient::getNumberOfPoints).reversed()).collect(Collectors.toList()));
            Main.playerObservableList.clear();
            textAreaPlayerResults.setText("");
            buttonPlayerPointsForRound.setDisable(true);
            buttonStartRound.setDisable(true);
            buttonEndGame.setDisable(true);
            Main.server = null;
            labelConnectionStatus.setText("Status połączenia: rozłączono z serwerem.");
            labelRoundStatus.setText("Wysłano wyniki, zakończono grę.");
        } catch (RemoteException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Nie możesz rozpocząć rundy.",
                    "Powód: błąd połączenia z serwerem.").showAndWait();
        }
    }

    @FXML
    void buttonPlayerPointsForRound_onAction() {
        if (showPlayerAnswers) {
            if (tableViewPlayers.getSelectionModel().getSelectedItem() != null) {
                if (textFieldPlayerPointsForRound.getText().matches("^-?[1-9][0-9]*$")) {
                    try {
                        Main.server.addPoint(Main.captainNickname, tableViewPlayers.getSelectionModel().getSelectedItem()
                                .getNickname(), Integer.valueOf(textFieldPlayerPointsForRound.getText()));
                    } catch (RemoteException e) {
                        customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                                "Nie możesz rozpocząć rundy.",
                                "Powód: błąd połączenia z serwerem.").showAndWait();
                    }
                } else
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Punkty nie zostaną przydzielone.",
                            "Powód: niepoprawny format punktacji.").showAndWait();
            } else
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Punkty nie zostaną przydzielone.",
                        "Powód: nie zaznaczono gracza.").showAndWait();
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Punkty nie zostaną przydzielone.",
                    "Powód: nie jesteś w trybie przyznawania punktów.").showAndWait();
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
                        Main.server.sendCommand("Sterownia silnika", textFieldPlayer1Command.getText(), Main.captainNickname);
                    if (playersType2)
                        Main.server.sendCommand("Laboratorium paliw rakietowych", textFieldPlayer2Command.getText(), Main.captainNickname);
                    if (playersType3)
                        Main.server.sendCommand("Działko bojowe", textFieldPlayer3Command.getText(), Main.captainNickname);
                    gameHasBeenStarted = true;
                    Main.server.startRound(Integer.parseInt(textFieldRoundTime.getText()), Main.captainNickname);
                    activateRound(Integer.parseInt(textFieldRoundTime.getText()));
                } catch (RemoteException e) {
                    e.printStackTrace();
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Nie możesz rozpocząć rundy.",
                            "Powód: błąd połączenia z serwerem.").showAndWait();
                }
            } else
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Nie można rozpocząć gry.",
                        "Powód: zbyt mała ilość graczy (min. 2).").showAndWait();

        } else {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Nie możesz rozpocząć rundy.",
                    "Powód: brak połączenia z serwerem.").showAndWait();
        }
    }

    @FXML
    void tableViewPlayers_onMouseClicked() {
        if (tableViewPlayers.getSelectionModel().getSelectedItem() != null && showPlayerAnswers)
            textAreaPlayerResults.setText(tableViewPlayers.getSelectionModel().getSelectedItem().getRoundAnswers());
    }

    public void exitFromApplication() {
        Platform.runLater(() -> {
            Main.server = null;
            buttonPlayerPointsForRound.setDisable(true);
            buttonStartRound.setDisable(true);
            buttonEndGame.setDisable(true);
            labelConnectionStatus.setText("Status połączenia: rozłączono z serwerem.");
            labelRoundStatus.setText("Status gry: zakończona");
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

    public void refreshTableView(List<PlayerClient> playerList, Boolean playerHasBeenRemoved) {
        Main.playerObservableList.clear();
        Main.playerObservableList.addAll(playerList);
        if (playerHasBeenRemoved) {
            Platform.runLater(() -> {
                textAreaPlayerResults.setText("");
                if (Main.playerObservableList.size() < 2 && gameHasBeenStarted) {
                    try {
                        Main.server.removeCaptain(Main.captainNickname, false);
                    } catch (RemoteException ignored) {
                    }

                    Main.playerObservableList.clear();
                    Main.server = null;
                    labelConnectionStatus.setText("Status połączenia: rozłączono z serwerem.");
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Gra została przerwana.",
                            "Powód: zbyt duża ilość graczy opuściła grę.").showAndWait();
                } else
                    customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                            "Serwer odświeżył listę graczy.",
                            "Powód: jeden z graczy wyszedł z gry.").showAndWait();
            });
        }
    }

    private void prepareComponents(String gameStatus) {
        switch (gameStatus) {
            case "beforeFirstRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                labelRoundStatus.setText("Status rundy: nie rozpoczęto gry");
                break;
            case "beforeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                labelRoundStatus.setText("Status rundy: nie rozpoczęto (wydaj rozkazy)");
                break;
            case "activeRound":
                buttonPlayerPointsForRound.setDisable(true);
                buttonStartRound.setDisable(true);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                labelRoundStatus.setText("Status rundy: aktywna");
                showPlayerAnswers = false;
                break;
            case "afterRound":
                buttonPlayerPointsForRound.setDisable(false);
                buttonStartRound.setDisable(false);
                textAreaPlayerResults.setText("");
                textFieldPlayerPointsForRound.setText("");
                textFieldRoundTime.setText("");
                labelRoundStatus.setText("Status rundy: punktacja/oczekiwanie na kolejną rundę");
                showPlayerAnswers = true;
                break;
        }
    }

    private void activateRound(Integer roundTime) throws RemoteException {
        Timeline timeline = new Timeline();
        secondsToEndOfRound = roundTime;
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            timeToEndOfRound.setValue(secondsToEndOfRound);
            secondsToEndOfRound--;

            if (timeToEndOfRound.getValue() <= 0) {
                timeline.stop();
                prepareComponents("afterRound");
            }
        });

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
        prepareComponents("activeRound");
        Main.server.clearRoundAnswers(Main.captainNickname);
    }
}
