package main.java.controller;

import javafx.fxml.FXML;

import javafx.stage.Modality;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import main.java.util.window.WindowUtil;
import main.java.users.students.Grade;
import main.java.users.students.GradeType;
import main.java.users.students.Grade;
import main.java.users.students.Course;
import org.joda.time.Interval;

/**
 * Created by germ21 on 2/26/2017.
 * Controller responsible for AddCourse.fxml backend and logic.
 */
public class AddCourseController {
    //may not even be needed
    private Grade addedGrade;
    private Course createdCourse;

    @FXML
    private TextField courseNameField;
    @FXML
    private TextField CRNField;
    @FXML
    private DatePicker classTimeDatePicker;
    @FXML
    private TextField creditHoursField;
    @FXML
    private Button createButton;
    @FXML
    private Button addGradeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label createFailLabel;

    /**
     * The add in process that will take place when the
     * user fills in information.
     *
     * @param event The event triggered.
     */
    @FXML
    private void handleCreateButtonAction(ActionEvent event) {
        //sets all values
        if (this.courseNameField.getText().isEmpty() || this.CRNField.getText().isEmpty()|| this.creditHoursField.getText().isEmpty() || this.classTimeDatePicker.getValue()==null) {
            createFailLabel.setVisible(true);
        } else {
            createCourse();
            WindowUtil.closeWindow(event);
        }


    }
    @FXML
    private void handleAddGradeButtonAction(ActionEvent event) {

        Stage stage = new Stage();
        stage.setTitle("Create Course");
        stage.initModality(Modality.APPLICATION_MODAL);
        AddGradeController controller = WindowUtil.showWindowAndWait("/fxml/AddGrade.fxml", stage);
        if (controller != null && controller.getCreatedGrade()!= null) {
            //this.createdCourse.addGrade(controller.getCreatedGrade());

            //Can just use line from above instead of bottom two
            addedGrade = controller.getCreatedGrade();
        }
        //this.createdCourse.addGrade(addedGrade);


    }
    @FXML
    private void createCourse() {
        int creditHours =  Integer.parseInt(this.creditHoursField.getText());
        int CRN = Integer.parseInt(this.CRNField.getText());
        String time = this.classTimeDatePicker.getValue().toString();
        String name = this.courseNameField.getText();

        this.createdCourse = new Course(name, creditHours, CRN);

        this.createdCourse.setTime(Interval.parse(time));
    }
    @FXML
    public Course getCreatedCourse(){return this.createdCourse;}
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        // get a handle to the stage
        createdCourse = null;
        WindowUtil.closeWindow(event);
    }


}
