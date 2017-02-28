package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.java.database.FailedLoginException;
import main.java.database.SQLConnection;
import main.java.users.User;
import main.java.util.window.WindowUtil;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Controller responsible for login.fxml backend and logic.
 */
public class LoginController implements Initializable {
    private final String HOST = "jdbc:mysql://gator4196.hostgator.com:3306/txscypaa_agilerecords";
    private final String PASSWORD = "txscypaa_agile";
    private final String DATABASE_NAME = "4@lq^tsFiI0b";

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

    private User loggedInUser;

    /**
     * The login process that will take place when the
     * user prompts the system for login.
     *
     * @param event The event triggered.
     */
    @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = textField.getText();
        String password = passwordField.getText();
        if (username.equals("") || password.equals("")) {
            loginFailLabel.setVisible(true);
            return;
        }

        SQLConnection connection = new SQLConnection(HOST, PASSWORD, DATABASE_NAME);
        try{
            loggedInUser = connection.attemptLogin(username, password);
            WindowUtil.closeWindow(event);
        } catch(FailedLoginException ex){
            loginFailLabel.setVisible(true);
        }

        if (checkBox.isSelected()) {
            preferences.put("Last Username", textField.getText());
        }
        preferences.putBoolean("Use Username", checkBox.isSelected());
    }

    /**
     * Used to flip the position of the checkbox when the
     * enter key is pressed while focus is on the checkbox.
     *
     * @param event The key event triggered.
     */
    @FXML
    private void flipSelected(KeyEvent event) {
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

    public User getLoggedInUser(){
        return this.loggedInUser;
    }
}
