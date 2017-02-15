package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.users.User;

import java.net.URL;
import java.sql.Connection;
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
    }

    /**
     * Executes login logic on button press.
     * Also saves username for future use if checkbox is selected.
     *
     * @return Whether the login was successful or not.
     */
    @FXML
    private boolean attemptLogin() {
        // Tests for content in text fields before connecting to database.
        if (textField.getText().equals("") || passwordField.getText().equals("")) {
            loginFailLabel.setVisible(true);
            return false;
        }
        // TODO: Add login logic
        Connection connection = null;
        if (connection == null) {
            loginFailLabel.setVisible(true);
        }
        if (checkBox.isSelected()) {
            preferences.put("Last Username", textField.getText());
        }
        return false;
    }

    /**
     * Will launch the appropriate view on a successful login,
     * depending on what user has successfully logged in.
     *
     * @param user The user that has successfully logged in.
     */
    private void launchView(final User user) {
        // TODO: Add launch logic.
    }
}
