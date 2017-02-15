package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.users.students.Student;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller responsible for Admin.fxml backend and logic.
 */
public class AdminController implements Initializable {
    @FXML
    private TableView<Student> adminTableView;
    @FXML
    private TableColumn<Student, Integer> idTableColumn;
    @FXML
    private TableColumn<Student, String> firstNameTableColumn;
    @FXML
    private TableColumn<Student, String> lastNameTableColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Label statusLabel;

    private ObservableList<Student> students = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // TODO: Load Students Into Observable List.
    }

    @FXML
    private void displayCreateStudent() {
        // TODO: Launch CreateStudent.fxml.
        // TODO: Use returned data to add new student to table view.
    }

    @FXML
    private void removeSelectedStudents() {
        adminTableView.getItems()
                .removeAll(adminTableView.getSelectionModel().getSelectedItems());
    }
}
