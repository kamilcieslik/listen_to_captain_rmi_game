package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import player_fx_bean.PlayerClientBean;

public class Test extends Application {
    public void start(Stage primaryStage) {
        try {
            PlayerClientBean playerClientBean = new PlayerClientBean();
            playerClientBean.initPlayerAndCaptainNicknames("mrfarinq", "cap", "Laboratorium paliw rakietowych");
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.setScene(new Scene(playerClientBean, 1600, 900));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
