package window.util;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.Nullable;
import users.students.Course;
import users.students.Grade;
import users.students.Student;
import window.controller.*;

import java.io.IOException;
import java.util.Optional;

/**
 * Contains tools for window management and display windows with ease.
 */
public abstract class WindowUtil {
    /**
     * Helper function for generating a stage object.
     *
     * @param root  The scene that will be displayed through the stage.
     * @param title The title the stage will have.
     * @return The created stage object.
     */
    private static Stage buildStage(Parent root, String title) {
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle(title);
        stage.getIcons().add(new Image(WindowUtil.class.getResourceAsStream("/icon/agile-records.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    /**
     * Closes a window given a source event.
     *
     * @param event The source event.
     */
    public static void closeWindow(final Event event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Displays a confirmation alert on screen
     *
     * @return Whether the ok button was pressed or not.
     */
    public static boolean displayConfirmationAlert() {
        boolean confirmation = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Commit");
        alert.setHeaderText("Commit Change?");
        alert.setContentText("Executing this action will edit the database and will not be reversible.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                confirmation = true;
            }
        }
        return confirmation;
    }

    /**
     * Displays create course screen.
     *
     * @return The instanced EditCourseController.
     */
    @Nullable
    public static EditCourseController displayCreateCourse() {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-course.fxml"));
            Parent root = loader.load();
            Stage stage = buildStage(root, "Create Course");
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays a course in edit mode.
     *
     * @param course The course that will be displayed.
     * @return The instanced EditCourseController.
     */
    @Nullable
    public static EditCourseController displayEditCourse(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-course.fxml"));
            Parent root = loader.load();
            loader.<EditCourseController>getController().init(course);
            Stage stage = buildStage(root, "Edit Course");
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays create grade screen.
     *
     * @return The instanced EditGradeController.
     */
    @Nullable
    public static EditGradeController displayCreateGrade() {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-grade.fxml"));
            Parent root = loader.load();
            Stage stage = buildStage(root, "Create Grade");
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays a grade object in edit mode.
     *
     * @param grade The grade that will be displayed.
     * @return The instanced EditGradeController.
     */
    @Nullable
    public static EditGradeController displayEditGrade(Grade grade) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-grade.fxml"));
            Parent root = loader.load();
            loader.<EditGradeController>getController().init(grade);
            Stage stage = buildStage(root, "Edit Grade");
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays create student screen.
     *
     * @return The instanced EditStudentController.
     */
    @Nullable
    public static EditStudentController displayCreateStudent() {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-student.fxml"));
            Parent root = loader.load();
            Stage stage = buildStage(root, "Create Student");
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays a student object in edit mode.
     *
     * @param student The student that will be displayed
     * @return The instanced EditStudentController.
     */
    @Nullable
    public static EditStudentController displayEditStudent(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/edit-student.fxml"));
            Parent root = loader.load();
            loader.<EditStudentController>getController().init(student, true);
            Stage stage = buildStage(root, "Edit Student");
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays login screen
     *
     * @return The instanced LoginController.
     */
    @Nullable
    public static LoginController displayLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            Stage stage = buildStage(root, "Agile Records");
            stage.showAndWait();
            return loader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Displays a course object.
     *
     * @param course The course that will be displayed.
     */
    public static void displayCourseView(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowUtil.class.getResource("/fxml/course-view.fxml"));
            Parent root = loader.load();
            loader.<CourseViewController>getController().init(course);
            Stage stage = buildStage(root, course.getName());
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Uses a .fxml path and stage to display a window.
     *
     * @param path  Path to the .fxml file.
     * @param stage The stage that will be used to display the window.
     * @param <T>   Generic
     * @return The controller of the window.
     */

    @Nullable
    public static <T> T showWindowAndWait(final String path, final Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowUtil.class.getResource(path));
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Uses a .fxml path, stage and custom controller to display a window.
     *
     * @param path       Path to the .fxml file.
     * @param stage      The stage that will be sed to display the window.
     * @param controller The controller that will be used to display the given stage.
     * @param <T>        Generic
     * @return The controller of the stage.
     */
    @Nullable
    public static <T> T showWindowAndWait(final String path, final Stage stage, final T controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowUtil.class.getResource(path));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            return fxmlLoader.getController();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
