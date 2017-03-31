package window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import security.Hash;
import window.util.WindowUtil;

import java.net.URL;
import java.util.ResourceBundle;

/**
 */
public class ConfirmationController implements Initializable {
    private boolean confirm = false;
    private Hash hash;

    @FXML
    private ImageView imageView;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button confirmButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleConfirmationAction(ActionEvent event){
        if (hash.checkPassword(passwordField.getText())){
            confirm = true;
            WindowUtil.closeWindow(event);
        } else {
            errorLabel.setVisible(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Hash hash){
        this.hash = hash;
    }

    public boolean isConfirmed(){
        return this.confirm;
    }
}
