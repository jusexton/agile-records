package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.users.students.Student;

/**
 * Created by Daniel on 2/26/2017.
 */
public class StudentViewController {

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





}
