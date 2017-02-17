package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.users.students.Student;
import main.java.util.WindowUtil;

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
    @FXML
    private Label loggedInAsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Allows cells to determine where each student property should be placed.
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Allows application to detect when rows are double clicked.
        adminTableView.setRowFactory(tableView -> buildRowWithEvent());

        // When clicked, opens window that allows user to create a student object
        addButton.setOnMouseClicked(mouseEvent -> openCreateStudent());

        // When clicked, removes selected rows.
        removeButton.setOnMouseClicked(mouseEvent ->
                adminTableView.getItems()
                        .removeAll(adminTableView.getSelectionModel().getSelectedItems())
        );

        // TODO: Load Students Into Observable List.
    }

    private TableRow<Student> buildRowWithEvent() {
        TableRow<Student> row = new TableRow<>();
        row.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                Student student = row.getItem();
                // TODO: Open Student View Using The Newly Selected Student Object.
            }
        });
        return row;
    }

    private void openCreateStudent() {
        Stage stage = new Stage();
        stage.setTitle("Create Student");
        stage.initModality(Modality.APPLICATION_MODAL);
        CreateStudentController controller = WindowUtil.showWindowAndWait("/fxml/CreateStudent.fxml", stage);
        if (controller != null && controller.getCreatedStudent() != null) {
            adminTableView.getItems().add(controller.getCreatedStudent());
        }
    }
}
