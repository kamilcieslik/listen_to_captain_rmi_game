package player_fx_bean;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerClientBean extends VBox implements Serializable, Initializable {

    @FXML
    private Label labelPlayerNick, labelCaptainNick, labelNumberOfPlayers, labelNumberOfPoints, labelPanelName,
            labelCaptainCommand, labelRoundTime, labelDevice1Player1_Name, labelDevice1Player1_Message,
            labelDevice2Player1_Name, labelDevice2Player1_Message, labelDevice3Player1_Name, lavelDevice3Player1_MinValue,
            lavelDevice3Player1_MaxValue, labelDevice3Player1_Message, labelDevice1Player2_Name,
            labelDevice1Player2_Message, labelDevice2Player1_Name1, labelDevice2Player2_Parameter1_Name,
            labelDevice2Player2_Parameter1_Value, labelDevice2Player2_Parameter2_Name, labelDevice2Player2_Parameter3_Name,
            labelDevice2Player2_Parameter3_Value, labelDevice2Player2_Message, labelDevice3Player2_Name,
            lavelDevice3Player2_MinValue, lavelDevice3Player2_MaxValue, labelDevice3Player2_Message,
            labelDevice1Player3_Name, labelDevice1Player3_Message, labelDevice2Player3_Name, labelDevice2Player3_Message,
            labelDevice3Player3_Name, labelDevice3Player3_Message;

    @FXML
    private TextField textFieldCaptainCommand, textFieldDevice1Player1_Value, textFieldDevice1Player2_Value,
            textFieldDevice2Player2_Parameter2_Value, textFieldDevice1Player3_Value;

    @FXML
    private VBox vBoxPlayer1Devices, vBoxPlayer2Devices, vBoxPlayer3Devices;

    @FXML
    private RadioButton radioButtonDevice2Player1_Option1, radioButtonDevice2Player1_Option2,
            radioButtonDevice2Player3_Option1, radioButtonDevice2Player3_Option2;

    @FXML
    private Slider sliderDevice3Player1_Value, sliderDevice3Player2_Value;

    @FXML
    private Button buttonDevice1Player3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
