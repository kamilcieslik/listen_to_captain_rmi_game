package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import player_fx_bean.PlayerClientBean;
import rmi.PlayerClient;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private Integer timeToEndOfRound;

    @FXML
    private PlayerClientBean playerBeanType_2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        playerBeanType_2.initPlayerAndCaptainNicknames(Main.nickname, Main.captainNickname, "Laboratorium paliw rakietowych");
    }

    public void exitFromApplication() {
        Platform.runLater(() -> {
            Main.server = null;
            playerBeanType_2.booleanPropertyKickFromServerProperty().setValue(true);
            customMessageBox.showMessageBox(Alert.AlertType.ERROR, "BŁĄD KRYTYCZNY",
                    "Gra została przerwana.",
                    "Powód: utracono połączenie z serwerem.").showAndWait();
        });
    }

    @SuppressWarnings("Duplicates")
    public void startCountdownToTheEndOfRound(Integer roundTime) {
        Timeline timeline = new Timeline();
        timeToEndOfRound = roundTime;
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            if (timeToEndOfRound <= 0) {
                try {
                    Main.server.sendPlayerAnswer(playerBeanType_2.getPlayerAnswers(2), Main.nickname, Main.captainNickname);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                timeline.stop();
                playerBeanType_2.initNewRoundComponentsValues();
                playerBeanType_2.setStringPropertyCaptainCommand("");
            }

            playerBeanType_2.setIntegerPropertyTimeToEndOfRound(timeToEndOfRound);
            timeToEndOfRound--;
        });

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    public PlayerClientBean getPlayerBeanType_2() {
        return playerBeanType_2;
    }

    public void endOfGame(List<PlayerClient> results) {
        Platform.runLater(() -> {
            Main.server = null;
            playerBeanType_2.booleanPropertyEndOfGameProperty().setValue(true);

            FXMLLoader loader = new FXMLLoader();
            try {
                loader.setLocation(getClass().getClassLoader().getResource("fxml/results.fxml"));
                loader.load();
                ResultsController display = loader.getController();
                display.setResults(results);
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.setTitle("Listen To Your Captain - ver. Client no. 2");
                stage.getIcons().add(new Image("/image/app_icon.png"));
                stage.setScene(new Scene(root, 650, 700));
                stage.showAndWait();
            } catch (IOException ioEcx) {
                ioEcx.printStackTrace();
                Logger.getLogger(ResultsController.class.getName()).log(Level.SEVERE, null, ioEcx);
            }
        });
    }
}
