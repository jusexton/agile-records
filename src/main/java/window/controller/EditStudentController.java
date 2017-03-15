package main.java.window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.database.SQLConnection;
import main.java.users.students.Course;
import main.java.users.students.Major;
import main.java.users.students.Student;
import main.java.security.Hash;
import main.java.security.util.HashingUtil;
import main.java.window.util.WindowUtil;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller responsible for editstudent.fxml backend and logic.
 */
public class EditStudentController implements Initializable {
    private Student student;
    private boolean editMode;

    @FXML
    private Label passwordLabel;
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
    private Label errorLabel;
    @FXML
    private ComboBox<String> majorComboBox;

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        EditCourseController controller = WindowUtil.displayCreateCourse();

        // Handles returned information.
        if (controller != null && controller.getCourse().isPresent()) {
            Course course = controller.getCourse().get();
            courseTableView.getItems().add(course);
        }
    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        courseTableView.getItems()
                .removeAll(courseTableView.getSelectionModel().getSelectedItems());
    }

    /**
     * Uses on screen control values to build the student object.
     *
     * @param event
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        if (editMode) {
            if (usernameTextField.getText().isEmpty() ||
                    majorComboBox.getSelectionModel().isEmpty()) {
                displayErrorLabel("Required Field(s) Blank");
            } else {
                if (this.student.getUserName().equals(usernameTextField.getText()) || usernameIsAvailable()) {
                    Optional<ButtonType> result = WindowUtil.displayConfirmationAlert();
                    if (result.isPresent()){
                        if (result.get() == ButtonType.OK){
                            createStudent();
                            WindowUtil.closeWindow(event);
                        }
                    }
                }
            }
        } else {
            // Make sure required fields are filled.
            if (usernameTextField.getText().isEmpty() ||
                    passwordField.getText().isEmpty() ||
                    majorComboBox.getSelectionModel().isEmpty()) {
                displayErrorLabel("Required Field(s) Blank");
            } else {
                if (usernameIsAvailable()) {
                    createStudent();
                    WindowUtil.closeWindow(event);
                } else {
                    displayErrorLabel("Username Taken");
                }
            }
        }
    }

    /**
     * Closes window
     *
     * @param event
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        student = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Allows cells to determine where each student property should be placed.
        crnTableColumn.setCellValueFactory(new PropertyValueFactory<>("CRN"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditsTableColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));

        courseTableView.setRowFactory(row -> buildRowWithEvent());
        Arrays.asList(Major.values())
                .forEach(value -> majorComboBox.getItems().add(value.toString()));
    }

    public void init(Student student) {
        init(student, false);
    }

    public void init(Student student, boolean editMode) {
        this.student = student;
        this.editMode = editMode;
        usernameTextField.setText(student.getUserName());
        majorComboBox.setValue(student.getMajor().toString());
        firstNameTextField.setText(student.getFirstName());
        lastNameTextField.setText(student.getLastName());
        emailTextField.setText(student.getEmail());
        courseTableView.getItems().addAll(student.getCourses());

        if (editMode) {
            createButton.setText("Save Changes");
            passwordLabel.setText("Password: ");
        }
    }

    /**
     * Builds row with mouse event attached that listens
     * for double click.
     *
     * @return The newly built row.
     */
    private TableRow<Course> buildRowWithEvent() {
        TableRow<Course> row = new TableRow<>();
        row.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                EditCourseController controller = WindowUtil.displayEditCourse(row.getItem());
                if (controller != null && controller.getCourse().isPresent()){
                    int index = row.getIndex();
                    courseTableView.getItems().remove(index);
                    courseTableView.getItems().add(index, controller.getCourse().get());
                }
            }
        });
        return row;
    }

    /**
     * Builds student object then sets the student object.
     */
    private void createStudent() {
        try {
            if (editMode) {
                if (!passwordField.getText().isEmpty()) {
                    String salt = HashingUtil.generateSalt(20, new Random());
                    student.setPassword(HashingUtil.hash(passwordField.getText(), "SHA-512", salt));
                }
            } else {
                String salt = HashingUtil.generateSalt(20, new Random());
                Hash hash = HashingUtil.hash(passwordField.getText(), "SHA-512", salt);
                student = new Student(usernameTextField.getText(), hash);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }

        student.setFirstName(firstNameTextField.getText());
        student.setLastName(lastNameTextField.getText());
        student.setEmail(emailTextField.getText());
        student.setMajor(Major.valueOf(majorComboBox.getValue()));
        student.setCourses(courseTableView.getItems());
    }

    /**
     * Displays the error label with the passed message.
     *
     * @param message The message that will be displayed.
     */
    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Calculates if the requested username is available.
     *
     * @return The result
     */
    private boolean usernameIsAvailable() {
        String username = usernameTextField.getText();
        if (!username.isEmpty()) {
            try (SQLConnection connection = new SQLConnection()) {
                return connection.isUnique(username);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public Optional<Student> getStudent() {
        return Optional.ofNullable(student);
    }

    public boolean inEditMode() {
        return this.editMode;
    }
}
