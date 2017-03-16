package main.java.window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.database.SQLConnection;
import main.java.users.students.Course;
import main.java.users.students.Student;
import main.java.window.util.WindowUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 */
public class StudentViewController implements Initializable {
    private Student displayedStudent;
    private boolean isAdmin;

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
    private void handleEditButtonAction(ActionEvent event) {
        EditStudentController controller = WindowUtil.displayEditStudent(displayedStudent);

        if (controller != null && controller.getStudent().isPresent()) {
            Student editedStudent = controller.getStudent().get();
            init(editedStudent);
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

    public void init(Student student) {
        courseViewTable.getItems().clear();
        this.displayedStudent = student;
        nameLabel.setText(student.getFirstName() + " " + student.getLastName());
        majorLabel.setText(student.getMajor().toString());
        idLabel.setText(Integer.toString(student.getID()));
        gpaLabel.setText(Double.toString(student.getGPA()));
        usernameLabel.setText(student.getUserName());
        courseViewTable.getItems().addAll(student.getCourses());
    }

    public void init(Student student, boolean isAdmin) {
        this.isAdmin = isAdmin;
        init(student);
        if (isAdmin) {
            adminButtonBar.setVisible(true);
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
}
