package window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import database.SQLConnection;
import users.Admin;
import users.students.Course;
import users.students.Student;
import util.StringUtil;
import window.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 */
public class StudentViewController implements Initializable {
    private Student displayedStudent;
    private Admin admin;
    private boolean loggingOut = false;

    //Table values
    @FXML
    private TableView<Course> courseViewTable;
    @FXML
    private TableColumn<Course, Integer> crnColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, Integer> creditHoursColumn;

    // Buttons
    @FXML
    private ButtonBar toolButtonBar;
    @FXML
    private ButtonBar adminButtonBar;
    @FXML
    private Button editButton;

    //Label values
    @FXML
    private Label nameLabel;
    @FXML
    private Label majorLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label gpaLabel;

    @FXML
    private void handleLogoutButtonAction(ActionEvent event){
        loggingOut = true;
        WindowUtil.closeWindow(event);
    }

    @FXML
    private void handleEditButtonAction(ActionEvent event) {
        EditStudentController controller = WindowUtil.displayEditStudent(displayedStudent, admin);

        if (controller != null && controller.getStudent().isPresent()) {
            // Config window with edited student.
            Student editedStudent = controller.getStudent().get();
            init(editedStudent);
            // Update database.
            try (SQLConnection connection = new SQLConnection()) {
                connection.updateUser(editedStudent);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crnColumn.setCellValueFactory(new PropertyValueFactory<>("CRN"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditHoursColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));

        courseViewTable.setRowFactory(row -> buildRowWithEvent());
    }

    /**
     * Initializes student instance variables to window.
     *
     * @param student The student instance that will be loaded.
     */
    public void init(Student student) {
        courseViewTable.getItems().clear();
        this.displayedStudent = student;

        String label = String.format("Student Name: %s %s",
                student.getFirstName(),
                student.getLastName());
        nameLabel.setText(label);

        label = String.format("Major: %s", StringUtil.formatMajor(student.getMajor()));
        majorLabel.setText(label);

        label = String.format("Student ID: %s", student.getID());
        idLabel.setText(label);

        label = String.format("Unofficial GPA: %s", student.getGPA());
        gpaLabel.setText(label);

        label = String.format("Username: %s",student.getUserName() );
        usernameLabel.setText(label);
        courseViewTable.getItems().addAll(student.getCourses());
    }

    /**
     * Initialize controller with student in admin mode.
     *
     * @param student The student instance that will be displayed.
     * @param admin The admin viewing the student
     */
    public void init(Student student, Admin admin) {
        this.admin = admin;
        init(student);
        if (admin != null) {
            adminButtonBar.setVisible(true);
            toolButtonBar.setVisible(false);
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
                WindowUtil.displayCourseView(row.getItem());
            }
        });
        return row;
    }

    public Student getDisplayedStudent() {
        return displayedStudent;
    }

    public boolean isLoggingOut(){
        return this.loggingOut;
    }
}
