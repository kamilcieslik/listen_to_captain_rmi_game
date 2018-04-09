package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import player_fx_bean.PlayerClientBean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test extends Application {
    private static Stage mainStage;

    public static void setMainStage(Stage mainStage) {
        Test.mainStage = mainStage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        Test.mainStage = primaryStage;
        PlayerClientBean playerClientBean = new PlayerClientBean();
        loader.setController(playerClientBean);
       Parent root = loader.getRoot();
        primaryStage.getIcons().add(new Image("/image/app_icon.png"));
       primaryStage.setScene(new Scene(root, 1600, 900));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
