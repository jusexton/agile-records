package main.java.window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.database.FailedLoginException;
import main.java.database.SQLConnection;
import main.java.users.User;
import main.java.window.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Controller responsible for login.fxml backend and logic.
 */
public class LoginController implements Initializable {
    private User loggedInUser;
    private Preferences preferences;

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

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = textField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            loginFailLabel.setVisible(true);
            return;
        }

        try (SQLConnection connection = new SQLConnection()) {
            loggedInUser = connection.attemptLogin(username, password);
            WindowUtil.closeWindow(event);
        } catch(FailedLoginException ex){
            loginFailLabel.setText("Username Or Password Incorrect !");
            loginFailLabel.setVisible(true);
        } catch (SQLException ex) {
            loginFailLabel.setText("No Internet Connection !");
            loginFailLabel.setVisible(true);
        }

        if (checkBox.isSelected()) {
            preferences.put("Last Username", textField.getText());
        } else {
            preferences.remove("Last Username");
        }
        preferences.putBoolean("Use Username", checkBox.isSelected());
    }

    @FXML
    private void handleKeyEvent(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkBox.setSelected(!checkBox.isSelected());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preferences = Preferences.userRoot().node(this.getClass().getName());
        textField.setText(preferences.get("Last Username", ""));
        checkBox.setSelected(preferences.getBoolean("Use Username", false));
    }

    public Optional<User> getLoggedInUser(){
        return Optional.ofNullable(loggedInUser);
    }
}
