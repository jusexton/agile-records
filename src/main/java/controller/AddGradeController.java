package main.java.controller;


import javafx.fxml.FXML;
import java.lang.*;

import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.users.students.Grade;
import main.java.users.students.GradeType;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import main.java.users.students.Major;
import main.java.util.window.WindowUtil;
import org.apache.tools.ant.taskdefs.condition.IsFalse;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by germ21 on 2/26/2017.
 */
public class AddGradeController implements Initializable {
    private Grade newGrade;
    private GradeType gtype;
    @FXML
    private ComboBox gradeTypeField;
    @FXML
    private TextField scoreField;
    @FXML
    private TextField nameField;
    @FXML
    private Button addGradeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label createFailLabel;



    @FXML
    private void handleAddGradeButtonAction(ActionEvent event) {
        if (this.gradeTypeField.getSelectionModel().isEmpty() || this.scoreField.getText().isEmpty()|| this.nameField.getText().isEmpty()) {
            createFailLabel.setVisible(true);
        } else {
            this.createGrade();
            //return getCreatedGrade();
            WindowUtil.closeWindow(event);
        }
    }
    @FXML
    private void createGrade() {

            double gradeScore = Double.parseDouble(this.scoreField.getText());
            String type = this.gradeTypeField.getValue().toString();
            this.gtype = GradeType.valueOf(type);
            this.newGrade = new Grade(gradeScore, gtype);
            this.newGrade.setName(this.nameField.getText());

    }
    @FXML
    public Grade getCreatedGrade() {
        return this.newGrade;
    }
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        this.newGrade = null;
        WindowUtil.closeWindow(event);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gradeTypeField.getItems().setAll(GradeType.values());
    }
}
