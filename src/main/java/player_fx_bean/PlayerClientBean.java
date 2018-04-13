package player_fx_bean;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Set;

public class PlayerClientBean extends VBox implements Serializable {
    private Validator validator;
    private ValidationModel validationModel = new ValidationModel();

    private StringProperty beanPropertySpeed_Messages = new SimpleStringProperty("");
    private StringProperty beanPropertyCombustionTemperature_Message = new SimpleStringProperty("");
    private StringProperty beanPropertyEngineTemperature_Message = new SimpleStringProperty("");
    private StringProperty beanPropertyNumberOfBullets_Message = new SimpleStringProperty("");

    private final StringProperty stringPropertyPanelName = new SimpleStringProperty("");
    private final IntegerProperty integerPropertyNumberOfPoints = new SimpleIntegerProperty(0);
    private final IntegerProperty integerPropertyNumberOfPlayers = new SimpleIntegerProperty(0);
    private final IntegerProperty integerPropertyTimeToEndOfRound = new SimpleIntegerProperty(0);
    private final StringProperty stringPropertyCaptainCommand = new SimpleStringProperty("");
    private final BooleanProperty booleanPropertyKickFromServer = new SimpleBooleanProperty(true);
    private final StringProperty stringPropertyCaptainNickname = new SimpleStringProperty("");
    private final DoubleProperty doublePropertyEngineTemperature = new SimpleDoubleProperty(0.0);
    private final DoubleProperty doublePropertyCombustionTemperature = new SimpleDoubleProperty(215.0);
    private final DoubleProperty doublePropertyAngleOfAttack = new SimpleDoubleProperty(13.0);
    private final IntegerProperty integerPropertyNumberOfVentilators = new SimpleIntegerProperty(1);
    private final BooleanProperty fuelCombustionMode_1 = new SimpleBooleanProperty(false);
    private final BooleanProperty fuelCombustionMode_2 = new SimpleBooleanProperty(false);
    private final BooleanProperty typeOfTriggerMechanism_1 = new SimpleBooleanProperty(false);
    private final BooleanProperty typeOfTriggerMechanism_2 = new SimpleBooleanProperty(false);
    private final BooleanProperty booleanPropertyLoadBullet = new SimpleBooleanProperty(false);
    private final BooleanProperty booleanPropertyEndOfGame = new SimpleBooleanProperty(true);

    @FXML
    private Label labelPlayerNick, labelCaptainNick, labelNumberOfPlayers, labelNumberOfPoints, labelPanelName,
            labelCaptainCommand, labelRoundTime, labelDevice1Player1_Name, labelDevice1Player1_Message,
            labelDevice2Player1_Name, labelDevice2Player1_Message, labelDevice3Player1_Name, labelDevice3Player1_Message,
            labelDevice1Player2_Name, labelDevice1Player2_Message, labelDevice2Player1_Name1,
            labelDevice2Player2_Parameter1_Name, labelDevice2Player2_Parameter1_Value, labelDevice2Player2_Parameter2_Name,
            labelDevice2Player2_Parameter3_Name, labelDevice2Player2_Parameter3_Value, labelDevice2Player2_Message,
            labelDevice3Player2_Name, labelDevice1Player3_Name, labelDevice1Player3_Message, labelDevice2Player3_Name,
            labelDevice2Player3_Message, labelDevice3Player3_Name, labelDevice3Player3_Message, labelGameStatus,
            labelDevice3Player1_Value, labelDevice3Player2_Value;
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
    private Button buttonDevice3Player3;

    // Konstruktor:
    public PlayerClientBean() {
        connectWithFXML();
        initRadioButtons();

        addBeanValidationBindings();
        addPropertyBindings();
        addPropertyListeners();

        booleanPropertyKickFromServer.setValue(false);
        booleanPropertyEndOfGame.setValue(false);
        buttonDevice3Player3.setOnAction(event -> booleanPropertyLoadBullet.setValue(true));

        initNewRoundComponentsValues();
    }

    // Metody publiczne:
    public String getPlayerAnswers(Integer playerType) {
        String roundAnswers = "";
        roundAnswers += "Odpowiedzi gracza: " + labelPlayerNick.getText() + ":\n";
        if (playerType == 1) {
            if (!labelDevice1Player1_Message.getText().equals(""))
                roundAnswers += "- prędkość rakiety: nie udzielono odpowiedzi,\n";
            else
                roundAnswers += "- prędkość rakiety: " + textFieldDevice1Player1_Value.getText() + ",\n";

            if (!labelDevice2Player1_Message.getText().equals(""))
                roundAnswers += "- tryb spalania paliwa: nie udzielono odpowiedzi,\n";
            else {
                if (isFuelCombustionMode_1())
                    roundAnswers += "- tryb spalania paliwa: najdłuższy czas spalania,\n";
                else
                    roundAnswers += "- tryb spalania paliwa: najwyższa wydajność przyrządów,\n";
            }
            roundAnswers += "- kąt natarcia: " + String.valueOf(Math.round(getDoublePropertyAngleOfAttack())) + ".\n";
        } else if (playerType == 2) {
            if (!labelDevice1Player2_Message.getText().equals(""))
                roundAnswers += "- temperatura spalania: nie udzielono odpowiedzi,\n";
            else
                roundAnswers += "- temperatura spalania: " + textFieldDevice1Player2_Value.getText() + ",\n";

            if (!labelDevice2Player2_Message.getText().equals(""))
                roundAnswers += "- współczynnik spalania: nie udzielono odpowiedzi,\n";
            else
                roundAnswers += "- temperatura spalania: " + labelDevice2Player2_Parameter3_Value.getText() + ",\n";

            roundAnswers += "- impuls jednostkowy: " + String.valueOf(Math.round(getDoublePropertyCombustionTemperature())) + ".\n";
        } else if (playerType == 3) {
            if (!labelDevice1Player3_Message.getText().equals(""))
                roundAnswers += "- ilość pocisków w magazynku: nie udzielono odpowiedzi,\n";
            else
                roundAnswers += "- ilość pocisków w magazynku: " + textFieldDevice1Player3_Value.getText() + ",\n";

            if (!labelDevice2Player3_Message.getText().equals(""))
                roundAnswers += "- rodzaj mechanizmu spustowego: nie udzielono odpowiedzi,\n";
            else {
                if (isTypeOfTriggerMechanism_1())
                    roundAnswers += "- rodzaj mechanizmu spustowego: ogień seryjny,\n";
                else
                    roundAnswers += "- rodzaj mechanizmu spustowego: ogień pojedynczy,\n";
            }
            roundAnswers += "- ładowanie pocisku: " + labelDevice3Player3_Message.getText() + ".\n";
        }

        return roundAnswers;
    }

    public void initPlayerAndCaptainNicknames(String nickname, String captainNickname, String panelName) {
        labelPlayerNick.setText(nickname);
        stringPropertyCaptainNickname.setValue(captainNickname);
        stringPropertyPanelName.setValue(panelName);
    }

    public void initNewRoundComponentsValues() {
        textFieldDevice1Player1_Value.setText("");
        radioButtonDevice2Player1_Option1.setSelected(false);
        radioButtonDevice2Player1_Option2.setSelected(false);
        sliderDevice3Player1_Value.setValue(13.0);

        textFieldDevice1Player2_Value.setText("");
        integerPropertyNumberOfVentilators.setValue(new Random().nextInt(20) + 1);
        textFieldDevice2Player2_Parameter2_Value.setText("");
        sliderDevice3Player2_Value.setValue(215.0);

        textFieldDevice1Player3_Value.setText("");
        radioButtonDevice2Player3_Option1.setSelected(false);
        radioButtonDevice2Player3_Option2.setSelected(false);
        booleanPropertyLoadBullet.setValue(false);
    }

    // Metody prywatne:
    private void connectWithFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/fxml/player_client_bean.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void addBeanValidationBindings() {
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        validateModel();

        labelDevice1Player1_Message.textProperty().bind(beanPropertySpeed_Messages);
        labelDevice1Player2_Message.textProperty().bind(beanPropertyCombustionTemperature_Message);
        labelDevice2Player2_Message.textProperty().bind(beanPropertyEngineTemperature_Message);
        labelDevice1Player3_Message.textProperty().bind(beanPropertyNumberOfBullets_Message);

        labelDevice1Player1_Message.managedProperty().bind(beanPropertySpeed_Messages.isNotEmpty());
        labelDevice1Player2_Message.managedProperty().bind(beanPropertyCombustionTemperature_Message.isNotEmpty());
        labelDevice2Player2_Message.managedProperty().bind(beanPropertyEngineTemperature_Message.isNotEmpty());
        labelDevice1Player3_Message.managedProperty().bind(beanPropertyNumberOfBullets_Message.isNotEmpty());

        validationModel.stringPropertySpeedProperty().bind(textFieldDevice1Player1_Value.textProperty());
        validationModel.stringPropertyCombustionTemperatureProperty().bind(textFieldDevice1Player2_Value.textProperty());
        validationModel.stringPropertyEngineTemperatureProperty().bind(textFieldDevice2Player2_Parameter2_Value.textProperty());
        validationModel.stringPropertyNumberOfBulletsProperty().bind(textFieldDevice1Player3_Value.textProperty());

        textFieldDevice1Player1_Value.textProperty().addListener(observable -> validateModel());
        textFieldDevice1Player2_Value.textProperty().addListener(observable -> validateModel());
        textFieldDevice2Player2_Parameter2_Value.textProperty().addListener(observable -> validateModel());
        textFieldDevice1Player3_Value.textProperty().addListener(observable -> validateModel());
    }

    private void addPropertyBindings() {
        labelPanelName.textProperty().bind(stringPropertyPanelName);
        labelNumberOfPoints.textProperty().bind(integerPropertyNumberOfPoints.asString());
        labelNumberOfPlayers.textProperty().bind(integerPropertyNumberOfPlayers.asString());
        labelRoundTime.textProperty().bind(Bindings.format("%d s", integerPropertyTimeToEndOfRound));
        textFieldCaptainCommand.textProperty().bind(stringPropertyCaptainCommand);
        labelCaptainNick.textProperty().bind(stringPropertyCaptainNickname);

        // Gracz 1:
        fuelCombustionMode_1.bind(radioButtonDevice2Player1_Option1.selectedProperty());
        fuelCombustionMode_2.bind(radioButtonDevice2Player1_Option2.selectedProperty());
        doublePropertyAngleOfAttack.bind(sliderDevice3Player1_Value.valueProperty());
        labelDevice3Player1_Value.textProperty().bind(Bindings.format("%d ", doublePropertyAngleOfAttack));

        // Gracz 2:
        labelDevice2Player2_Parameter1_Value.textProperty().bind(integerPropertyNumberOfVentilators.asString());
        doublePropertyCombustionTemperature.bind(sliderDevice3Player2_Value.valueProperty());
        labelDevice2Player2_Parameter3_Value.textProperty().bind(Bindings.format("%.2f", doublePropertyEngineTemperature));
        labelDevice3Player2_Value.textProperty().bind(Bindings.format("%d ", doublePropertyCombustionTemperature));

        // Gracz 3:
        typeOfTriggerMechanism_1.bind(radioButtonDevice2Player3_Option1.selectedProperty());
        typeOfTriggerMechanism_2.bind(radioButtonDevice2Player3_Option2.selectedProperty());
    }

    private void addPropertyListeners() {
        stringPropertyPanelName.addListener((o, oldVal, newVal) -> prepareContactModeComponents(newVal));

        booleanPropertyKickFromServer.addListener((o, oldVal, newVal) -> {
            if (!newVal)
                labelGameStatus.setText("Status aktywnej gry: połączono.");
            else {
                labelGameStatus.setText("Status aktywnej gry: zostałeś wyrzucony z gry.");
                stringPropertyCaptainNickname.setValue("---");
                integerPropertyNumberOfPlayers.setValue(0);
                integerPropertyNumberOfPoints.setValue(0);
                initNewRoundComponentsValues();
            }
        });

        booleanPropertyEndOfGame.addListener((o, oldVal, newVal) -> {
            if (newVal)
                labelGameStatus.setText("Status aktywnej gry: rozgrywka zakończona.");
            initNewRoundComponentsValues();
        });

        textFieldDevice2Player2_Parameter2_Value.textProperty().addListener((o, oldVal, newVal) -> {
            try {
                Double result = (double) integerPropertyNumberOfVentilators.get() / Double.valueOf(newVal);
                if (Double.POSITIVE_INFINITY != result)
                    doublePropertyEngineTemperature.setValue(result);
                else
                    doublePropertyEngineTemperature.setValue(0.0);
            } catch (NumberFormatException e) {
                doublePropertyEngineTemperature.setValue(0.0);
            }
        });

        fuelCombustionMode_1.addListener((o, oldVal, newVal) -> {
            if (newVal && fuelCombustionMode_2.getValue())
                labelDevice2Player1_Message.setText("Wybierz tryb spalania.");
            else
                labelDevice2Player1_Message.setText("");
        });

        fuelCombustionMode_2.addListener((o, oldVal, newVal) -> {
            if (newVal && fuelCombustionMode_1.getValue())
                labelDevice2Player1_Message.setText("Wybierz tryb spalania.");
            else
                labelDevice2Player1_Message.setText("");
        });

        typeOfTriggerMechanism_1.addListener((o, oldVal, newVal) -> {
            if (newVal && typeOfTriggerMechanism_2.getValue())
                labelDevice2Player3_Message.setText("Wybierz mechanizm spustowy.");
            else
                labelDevice2Player3_Message.setText("");
        });

        typeOfTriggerMechanism_2.addListener((o, oldVal, newVal) -> {
            if (newVal && typeOfTriggerMechanism_1.getValue())
                labelDevice2Player3_Message.setText("Wybierz mechanizm spustowy.");
            else
                labelDevice2Player3_Message.setText("");
        });

        booleanPropertyLoadBullet.addListener((o, oldVal, newVal) -> {
            if (newVal)
                labelDevice3Player3_Message.setText("Pocisk został załadowany.");
            else
                labelDevice3Player3_Message.setText("Nie załadowano pocisku.");
        });
    }

    private void validateModel() {
        final Set<ConstraintViolation<ValidationModel>> violations = validator.validate(validationModel);
        beanPropertySpeed_Messages.set("");
        beanPropertyCombustionTemperature_Message.set("");
        beanPropertyEngineTemperature_Message.set("");
        beanPropertyNumberOfBullets_Message.set("");
        for (ConstraintViolation<ValidationModel> violation : violations) {
            switch (violation.getPropertyPath().toString()) {
                case "stringPropertySpeed":
                    beanPropertySpeed_Messages.set(violation.getMessage());
                    break;
                case "stringPropertyCombustionTemperature":
                    beanPropertyCombustionTemperature_Message.set(violation.getMessage());
                    break;
                case "stringPropertyEngineTemperature":
                    beanPropertyEngineTemperature_Message.set(violation.getMessage());
                    break;
                case "stringPropertyNumberOfBullets":
                    beanPropertyNumberOfBullets_Message.set(violation.getMessage());
                    break;
            }
        }
    }

    private void prepareContactModeComponents(String mode) {
        switch (mode) {
            case "Sterownia silnika":
                vBoxPlayer1Devices.setVisible(true);
                vBoxPlayer1Devices.setDisable(false);
                vBoxPlayer1Devices.setMinWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer1Devices.setMinHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer1Devices.setPrefWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer1Devices.setPrefHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer1Devices.setMaxWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer1Devices.setMaxHeight(Control.USE_COMPUTED_SIZE);

                vBoxPlayer2Devices.setVisible(false);
                vBoxPlayer2Devices.setDisable(true);
                vBoxPlayer2Devices.setMinWidth(0);
                vBoxPlayer2Devices.setMinHeight(0);
                vBoxPlayer2Devices.setPrefWidth(0);
                vBoxPlayer2Devices.setPrefHeight(0);
                vBoxPlayer2Devices.setMaxWidth(0);
                vBoxPlayer2Devices.setMaxHeight(0);

                vBoxPlayer3Devices.setVisible(false);
                vBoxPlayer3Devices.setDisable(true);
                vBoxPlayer3Devices.setMinWidth(0);
                vBoxPlayer3Devices.setMinHeight(0);
                vBoxPlayer3Devices.setPrefWidth(0);
                vBoxPlayer3Devices.setPrefHeight(0);
                vBoxPlayer3Devices.setMaxWidth(0);
                vBoxPlayer3Devices.setMaxHeight(0);
                break;

            case "Laboratorium paliw rakietowych":
                vBoxPlayer1Devices.setVisible(false);
                vBoxPlayer1Devices.setDisable(true);
                vBoxPlayer1Devices.setMinWidth(0);
                vBoxPlayer1Devices.setMinHeight(0);
                vBoxPlayer1Devices.setPrefWidth(0);
                vBoxPlayer1Devices.setPrefHeight(0);
                vBoxPlayer1Devices.setMaxWidth(0);
                vBoxPlayer1Devices.setMaxHeight(0);

                vBoxPlayer2Devices.setVisible(true);
                vBoxPlayer2Devices.setDisable(false);
                vBoxPlayer2Devices.setMinWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer2Devices.setMinHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer2Devices.setPrefWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer2Devices.setPrefHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer2Devices.setMaxWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer2Devices.setMaxHeight(Control.USE_COMPUTED_SIZE);

                vBoxPlayer3Devices.setVisible(false);
                vBoxPlayer3Devices.setDisable(true);
                vBoxPlayer3Devices.setMinWidth(0);
                vBoxPlayer3Devices.setMinHeight(0);
                vBoxPlayer3Devices.setPrefWidth(0);
                vBoxPlayer3Devices.setPrefHeight(0);
                vBoxPlayer3Devices.setMaxWidth(0);
                vBoxPlayer3Devices.setMaxHeight(0);
                break;

            case "Działko bojowe":
                vBoxPlayer1Devices.setVisible(false);
                vBoxPlayer1Devices.setDisable(true);
                vBoxPlayer1Devices.setMinWidth(0);
                vBoxPlayer1Devices.setMinHeight(0);
                vBoxPlayer1Devices.setPrefWidth(0);
                vBoxPlayer1Devices.setPrefHeight(0);
                vBoxPlayer1Devices.setMaxWidth(0);
                vBoxPlayer1Devices.setMaxHeight(0);

                vBoxPlayer2Devices.setVisible(false);
                vBoxPlayer2Devices.setDisable(true);
                vBoxPlayer2Devices.setMinWidth(0);
                vBoxPlayer2Devices.setMinHeight(0);
                vBoxPlayer2Devices.setPrefWidth(0);
                vBoxPlayer2Devices.setPrefHeight(0);
                vBoxPlayer2Devices.setMaxWidth(0);
                vBoxPlayer2Devices.setMaxHeight(0);

                vBoxPlayer3Devices.setVisible(true);
                vBoxPlayer3Devices.setDisable(false);
                vBoxPlayer3Devices.setMinWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer3Devices.setMinHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer3Devices.setPrefWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer3Devices.setPrefHeight(Control.USE_COMPUTED_SIZE);
                vBoxPlayer3Devices.setMaxWidth(Control.USE_COMPUTED_SIZE);
                vBoxPlayer3Devices.setMaxHeight(Control.USE_COMPUTED_SIZE);
                break;
        }
    }

    private void initRadioButtons() {
        ToggleGroup toggleGroupDevice2Player1 = new ToggleGroup();
        radioButtonDevice2Player1_Option1.setToggleGroup(toggleGroupDevice2Player1);
        radioButtonDevice2Player1_Option2.setToggleGroup(toggleGroupDevice2Player1);

        ToggleGroup toggleGroupDevice2Player3 = new ToggleGroup();
        radioButtonDevice2Player3_Option1.setToggleGroup(toggleGroupDevice2Player3);
        radioButtonDevice2Player3_Option2.setToggleGroup(toggleGroupDevice2Player3);
    }

    // Gettery i settery (właściwości):

    public String getStringPropertyPanelName() {
        return stringPropertyPanelName.get();
    }

    public StringProperty stringPropertyPanelNameProperty() {
        return stringPropertyPanelName;
    }

    public void setStringPropertyPanelName(String stringPropertyPanelName) {
        this.stringPropertyPanelName.set(stringPropertyPanelName);
    }

    public int getIntegerPropertyNumberOfPoints() {
        return integerPropertyNumberOfPoints.get();
    }

    public IntegerProperty integerPropertyNumberOfPointsProperty() {
        return integerPropertyNumberOfPoints;
    }

    public void setIntegerPropertyNumberOfPoints(int integerPropertyNumberOfPoints) {
        this.integerPropertyNumberOfPoints.set(integerPropertyNumberOfPoints);
    }

    public int getIntegerPropertyNumberOfPlayers() {
        return integerPropertyNumberOfPlayers.get();
    }

    public IntegerProperty integerPropertyNumberOfPlayersProperty() {
        return integerPropertyNumberOfPlayers;
    }

    public void setIntegerPropertyNumberOfPlayers(int integerPropertyNumberOfPlayers) {
        this.integerPropertyNumberOfPlayers.set(integerPropertyNumberOfPlayers);
    }

    public int getIntegerPropertyTimeToEndOfRound() {
        return integerPropertyTimeToEndOfRound.get();
    }

    public IntegerProperty integerPropertyTimeToEndOfRoundProperty() {
        return integerPropertyTimeToEndOfRound;
    }

    public void setIntegerPropertyTimeToEndOfRound(int integerPropertyTimeToEndOfRound) {
        this.integerPropertyTimeToEndOfRound.set(integerPropertyTimeToEndOfRound);
    }

    public String getStringPropertyCaptainCommand() {
        return stringPropertyCaptainCommand.get();
    }

    public StringProperty stringPropertyCaptainCommandProperty() {
        return stringPropertyCaptainCommand;
    }

    public void setStringPropertyCaptainCommand(String stringPropertyCaptainCommand) {
        this.stringPropertyCaptainCommand.set(stringPropertyCaptainCommand);
    }

    public boolean isBooleanPropertyKickFromServer() {
        return booleanPropertyKickFromServer.get();
    }

    public BooleanProperty booleanPropertyKickFromServerProperty() {
        return booleanPropertyKickFromServer;
    }

    public void setBooleanPropertyKickFromServer(boolean booleanPropertyKickFromServer) {
        this.booleanPropertyKickFromServer.set(booleanPropertyKickFromServer);
    }

    public String getStringPropertyCaptainNickname() {
        return stringPropertyCaptainNickname.get();
    }

    public StringProperty stringPropertyCaptainNicknameProperty() {
        return stringPropertyCaptainNickname;
    }

    public void setStringPropertyCaptainNickname(String stringPropertyCaptainNickname) {
        this.stringPropertyCaptainNickname.set(stringPropertyCaptainNickname);
    }

    public double getDoublePropertyEngineTemperature() {
        return doublePropertyEngineTemperature.get();
    }

    public DoubleProperty doublePropertyEngineTemperatureProperty() {
        return doublePropertyEngineTemperature;
    }

    public void setDoublePropertyEngineTemperature(double doublePropertyEngineTemperature) {
        this.doublePropertyEngineTemperature.set(doublePropertyEngineTemperature);
    }

    public double getDoublePropertyCombustionTemperature() {
        return doublePropertyCombustionTemperature.get();
    }

    public DoubleProperty doublePropertyCombustionTemperatureProperty() {
        return doublePropertyCombustionTemperature;
    }

    public void setDoublePropertyCombustionTemperature(double doublePropertyCombustionTemperature) {
        this.doublePropertyCombustionTemperature.set(doublePropertyCombustionTemperature);
    }

    public double getDoublePropertyAngleOfAttack() {
        return doublePropertyAngleOfAttack.get();
    }

    public DoubleProperty doublePropertyAngleOfAttackProperty() {
        return doublePropertyAngleOfAttack;
    }

    public void setDoublePropertyAngleOfAttack(double doublePropertyAngleOfAttack) {
        this.doublePropertyAngleOfAttack.set(doublePropertyAngleOfAttack);
    }

    public int getIntegerPropertyNumberOfVentilators() {
        return integerPropertyNumberOfVentilators.get();
    }

    public IntegerProperty integerPropertyNumberOfVentilatorsProperty() {
        return integerPropertyNumberOfVentilators;
    }

    public void setIntegerPropertyNumberOfVentilators(int integerPropertyNumberOfVentilators) {
        this.integerPropertyNumberOfVentilators.set(integerPropertyNumberOfVentilators);
    }

    public boolean isFuelCombustionMode_1() {
        return fuelCombustionMode_1.get();
    }

    public BooleanProperty fuelCombustionMode_1Property() {
        return fuelCombustionMode_1;
    }

    public void setFuelCombustionMode_1(boolean fuelCombustionMode_1) {
        this.fuelCombustionMode_1.set(fuelCombustionMode_1);
    }

    public boolean isFuelCombustionMode_2() {
        return fuelCombustionMode_2.get();
    }

    public BooleanProperty fuelCombustionMode_2Property() {
        return fuelCombustionMode_2;
    }

    public void setFuelCombustionMode_2(boolean fuelCombustionMode_2) {
        this.fuelCombustionMode_2.set(fuelCombustionMode_2);
    }

    public boolean isTypeOfTriggerMechanism_1() {
        return typeOfTriggerMechanism_1.get();
    }

    public BooleanProperty typeOfTriggerMechanism_1Property() {
        return typeOfTriggerMechanism_1;
    }

    public void setTypeOfTriggerMechanism_1(boolean typeOfTriggerMechanism_1) {
        this.typeOfTriggerMechanism_1.set(typeOfTriggerMechanism_1);
    }

    public boolean isTypeOfTriggerMechanism_2() {
        return typeOfTriggerMechanism_2.get();
    }

    public BooleanProperty typeOfTriggerMechanism_2Property() {
        return typeOfTriggerMechanism_2;
    }

    public void setTypeOfTriggerMechanism_2(boolean typeOfTriggerMechanism_2) {
        this.typeOfTriggerMechanism_2.set(typeOfTriggerMechanism_2);
    }

    public boolean isBooleanPropertyLoadBullet() {
        return booleanPropertyLoadBullet.get();
    }

    public BooleanProperty booleanPropertyLoadBulletProperty() {
        return booleanPropertyLoadBullet;
    }

    public void setBooleanPropertyLoadBullet(boolean booleanPropertyLoadBullet) {
        this.booleanPropertyLoadBullet.set(booleanPropertyLoadBullet);
    }

    public boolean isBooleanPropertyEndOfGame() {
        return booleanPropertyEndOfGame.get();
    }

    public BooleanProperty booleanPropertyEndOfGameProperty() {
        return booleanPropertyEndOfGame;
    }

    public void setBooleanPropertyEndOfGame(boolean booleanPropertyEndOfGame) {
        this.booleanPropertyEndOfGame.set(booleanPropertyEndOfGame);
    }
}
