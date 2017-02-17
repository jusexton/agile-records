package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.users.students.Course;
import main.java.users.students.Student;
import main.java.util.WindowUtil;
import main.java.util.security.Hash;
import main.java.util.security.HashingUtil;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller responsible for CreateStudent.fxml backend and logic.
 */
public class CreateStudentController implements Initializable {
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

    private Student createdStudent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Allows cells to determine where each student property should be placed.
        crnTableColumn.setCellValueFactory(new PropertyValueFactory<>("CRN"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));

        // When clicked, opens window that allows user to create a course object
        addButton.setOnMouseClicked(mouseEvent -> {
            // TODO: Add Button Functionality
        });

        // When clicked, removes selected rows.
        removeButton.setOnMouseClicked(mouseEvent ->
                courseTableView.getItems()
                        .removeAll(courseTableView.getSelectionModel().getSelectedItems())
        );

        // Closes window after storing created student object;
        createButton.setOnMouseClicked(mouseEvent -> {
            createStudent();
            WindowUtil.closeWindow(mouseEvent);
        });

        // Closes window
        cancelButton.setOnMouseClicked(mouseEvent -> {
            createdStudent = null;
            WindowUtil.closeWindow(mouseEvent);
        });
    }

    private void createStudent() {
        // Make sure required fields are filled.
        if (usernameTextField.getText() == null || passwordField.getText() == null) {
            return;
        }

        try {
            String salt = HashingUtil.generateSalt(20, new Random());
            Hash hash = HashingUtil.hash(passwordField.getText(), "SHA-512", salt);
            createdStudent = new Student(usernameTextField.getText(), hash);
            createdStudent.setFirstName(firstNameTextField.getText());
            createdStudent.setLastName(lastNameTextField.getText());
            createdStudent.setEmail(emailTextField.getText());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Student getCreatedStudent() {
        return createdStudent;
    }
}
