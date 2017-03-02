package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.users.students.Course;
import main.java.users.students.Grade;
import main.java.util.window.WindowUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller responsible for CreateCourse.fxml backend and logic.
 */
public class CreateCourseController implements Initializable {
    private Course createdCourse;

    @FXML
    private TextField courseNameField;
    @FXML
    private TextField CRNField;
    @FXML
    private TextField creditHoursField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TextField startTimeLabel;
    @FXML
    private TableView<Grade> gradesTableView;
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
    private void handleAddButtonAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Create Grade");
        stage.initModality(Modality.APPLICATION_MODAL);
        CreateGradeController controller = WindowUtil.showWindowAndWait("/fxml/CreateGrade.fxml", stage);
        if (controller != null && controller.getCreatedGrade() != null) {
            // TODO: Implement add grade logic
        }
    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        // TODO: Implement remove grade logic.
    }

    /**
     * The add in process that will take place when the
     * user fills in information.
     *
     * @param event The event triggered.
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        // Makes sure all required fields are set.
        if (courseNameField.getText().isEmpty() ||
                CRNField.getText().isEmpty() ||
                creditHoursField.getText().isEmpty()) {
            displayErrorLabel("Required Field(s) Blank");
        } else {
            createCourse();
            WindowUtil.closeWindow(event);
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        createdCourse = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void createCourse() {
        String name = courseNameField.getText();
        int creditHours = Integer.parseInt(creditHoursField.getText());
        int CRN = Integer.parseInt(CRNField.getText());

        if (startDatePicker.getValue() != null &&
                endDatePicker.getValue() != null &&
                !startTimeLabel.getText().isEmpty()) {
            // TODO: Create Time Interval
        } else {
            displayErrorLabel("Date Fields Missing");
            return;
        }
        createdCourse = new Course(name, creditHours, CRN);
    }

    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public Optional<Course> getCreatedCourse() {
        return Optional.ofNullable(createdCourse);
    }
}
