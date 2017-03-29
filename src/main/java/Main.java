import database.SQLConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import users.Admin;
import users.User;
import users.students.Student;
import window.controller.AdminViewController;
import window.controller.LoginController;
import window.controller.StudentViewController;
import window.util.WindowUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Main extends Application {
    /**
     * Main access point of the application.
     *
     * @param args Passed application arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Launches application login screen.
        LoginController controller = WindowUtil.displayLogin();

        // If login was successful update user's last login property
        // and open the correct primary window.
        if (controller != null && controller.getLoggedInUser().isPresent()) {
            User loggedInUser = controller.getLoggedInUser().get();
            updateLoginTime(loggedInUser);
            String path = loggedInUser instanceof Student ?
                    "/fxml/student-view.fxml" : "/fxml/admin-view.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            if (loggedInUser instanceof Student) {
                loader.<StudentViewController>getController().init((Student) loggedInUser);
            } else {
                loader.<AdminViewController>getController().init((Admin) loggedInUser);
            }
            primaryStage.setTitle("Agile Records");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/agile-records.png")));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    @Override
    public void stop() throws Exception {
        // Clean up logic (if any)
        super.stop();
    }

    /**
     * Updates a given user instance's last login time property
     * with the current time and pushes this update to the database.
     *
     * @param user The user instance that will be updated.
     */
    private void updateLoginTime(User user) {
        user.setLastLoginTime(LocalDateTime.now());
        try (SQLConnection connection = new SQLConnection()) {
            connection.updateUser(user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
