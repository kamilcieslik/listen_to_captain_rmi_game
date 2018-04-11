package javafx.controller;

import app.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import player_fx_bean.PlayerClientBean;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private PlayerClientBean playerBeanType_1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playerBeanType_1.initPlayerAndCaptainNicknames(Main.nickname, Main.captainNickname, "Sterownia silnika");
    }

    public void exitFromApplication() {
        Platform.runLater(() -> {
            Main.server = null;
            playerBeanType_1.
            labelConnectionStatus.setText("Status połączenia: rozłączono z serwerem.");
            customMessageBox.showMessageBox(Alert.AlertType.ERROR, "BŁĄD KRYTYCZNY",
                    "Gra została przerwana.",
                    "Powód: utracono połączenie z serwerem.").showAndWait();
        });
    }
}
