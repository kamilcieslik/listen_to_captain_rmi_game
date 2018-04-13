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
import rmi.remote.Server;
import rmi.remote.impl.CaptainImpl;
import rmi.PlayerClient;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    public static String captainNickname = "captain";
    private static Stage mainStage;
    public static Server server;
    public static CaptainImpl captain;
    public static ObservableList<PlayerClient> playerObservableList = FXCollections.observableArrayList();

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

    public void stop() throws RemoteException {
        if (server != null)
            server.removeCaptain(captainNickname, false);

        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
