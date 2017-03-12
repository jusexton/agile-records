package main.java.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.users.students.Course;
import main.java.users.students.Grade;
import main.java.util.math.MathUtil;
import main.java.util.window.WindowUtil;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller responsible for CreateCourse.fxml backend and logic.
 */
public class EditCourseController implements Initializable {
    private Course course;

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
    private TableColumn<Grade, String> nameTableColumn;
    @FXML
    private TableColumn<Grade, String> typeTableColumn;
    @FXML
    private TableColumn<Grade, String> scoreTableColumn;
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
        // Launches CreateGrade window.
        Stage stage = new Stage();
        stage.setTitle("Create Grade");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/agile-records.png")));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditGradeController controller = WindowUtil.showWindowAndWait("/fxml/EditGrade.fxml", stage);

        // Handles returned information.
        if (controller != null && controller.getGrade().isPresent()) {
            Grade grade = controller.getGrade().get();
            gradesTableView.getItems().add(grade);
        }
    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        gradesTableView.getItems()
                .removeAll(gradesTableView.getSelectionModel().getSelectedItems());
    }

    /**
     * The add in process that will take place when the
     * user fills in information.
     *
     * @param event The event triggered.
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        // Make sure all required fields are set.
        if (courseNameField.getText().isEmpty() ||
                CRNField.getText().isEmpty() ||
                creditHoursField.getText().isEmpty()) {
            displayErrorLabel("Required Field(s) Blank");
            return;
        }

        // Make sure fields contain correct data.
        if (!MathUtil.isInteger(creditHoursField.getText()) ||
                !MathUtil.isInteger(CRNField.getText())){
            displayErrorLabel("Incorrect Values Passed");
            return;
        }

        createCourse();
        WindowUtil.closeWindow(event);
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        course = null;
        WindowUtil.closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        gradesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void init(Course course) {
        courseNameField.setText(course.getName());
        CRNField.setText(String.valueOf(course.getCRN()));
        creditHoursField.setText(String.valueOf(course.getCreditHours()));
        // TODO: Configure time
        gradesTableView.getItems().addAll(course.getGrades());
    }


    private void createCourse() {
        String name = courseNameField.getText();
        int creditHours = Integer.parseInt(creditHoursField.getText());
        int CRN = Integer.parseInt(CRNField.getText());

        course = new Course(name, creditHours, CRN);
        course.setGrades(gradesTableView.getItems());
        // TODO: Add Time Interval Functionality.
    }

    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public Optional<Course> getCourse() {
        return Optional.ofNullable(course);
    }
}
