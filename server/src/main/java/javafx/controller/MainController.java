package javafx.controller;

import app.Main;
import javafx.CustomMessageBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import rmi.CaptainClient;
import rmi.PlayerClient;
import rmi.remote.impl.ServerImpl;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private CustomMessageBox customMessageBox;
    private ServerImpl server;

    @FXML
    private Label labelServerStatus;
    @FXML
    private TableView<PlayerClient> tableViewPlayers;
    @FXML
    private TableColumn<PlayerClient, String> tableColumnPlayers_Nickname;
    @FXML
    private TableColumn<PlayerClient, String> tableColumnPlayers_Captain;
    @FXML
    private TableColumn<PlayerClient, String> tableColumnPlayers_Type;
    @FXML
    private TableColumn<PlayerClient, Integer> tableColumnPlayers_Points;


    @FXML
    private TableView<CaptainClient> tableViewCaptains;
    @FXML
    private TableColumn<CaptainClient, String> tableColumnCaptains_Nickname;
    @FXML
    private TableColumn<CaptainClient, Integer> tableColumnCaptains_NumberOfPlayers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        customMessageBox = new CustomMessageBox("image/app_icon.png");
        initTableViews();
    }

    @FXML
    void buttonKickCaptain_onAction() throws RemoteException {
        if (tableViewCaptains.getSelectionModel().getSelectedItem() != null) {
            CaptainClient selectedCaptain = tableViewCaptains.getSelectionModel().getSelectedItem();
            server.removeCaptain(selectedCaptain.getName(), false);
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja wyrzucenia kapitana z serwera nie powiedzie się.",
                    "Powód: nie zaznaczono kapitana.")
                    .showAndWait();
    }

    @FXML
    void buttonKickPlayer_onAction() throws RemoteException {
        if (tableViewPlayers.getSelectionModel().getSelectedItem() != null) {
            PlayerClient selectedPlayer = tableViewPlayers.getSelectionModel().getSelectedItem();
            server.removePlayer(selectedPlayer.getNickname());
        } else
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Operacja wyrzucenia kapitana z serwera nie powiedzie się.",
                    "Powód: nie zaznaczono kapitana.")
                    .showAndWait();
    }

    public void refreshCaptainsList() throws RemoteException {
        Main.captainObservableList.clear();
        Main.captainObservableList.addAll(server.getCaptains());
    }

    public void refreshPlayersList(){
        Main.playerObservableList.clear();
        Main.playerObservableList.addAll(server.getPlayers().values());
    }

    @FXML
    void buttonRefreshCaptains_onAction() throws RemoteException {
        Main.captainObservableList.clear();
        Main.captainObservableList.addAll(server.getCaptains());
    }

    @FXML
    void buttonRefreshPlayers_onAction() {
        Main.playerObservableList.clear();
        Main.playerObservableList.addAll(server.getPlayers().values());
    }

    @FXML
    void buttonStart_onAction(ActionEvent actionEvent) {
        try {
            int port = 1099;
            String serverUrl = "rmi://localhost/sserver";
            java.rmi.registry.LocateRegistry.createRegistry(port);
            server = new ServerImpl(this);
            Naming.rebind(serverUrl, server);
            labelServerStatus.setText("online");
            ((Button) actionEvent.getSource()).setDisable(true);
        } catch (Exception e) {
            customMessageBox.showMessageBox(Alert.AlertType.WARNING, "Ostrzeżenie",
                    "Nie udało się rozpocząć pracy serwera.",
                    "Powód: " + e.getMessage())
                    .showAndWait();
        }
    }

    @FXML
    void buttonStop_onAction() {
        Stage stage = (Stage) labelServerStatus.getScene().getWindow();
        stage.close();
    }

    private void initTableViews() {
        tableColumnPlayers_Nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tableColumnPlayers_Captain.setCellValueFactory(new PropertyValueFactory<>("captainNickname"));
        tableColumnPlayers_Type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableColumnPlayers_Points.setCellValueFactory(new PropertyValueFactory<>("numberOfPoints"));


        tableColumnCaptains_Nickname.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnCaptains_NumberOfPlayers.setCellValueFactory(new PropertyValueFactory<>("numberOfPlayers"));

        tableViewPlayers.setItems(Main.playerObservableList);
        tableViewCaptains.setItems(Main.captainObservableList);
    }
}
