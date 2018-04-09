package app;

import javafx.application.Application;
import javafx.controller.WelcomeBannerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static Stage mainStage;
    public static void setMainStage(Stage mainStage) {
        Main.mainStage = mainStage;
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Main.mainStage = primaryStage;
            loader.setLocation(getClass().getClassLoader().getResource("fxml/welcome_banner.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            mainStage.setTitle("Listen Your Captain - ver. Captain");
            mainStage.getIcons().add(new Image("/image/app_icon.png"));
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.resizableProperty().setValue(Boolean.FALSE);
            mainStage.setScene(new Scene(root, 819, 325));
            WelcomeBannerController loaderController = loader.getController();
            mainStage.addEventHandler(WindowEvent.WINDOW_SHOWN, window -> {
                Thread windowShownListener = new Thread(loaderController::initMainScene);
                windowShownListener.start();
            });
            mainStage.centerOnScreen();
            mainStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
