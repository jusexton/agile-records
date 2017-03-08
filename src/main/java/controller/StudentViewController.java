package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.java.users.students.Student;

import java.net.URL;
import java.util.ResourceBundle;

/**
 */
public class StudentViewController implements Initializable {
    private Student displayedStudent;

    //Table values
    @FXML
    private TableView<Student> studentViewTable;
    @FXML
    private TableColumn<Student, Integer> crnColumn;
    @FXML
    private TableColumn<Student, String> courseNameColumn;
    @FXML
    private TableColumn<Student, Integer> creditHoursColumn;
    @FXML
    private TableColumn<Student, Integer> timeColumn;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Set cell value factories.
        // TODO: Add double click on cell capabilities.
    }

    public void init(Student student) {
        this.displayedStudent = student;
        usernameLabel.setText(displayedStudent.getUserName());
        // TODO: Set other labels
    }

    public void init(Student student, boolean isAdmin) {
        init(student);
        if (isAdmin) {
            adminButtonBar.setVisible(true);
        }
    }

    public Student getDisplayedStudent() {
        return displayedStudent;
    }
}
