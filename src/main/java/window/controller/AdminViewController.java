package main.java.window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.database.SQLConnection;
import main.java.users.Admin;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.window.StudentViewController;
import main.java.window.util.WindowUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller responsible for Admin.fxml backend and logic.
 */
public class AdminViewController implements Initializable {
    private Admin loggedInAdmin;

    @FXML
    private SplitPane splitPane;
    @FXML
    private TableView<Student> adminTableView;
    @FXML
    private TableColumn<Student, Integer> idTableColumn;
    @FXML
    private TableColumn<Student, String> usernameTableColumn;
    @FXML
    private TableColumn<Student, String> lastLoginTableColumn;
    @FXML
    private TableColumn<Student, String> firstNameTableColumn;
    @FXML
    private TableColumn<Student, String> lastNameTableColumn;
    @FXML
    private TableColumn<Student, String> majorTableColumn;
    @FXML
    private TableColumn<Student, String> GPATableColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private Button commitButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Label usernameLabel;

    /**
     * Opens editstudent.fxml, adds the optional result to the table view
     * if it is present and adds the Student to the database.
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        EditStudentController controller = displayCreateStudent();

        // Make sure student object was returned
        if (controller != null && controller.getStudent().isPresent()) {
            // Confirm user decision.
            Optional<ButtonType> result = WindowUtil.displayConfirmationAlert();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    // Commence add procedure
                    Student student = controller.getStudent().get();
                    adminTableView.getItems().add(student);
                    try (SQLConnection connection = new SQLConnection()) {
                        connection.addUser(student);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Removes all selected items from the table view and database.
     */
    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        // Confirm user decision.
        Optional<ButtonType> result = WindowUtil.displayConfirmationAlert();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                // Execute remove procedure
                List<Student> selectedStudents = adminTableView.getSelectionModel().getSelectedItems();
                adminTableView.getItems().removeAll(selectedStudents);
                try (SQLConnection connection = new SQLConnection()) {
                    selectedStudents.forEach(student -> connection.removeUserById(student.getID()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the tableview to reflect the current contents
     * of the database.
     *
     * @param event
     */
    @FXML
    private void handleRefreshButtonAction(ActionEvent event) {
        adminTableView.getItems().clear();
        syncTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Allows cells to determine where each student property should be placed.
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        usernameTableColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        lastLoginTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastLoginTime"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        majorTableColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        GPATableColumn.setCellValueFactory(new PropertyValueFactory<>("GPA"));

        // Allows application to detect when rows are double clicked.
        adminTableView.setRowFactory(tableView -> buildRowWithEvent());
        adminTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        syncTable();
    }

    /**
     * Initializes any data the controller needs before launch.
     *
     * @param admin The admin object that will be logged into the application.
     */
    public void init(Admin admin) {
        this.loggedInAdmin = admin;
        usernameLabel.setText(admin.getUserName());
    }

    /**
     * Builds row with mouse event attached that listens
     * for double click.
     *
     * @return The newly built row.
     */
    private TableRow<Student> buildRowWithEvent() {
        TableRow<Student> row = new TableRow<>();
        row.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
                displayStudent(row.getItem());
            }
        });
        return row;
    }

    private void displayStudent(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/studentview.fxml"));
            AnchorPane root = loader.load();
            loader.<StudentViewController>getController().init(student, true);
            splitPane.getItems().set(1, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private EditStudentController displayCreateStudent() {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Create Student");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/agile-records.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        return WindowUtil.showWindowAndWait("/fxml/editstudent.fxml", stage);
    }

    private void syncTable() {
        // Populates table on load.
        try (SQLConnection connection = new SQLConnection()) {
            connection.getAllUsers()
                    .get("students")
                    .forEach(user -> adminTableView.getItems().add((Student) user));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User getLoggedInAdmin() {
        return this.loggedInAdmin;
    }
}
