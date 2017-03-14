package main.java.window.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.users.students.Grade;
import main.java.users.students.GradeType;
import main.java.users.students.util.MathUtil;
import main.java.window.util.WindowUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 */
public class EditGradeController implements Initializable {
    private Grade grade;

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
            return;
        }

        if (MathUtil.isDouble(scoreField.getText())){
            displayErrorLabel("Incorrect Values Passed.");
            return;
        }

        createGrade();
        WindowUtil.closeWindow(event);
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        this.grade = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Arrays.asList(GradeType.values())
                .forEach(type -> typeComboBox.getItems().add(type.toString()));
    }

    public void init(Grade grade){
        nameField.setText(grade.getName());
        scoreField.setText(String.valueOf(grade.getScore()));
        typeComboBox.setValue(grade.getType().toString());
    }


    private void createGrade() {
        double gradeScore = MathUtil.round(Double.parseDouble(scoreField.getText()), 2);
        GradeType type = GradeType.valueOf(typeComboBox.getValue());
        grade = new Grade(gradeScore, type);
        grade.setName(nameField.getText());
    }

    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public Optional<Grade> getGrade() {
        return Optional.ofNullable(grade);
    }
}
