package main.java.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.users.students.Grade;
import main.java.users.students.GradeType;
import main.java.util.math.MathUtil;
import main.java.util.window.WindowUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 */
public class CreateGradeController implements Initializable {
    private Grade createdGrade;

    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TextField scoreField;
    @FXML
    private TextField nameField;
    @FXML
    private Button createeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        if (typeComboBox.getSelectionModel().isEmpty() ||
                scoreField.getText().isEmpty() ||
                nameField.getText().isEmpty()) {
            displayErrorLabel("All Fields Are Required.");
        } else {
            createGrade();
            WindowUtil.closeWindow(event);
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        this.createdGrade = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.asList(GradeType.values())
                .forEach(type -> typeComboBox.getItems().add(type.toString()));
    }

    private void createGrade() {
        double gradeScore = MathUtil.round(Double.parseDouble(scoreField.getText()), 2);
        GradeType type = GradeType.valueOf(typeComboBox.getValue());
        createdGrade = new Grade(gradeScore, type);
        createdGrade.setName(nameField.getText());
    }

    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public Optional<Grade> getCreatedGrade() {
        return Optional.ofNullable(createdGrade);
    }
}
