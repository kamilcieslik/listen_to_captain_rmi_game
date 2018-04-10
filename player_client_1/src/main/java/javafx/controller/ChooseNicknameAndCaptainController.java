package javafx.controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseNicknameAndCaptainController implements Initializable {
    @FXML
    private TextField textFieldNick;

    @FXML
    private Label labelNick;

    @FXML
    private TableView<?> tableViewGames;

    @FXML
    private TableColumn<?, String> tableColumnCaptain;

    @FXML
    private TableColumn<?, Integer> tableColumnNumberOfPlayers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void buttonJoin() {
        Main.nickname = textFieldNick.getText();
        //Main.captainNickname
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
            loader.load();
            Parent parent = loader.getRoot();
            Stage primaryStage = new Stage();
            Main.setMainStage(primaryStage);
            primaryStage.setTitle("Listen To Your Captain - ver. Client no. 1");
            primaryStage.getIcons().add(new Image("/image/app_icon.png"));
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(900);
            primaryStage.setScene(new Scene(parent, 1600, 900));
            Stage stage = (Stage) textFieldNick.getScene().getWindow();
            stage.hide();
            primaryStage.show();
        } catch (IOException ioEcx) {
            Logger.getLogger(WelcomeBannerController.class.getName()).log(Level.SEVERE, null, ioEcx);
        }
    }

    @FXML
    void buttonRefresh() {

    }
}
