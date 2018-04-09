package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
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
        try {
            Test.mainStage = primaryStage;
            loader.setLocation(getClass().getClassLoader().getResource("fxml/player_client_bean.fxml"));
            loader.load();
            Parent root = loader.getRoot();
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.setScene(new Scene(root, 1600, 900));
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
