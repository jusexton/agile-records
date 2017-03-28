package window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import time.DateInterval;
import time.TimeInterval;
import users.students.Course;
import users.students.Grade;
import util.MathUtil;
import window.util.WindowUtil;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller responsible for CreateCourse.fxml backend and logic.
 */
public class EditCourseController implements Initializable {
    private Course course;
    private boolean editMode;

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
    private TextField startTimeTextField;
    @FXML
    private TextField endTimeTextField;
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
        EditGradeController controller = WindowUtil.displayCreateGrade();

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
                !MathUtil.isInteger(CRNField.getText())) {
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

        gradesTableView.setRowFactory(row -> buildRowWithEvent());
    }

    /**
     * Initializes course instance into window.
     *
     * @param course The course instance that will be initialized.
     */
    public void init(Course course) {
        createButton.setText("Save Changes");
        courseNameField.setText(course.getName());
        CRNField.setText(String.valueOf(course.getCRN()));
        creditHoursField.setText(String.valueOf(course.getCreditHours()));
        if (course.getDateInterval() != null) {
            startDatePicker.setValue(course.getDateInterval().getStart());
            endDatePicker.setValue(course.getDateInterval().getEnd());
        }

        if (course.getTimeInterval() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
            startTimeTextField.setText(course.getTimeInterval().getStart().format(formatter));
            endTimeTextField.setText(course.getTimeInterval().getEnd().format(formatter));
        }
        gradesTableView.getItems().addAll(course.getGrades());
    }

    /**
     * Builds row with mouse event attached that listens
     * for double click.
     *
     * @return The newly built row.
     */
    private TableRow<Grade> buildRowWithEvent() {
        TableRow<Grade> row = new TableRow<>();
        row.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                EditGradeController controller = WindowUtil.displayEditGrade(row.getItem());
                if (controller != null && controller.getGrade().isPresent()) {
                    int index = row.getIndex();
                    gradesTableView.getItems().remove(index);
                    gradesTableView.getItems().add(index, controller.getGrade().get());
                }
            }
        });
        return row;
    }

    /**
     * Creates course instance from interface values.
     */
    private void createCourse() {
        String name = courseNameField.getText();
        int creditHours = Integer.parseInt(creditHoursField.getText());
        int CRN = Integer.parseInt(CRNField.getText());

        course = new Course(name, creditHours, CRN);
        course.setGrades(gradesTableView.getItems());

        if (startDatePicker.getValue() != null && endDatePicker.getValue() != null){
            DateInterval dateInterval = new DateInterval(startDatePicker.getValue(), endDatePicker.getValue());
            course.setDateInterval(dateInterval);
        }

        if (!startTimeTextField.getText().isEmpty() && !endTimeTextField.getText().isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
            TimeInterval timeInterval = new TimeInterval(
                    LocalTime.parse(startTimeTextField.getText().replaceAll(" ", ""), formatter),
                    LocalTime.parse(endTimeTextField.getText().replaceAll(" ", ""), formatter));
            course.setTimeInterval(timeInterval);
        }
    }

    /**
     * Displays error message to interface for user's viewing.
     *
     * @param message The message that will be displayed.
     */
    private void displayErrorLabel(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    public Optional<Course> getCourse() {
        return Optional.ofNullable(course);
    }

    public boolean inEditMode() {
        return this.editMode;
    }
}
