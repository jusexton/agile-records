package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.users.students.Course;
import main.java.users.students.Major;
import main.java.users.students.Student;
import main.java.util.security.Hash;
import main.java.util.security.HashingUtil;
import main.java.util.window.WindowUtil;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller responsible for CreateStudent.fxml backend and logic.
 */
public class CreateStudentController implements Initializable {
    private Student createdStudent;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TableView<Course> courseTableView;
    @FXML
    private TableColumn<Course, Integer> crnTableColumn;
    @FXML
    private TableColumn<Course, String> nameTableColumn;
    @FXML
    private TableColumn<Course, String> creditsTableColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label createFailLabel;
    @FXML
    private ComboBox<String> majorComboBox;

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        // TODO: Add functionality
    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        courseTableView.getItems()
                .removeAll(courseTableView.getSelectionModel().getSelectedItems());
    }

    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        // Make sure required fields are filled.
        if (usernameTextField.getText().isEmpty() ||
                passwordField.getText().isEmpty() ||
                majorComboBox.getSelectionModel().isEmpty()) {
            createFailLabel.setVisible(true);
        } else {
            createStudent();
            WindowUtil.closeWindow(event);
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        createdStudent = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Allows cells to determine where each student property should be placed.
        crnTableColumn.setCellValueFactory(new PropertyValueFactory<>("CRN"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));

        Arrays.asList(Major.values())
                .forEach(value -> majorComboBox.getItems().add(value.toString()));
    }

    /**
     * Builds student object then sets the createdStudent object.
     */
    private void createStudent() {
        try {
            String salt = HashingUtil.generateSalt(20, new Random());
            Hash hash = HashingUtil.hash(passwordField.getText(), "SHA-512", salt);
            createdStudent = new Student(usernameTextField.getText(), hash);
            createdStudent.setFirstName(firstNameTextField.getText());
            createdStudent.setLastName(lastNameTextField.getText());
            createdStudent.setEmail(emailTextField.getText());
            createdStudent.setMajor(Major.valueOf(majorComboBox.getSelectionModel().getSelectedItem()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Optional<Student> getCreatedStudent() {
        return Optional.ofNullable(createdStudent);
    }
}
