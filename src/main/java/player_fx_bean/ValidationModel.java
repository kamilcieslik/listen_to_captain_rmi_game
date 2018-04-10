package player_fx_bean;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ValidationModel {
    @NotNull
    @Size(min = 1, message = "Podaj temperaturę spalania.")
    @Pattern(regexp = "^(([1-9][0-9]*(.[0-9]{1,2})?)$|^0(.[0-9]{1,2})?)$", message = "Niepoprawny format temperatury spalania.")
    private StringProperty stringPropertyCombustionTemperature = new SimpleStringProperty("");

    @NotNull
    @Size(min = 1, message = "Podaj prędkość.")
    @Pattern(regexp = "^(([1-9][0-9]*(.[0-9]{1,2})?)$|^0(.[0-9]{1,2})?)?$", message = "Niepoprawny format prędkości.")
    private StringProperty stringPropertySpeed = new SimpleStringProperty("");

    @NotNull
    @Size(min = 1, message = "Podaj temperaturę silnika.")
    @Pattern(regexp = "^(([1-9][0-9]*(.[0-9]{1,2})?)$|^0(.[0-9]{1,2})?)?$", message = "Niepoprawny format temperatury silnika.")
    private StringProperty stringPropertyEngineTemperature = new SimpleStringProperty("");

    @NotNull
    @Size(min = 1, message = "Podaj ilość pocisków w magazynku.")
    @Pattern(regexp = "^([0-9]+)?$", message = "Niepoprawny format ilości pocisków.")
    private StringProperty stringPropertyNumberOfBullets = new SimpleStringProperty("");


    public String getStringPropertyCombustionTemperature() {
        return stringPropertyCombustionTemperature.get();
    }

    public StringProperty stringPropertyCombustionTemperatureProperty() {
        return stringPropertyCombustionTemperature;
    }

    public void setStringPropertyCombustionTemperature(String stringPropertyCombustionTemperature) {
        this.stringPropertyCombustionTemperature.set(stringPropertyCombustionTemperature);
    }

    public String getStringPropertySpeed() {
        return stringPropertySpeed.get();
    }

    public StringProperty stringPropertySpeedProperty() {
        return stringPropertySpeed;
    }

    public void setStringPropertySpeed(String stringPropertySpeed) {
        this.stringPropertySpeed.set(stringPropertySpeed);
    }

    public String getStringPropertyEngineTemperature() {
        return stringPropertyEngineTemperature.get();
    }

    public StringProperty stringPropertyEngineTemperatureProperty() {
        return stringPropertyEngineTemperature;
    }

    public void setStringPropertyEngineTemperature(String stringPropertyEngineTemperature) {
        this.stringPropertyEngineTemperature.set(stringPropertyEngineTemperature);
    }

    public String getStringPropertyNumberOfBullets() {
        return stringPropertyNumberOfBullets.get();
    }

    public StringProperty stringPropertyNumberOfBulletsProperty() {
        return stringPropertyNumberOfBullets;
    }

    public void setStringPropertyNumberOfBullets(String stringPropertyNumberOfBullets) {
        this.stringPropertyNumberOfBullets.set(stringPropertyNumberOfBullets);
    }
}
