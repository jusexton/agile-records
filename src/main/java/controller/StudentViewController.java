package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.users.students.Student;

import java.net.URL;
import java.util.ResourceBundle;

/**
 */
public class StudentViewController implements Initializable {
    private Student loggedInStudent;

    //Table values
    @FXML
    private TableView<Student> studentViewTable;
    @FXML
    private TableColumn<Student, Integer> crnColumn;
    @FXML
    private TableColumn<Student, String> courseColumn;
    @FXML
    private TableColumn<Student, Integer> timeColumn;
    @FXML
    private TableColumn<Student, Integer> creditHrsColumn;

    // Buttons
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;

    //Label values
    @FXML
    private Label nameLabel;
    @FXML
    private Label majorLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label logLabel;
    @FXML
    private Label gpaLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void init(Student student){
        this.loggedInStudent = student;
        logLabel.setText(loggedInStudent.getUserName());
    }

    public Student getLoggedInStudent(){
        return loggedInStudent;
    }
}
