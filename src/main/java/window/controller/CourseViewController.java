package main.java.window.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.users.students.Course;
import main.java.users.students.Grade;
import main.java.window.util.WindowUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 */
public class CourseViewController implements Initializable {
    @FXML
    private Label nameLabel;
    @FXML
    private Label CRNLabel;
    @FXML
    private Label creditHoursLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label gpaLabel;
    @FXML
    private Label scoreLabel;
    @FXML
    private TableView<Grade> gradeTableView;
    @FXML
    private TableColumn<Grade, String> nameColumn;
    @FXML
    private TableColumn<Grade, String> typeColumn;
    @FXML
    private TableColumn<Grade, Double> scoreColumn;
    @FXML
    private Button closeButton;

    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        WindowUtil.closeWindow(event);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    public void init(Course course) {
        nameLabel.setText(course.getName());
        CRNLabel.setText(String.valueOf(course.getCRN()));
        creditHoursLabel.setText(String.valueOf(course.getCreditHours()));

        if (course.getDateInterval() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");
            LocalDate startDate = course.getDateInterval().getStart();
            LocalDate endDate = course.getDateInterval().getEnd();
            dateLabel.setText(String.format("%s - %s", startDate.format(formatter), endDate.format(formatter)));
        }

        // TODO: Time Formatting Bug
        if (course.getTimeInterval() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mma");
            LocalTime startTime = course.getTimeInterval().getStart();
            LocalTime endTime = course.getTimeInterval().getEnd();
            timeLabel.setText(String.format("%s - %s", startTime.format(formatter), endTime.format(formatter)));
        }

        gpaLabel.setText(String.valueOf(course.getAverage()));
        String score = String.format("%s / %s", course.getTotalScore(), (double) course.getGrades().size() * 100);
        scoreLabel.setText(score);

        gradeTableView.getItems().addAll(course.getGrades());
    }
}
