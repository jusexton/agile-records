package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Controller responsible for login.fxml backend and logic.
 */
public class LoginController implements Initializable {

    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginFailLabel;

    private Preferences preferences;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        textField.setText(preferences.get("Last Username", ""));
        checkBox.setSelected(preferences.getBoolean("Use Username", false));
    }

    @FXML
    private void handleLoginAction(ActionEvent event) {
        if (textField.getText().equals("") || passwordField.getText().equals("")) {
            loginFailLabel.setVisible(true);
        }

        // TODO: Add login logic

        if (checkBox.isSelected()) {
            preferences.put("Last Username", textField.getText());
        }
        preferences.putBoolean("Use Username", checkBox.isSelected());
    }

    @FXML
    private void flipSelected(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkBox.setSelected(!checkBox.isSelected());
        }
    }
}
