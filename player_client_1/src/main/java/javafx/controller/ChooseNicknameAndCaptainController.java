package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import rmi.remote.Server;
import rmi.CaptainClient;
import rmi.remote.impl.PlayerImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChooseNicknameAndCaptainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private ObservableList<CaptainClient> captainObservableList = FXCollections.observableArrayList();
    private Server server;

    @FXML
    private TextField textFieldNick;
    @FXML
    private Label labelNick;
    @FXML
    private TableView<CaptainClient> tableViewGames;
    @FXML
    private TableColumn<CaptainClient, String> tableColumnCaptain;
    @FXML
    private TableColumn<CaptainClient, Integer> tableColumnNumberOfPlayers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");

        initTableViews();
    }

    @FXML
    void buttonJoin() {
        if (textFieldNick.getText().equals(""))
            labelNick.setText("Nie podano nicku gracza.");
        else {
            Main.nickname = textFieldNick.getText();
            try {
                if (server == null) {
                    String url = "rmi://localhost/sserver";
                    server = (Server) Naming.lookup(url);
                }

                if (server.isExistPlayerNickname(Main.nickname))
                    labelNick.setText("Gracz o podanym nicku już istnieje.");
                else if (tableViewGames.getSelectionModel().getSelectedItem() == null)
                    labelNick.setText("Nie wybrano gry.");
                else {
                    Main.captainNickname = tableViewGames.getSelectionModel().getSelectedItem().getName();
                    FXMLLoader loader = new FXMLLoader();
                    try {
                        loader.setLocation(getClass().getClassLoader().getResource("fxml/main.fxml"));
                        loader.load();
                        MainController mainController = loader.getController();
                        Main.player = new PlayerImpl(Main.nickname, "Sterownia silnika", Main.captainNickname,
                                mainController, server);
                        Main.server = Main.player.getServer();
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
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                        "Pobranie listy kapitanów nie powiodło się.",
                        "Powód: nieudana operacja połączenia z serwerem.").showAndWait();
            }
        }
    }

    @FXML
    void buttonRefresh() {
        String url = "rmi://localhost/sserver";
        try {
            if (server == null) {
                server = (Server) Naming.lookup(url);
            }
            List list = new ArrayList(server.getCaptains());
            captainObservableList.clear();
            captainObservableList.addAll(list);
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Pobranie listy kapitanów nie powiodło się.",
                    "Powód: nieudana operacja połączenia z serwerem.").showAndWait();
        }
    }

    private void initTableViews() {
        tableColumnCaptain.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnNumberOfPlayers.setCellValueFactory(new PropertyValueFactory<>("numberOfPlayers"));

        tableViewGames.setItems(captainObservableList);
    }
}
