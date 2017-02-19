package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import main.java.users.User;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Controller responsible for login.fxml backend and logic.
 */
// TODO: Allow preferences to display checkbox according to past data.
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

        setHandlers();
    }

    /**
     * Executes login logic on button press.
     * Also saves username for future use if checkbox is selected.
     *
     * @return Whether the login was successful or not.
     */
    // TODO: May need to rethink this function.
    @Nullable
    private User attemptLogin() {
        // Tests for content in text fields before connecting to database.
        if (textField.getText().equals("") || passwordField.getText().equals("")) {
            loginFailLabel.setVisible(true);
            return null;
        }

        // TODO: Add login logic
        Connection connection = null;
        User loggedInUser = null;
        if (connection == null) {
            loginFailLabel.setVisible(true);
            return null;
        }
        if (checkBox.isSelected()) {
            preferences.put("Last Username", textField.getText());
        }
        return null;
    }

    /**
     * Will launch the appropriate view on a successful login,
     * depending on what user has successfully logged in. Also
     * closes login screen.
     *
     * @param user The user that has successfully logged in.
     */
    private void launchView(final User user) {
        // TODO: Add launch logic.
    }

    /**
     * Assigns each control handlers.
     */
    private void setHandlers() {
        loginButton.setOnMouseClicked(mouseEvent -> attemptLogin());
        loginButton.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                attemptLogin();
            }
        });
        checkBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                checkBox.setSelected(!checkBox.isSelected());
            }
        });
    }
}
