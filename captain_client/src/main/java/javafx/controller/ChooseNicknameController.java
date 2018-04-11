package javafx.controller;

import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.CustomMessageBox;
import rmi.Server;
import rmi.impl.CaptainImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseNicknameController implements Initializable {
    private CustomMessageBox customMessageBox;

    @FXML
    private TextField textFieldNick;

    @FXML
    private Label labelNick;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");
    }

    @FXML
    void buttonJoin() {
        if (textFieldNick.getText().equals(""))
            labelNick.setText("Nie podano nicku kapitana.");
        else {
            Main.captainNickname = textFieldNick.getText();
            Server server;
            String url = "rmi://localhost/sserver";
            try {
                server = (Server) Naming.lookup(url);

                if (server.isExistCaptainNickname(Main.captainNickname))
                    labelNick.setText("Kapitan o podanym nicku już istnieje.");
                else {
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
                        loader.load();
                        MainController mainController = loader.getController();
                        Main.captain = new CaptainImpl(Main.captainNickname, mainController, server);
                        Main.server = Main.captain.getServer();
                        Parent parent = loader.getRoot();
                        Stage primaryStage = new Stage();
                        Main.setMainStage(primaryStage);
                        primaryStage.setTitle("Listen To Your Captain - ver. Captain");
                        primaryStage.getIcons().add(new Image("/image/app_icon.png"));
                        primaryStage.setMinWidth(970);
                        primaryStage.setMinHeight(900);
                        primaryStage.setScene(new Scene(parent, 1600, 900));
                        Stage stage = (Stage) textFieldNick.getScene().getWindow();
                        stage.hide();
                        primaryStage.show();
                    } catch (IOException ioEcx) {
                        Logger.getLogger(WelcomeBannerController.class.getName()).log(Level.SEVERE, null, ioEcx);
                    }
                }
            } catch (RemoteException | NotBoundException | MalformedURLException e) {
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Operacja utworzenia sesji kapitana nie powiodła się.",
                        "Powód: nieudana operacja połączenia z serwerem.").showAndWait();
            }
        }
    }

    @FXML
    void textFieldNick_onKeyPressed() {
        if (!labelNick.getText().equals(""))
            labelNick.setText("");
    }
}
