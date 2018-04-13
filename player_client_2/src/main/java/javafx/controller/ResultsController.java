package javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import rmi.PlayerClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {
    private static ObservableList<PlayerClient> observableListResults = FXCollections.observableArrayList();

    @FXML
    private TableView<PlayerClient> tableViewResults;
    @FXML
    private TableColumn<PlayerClient, String> tableColumnNick;
    @FXML
    private TableColumn<PlayerClient, Integer> tableColumnNumberOfPoints;


    void setResults(List<PlayerClient> results){
        observableListResults.addAll(results);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableViews();
    }

    @FXML
    void buttonClose() {
        Stage stage = (Stage) tableViewResults.getScene().getWindow();
        stage.close();
    }

    private void initTableViews() {
        tableColumnNick.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        tableColumnNumberOfPoints.setCellValueFactory(new PropertyValueFactory<>("numberOfPoints"));

        tableViewResults.setItems(observableListResults);
    }
}
