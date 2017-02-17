package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.users.students.Student;
import main.java.util.security.Hash;
import main.java.util.security.HashingUtil;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
        // Allows cells to determine where each student property should be placed.
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Allows application to detect when rows are double clicked.
        adminTableView.setRowFactory(tableView -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                    Student student = row.getItem();
                    System.out.println("Row Click Detected");
                    // TODO: Open Student View Using The Newly Selected Student Object.
                }
            });
            return row;
        });

        // When clicked, opens window that allows user to create a student object
        addButton.setOnMouseClicked(mouseEvent -> {
            // TODO: Add Button Functionality
        });

        // When clicked, removes selected rows.
        removeButton.setOnMouseClicked(mouseEvent ->
                adminTableView.getItems()
                        .removeAll(adminTableView.getSelectionModel().getSelectedItems())
        );

        // Test Data
        try {
            Hash hash = HashingUtil.hash("password", "SHA-512", "salt");
            Student student = new Student("username", hash, 1);
            student.setFirstName("First");
            student.setLastName("Last");
            students.add(student);
            adminTableView.getItems().addAll(students);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // TODO: Load Students Into Observable List.
    }

    private void displayCreateStudent() {
        // TODO: Launch CreateStudent.fxml.
        // TODO: Use returned data to add new student to table view.
    }

}
