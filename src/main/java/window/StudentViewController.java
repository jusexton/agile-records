package main.java.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.window.controller.EditStudentController;
import main.java.database.SQLConnection;
import main.java.users.students.Course;
import main.java.users.students.Student;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 */
public class StudentViewController implements Initializable {
    private Student displayedStudent;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editstudent.fxml"));
            Parent root = loader.load();
            loader.<EditStudentController>getController().init(displayedStudent, true);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Edit Student");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/agile-records.png")));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (loader.<EditStudentController>getController().getStudent().isPresent()) {
                Student editedStudent = loader.<EditStudentController>getController().getStudent().get();
                init(editedStudent);
                try (SQLConnection connection = new SQLConnection()) {
                    connection.updateUser(editedStudent);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        crnColumn.setCellValueFactory(new PropertyValueFactory<>("CRN"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditHoursColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
    }

    public void init(Student student) {
        courseViewTable.getItems().clear();
        this.displayedStudent = student;
        nameLabel.setText(student.getFirstName() + " " + student.getLastName());
        majorLabel.setText(student.getMajor().toString());
        idLabel.setText(Integer.toString(student.getID()));
        gpaLabel.setText(Double.toString(student.getGPA()));
        usernameLabel.setText(displayedStudent.getUserName());
        courseViewTable.getItems().addAll(student.getCourses());
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
