package player_fx_bean;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * <p>ListenerMethods class.</p>
 *
 * @author Kamil Cieślik.
 * @version $Id: $Id
 */
public class ListenerMethods {
    /**
     * <p>Constructor for ListenerMethods.</p>
     */
    public ListenerMethods() {

    }

    /**
     * <p>changeLabelTextField.</p>
     *
     * @param regex a {@link String} object.
     * @param textField a javafx.scene.control.TextField object.
     * @param label a javafx.scene.control.Label object.
     * @param isEmpty a {@link String} object.
     * @param doesNotFit a {@link String} object.
     * @param length a {@link Integer} object.
     * @param tooLong a {@link String} object.
     */
    public void changeLabelTextField(String regex, TextField textField, Label label, String isEmpty, String doesNotFit,
                                     Integer length, String tooLong) {
        if (textField.getText().isEmpty())
            label.setText(isEmpty);
        else if (!textField.getText().matches(regex))
            label.setText(doesNotFit);
        else if (textField.getText().length() > length)
            label.setText(tooLong);
        else
            label.setText("");
    }
}
