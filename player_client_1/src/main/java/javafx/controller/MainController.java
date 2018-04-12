package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import player_fx_bean.PlayerClientBean;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private Integer timeToEndOfRound;

    @FXML
    private PlayerClientBean playerBeanType_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        playerBeanType_1.initPlayerAndCaptainNicknames(Main.nickname, Main.captainNickname, "Sterownia silnika");
    }

    public void exitFromApplication() {
        Platform.runLater(() -> {
            Main.server = null;
            playerBeanType_1.booleanPropertyKickFromServerProperty().setValue(true);
            customMessageBox.showMessageBox(Alert.AlertType.ERROR, "BŁĄD KRYTYCZNY",
                    "Gra została przerwana.",
                    "Powód: utracono połączenie z serwerem.").showAndWait();
        });
    }

    public void startCountdownToTheEndOfRound(Integer roundTime) {
        Timeline timeline = new Timeline();
        timeToEndOfRound = roundTime;
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            playerBeanType_1.setIntegerPropertyTimeToEndOfRound(timeToEndOfRound);
            timeToEndOfRound--;
            if (timeToEndOfRound <= 0) {
                try {
                    Main.server.sendPlayerAnswer(playerBeanType_1.getPlayerAnswers(1), Main.nickname, Main.captainNickname);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                timeline.stop();
                playerBeanType_1.initNewRoundComponentsValues();
            }
        });

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }
}
