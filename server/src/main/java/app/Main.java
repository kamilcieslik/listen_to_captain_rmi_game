package app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.controller.WelcomeBannerController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import rmi.impl.CaptainImpl;
import rmi.impl.PlayerImpl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static Stage mainStage;
    public static ObservableList<CaptainImpl> captainObservableList = FXCollections.observableArrayList();
    public static ObservableList<PlayerImpl> playerObservableList = FXCollections.observableArrayList();
    public static void setMainStage(Stage mainStage) {
        Main.mainStage = mainStage;
    }
    public static Stage getMainStage() {
        return mainStage;
    }

    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Main.mainStage = primaryStage;
            loader.setLocation(getClass().getClassLoader().getResource("fxml/welcome_banner.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            mainStage.setTitle("Listen Your CaptainImpl - ver. ServerImpl");
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
        Main.captainObservableList.forEach(connectedCaptain ->{
            try {
                connectedCaptain.getConnection().lossConnectionWithServer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        Main.playerObservableList.forEach(connectedPlayer ->{
            try {
                connectedPlayer.getConnection().lossConnectionWithServer();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
