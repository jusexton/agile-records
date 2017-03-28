package window.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import database.SQLConnection;
import users.Admin;
import users.User;
import users.students.Student;
import window.util.WindowUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller responsible for Admin.fxml backend and logic.
 */
public class AdminViewController implements Initializable {
    private Admin loggedInAdmin;
    private ObservableList<Student> students;
    private StudentViewController studentViewController;

    @FXML
    private SplitPane splitPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Student> studentTableView;
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
    private Label studentCountLabel;
    @FXML
    private Label usernameLabel;

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        EditStudentController controller = WindowUtil.displayCreateStudent();

        // Make sure student object was returned
        if (controller != null && controller.getStudent().isPresent()) {
            // Confirm user decision.
            if (WindowUtil.displayConfirmationAlert()) {
                // Commence add procedure
                Student student = controller.getStudent().get();
                students.add(student);
                try (SQLConnection connection = new SQLConnection()) {
                    connection.addUser(student);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void handleRemoveButtonAction(ActionEvent event) {
        // Confirm user decision.
        if (WindowUtil.displayConfirmationAlert()) {
            // Execute remove procedure
            List<Student> selectedStudents = studentTableView.getSelectionModel().getSelectedItems();
            try (SQLConnection connection = new SQLConnection()) {
                selectedStudents.forEach(student -> {
                    connection.removeUserById(student.getID());
                    // Checks if a removed student is being displayed.
                    // Sets student display to nothing if so.
                    if (studentViewController != null){
                        if (studentViewController.getDisplayedStudent()
                                .getUserName()
                                .equals(student.getUserName())){
                            splitPane.getItems().set(1, new AnchorPane());
                        }
                    }
                });
                students.removeAll(selectedStudents);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void handleRefreshButtonAction(ActionEvent event) {
        students.clear();
        syncTable();
    }

    public AdminViewController() {
        students = FXCollections.observableArrayList();
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
        studentTableView.setRowFactory(row -> buildRowWithEvent());
        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Updates student count label on list change.
        students.addListener((ListChangeListener<? super Student>)
                (c -> studentCountLabel.setText(String.valueOf(students.size()))));

        syncTable();

        FilteredList<Student> filteredData = new FilteredList<>(students, p -> true);
        searchTextField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(student -> {
                    // If search bar is empty, all elements will be displayed.
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return student.getFirstName().toLowerCase().contains(lowerCaseFilter) ||
                            student.getLastName().toLowerCase().contains(lowerCaseFilter) ||
                            student.getUserName().toLowerCase().contains(lowerCaseFilter);
                })
        );

        SortedList<Student> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(studentTableView.comparatorProperty());
        studentTableView.setItems(sortedData);
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
                displayStudentView(row.getItem());
            }
        });
        return row;
    }

    /**
     * Loaded student view with given student into right split pane.
     *
     * @param student The student instance that is loaded.
     */
    private void displayStudentView(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student-view.fxml"));
            AnchorPane root = loader.load();
            loader.<StudentViewController>getController().init(student, true);
            studentViewController = loader.getController();
            splitPane.getItems().set(1, root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Synces student TableView with student database table
     */
    private void syncTable() {
        // Populates table on load.
        try (SQLConnection connection = new SQLConnection()) {
            connection.getAllUsers()
                    .get("students")
                    .forEach(user -> students.add((Student) user));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User getLoggedInAdmin() {
        return this.loggedInAdmin;
    }
}
