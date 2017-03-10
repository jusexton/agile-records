package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.java.controller.AdminViewController;
import main.java.controller.LoginController;
import main.java.controller.StudentViewController;
import main.java.database.SQLConnection;
import main.java.users.Admin;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.util.window.WindowUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;

// TODO: Find icon for application

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Launches application login screen.
        Stage loginStage = new Stage();
        loginStage.setTitle("Agile Records");
        loginStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/AgileRecords.png")));
        loginStage.setResizable(true);
        LoginController controller = WindowUtil.showWindowAndWait("/fxml/Login.fxml", loginStage);

        // If login was successful, open the correct primary window.
        if (controller != null && controller.getLoggedInUser().isPresent()) {
            User loggedInUser = controller.getLoggedInUser().get();
            updateLoginTime(loggedInUser);
            String path = loggedInUser instanceof Student ?
                    "/fxml/StudentView.fxml" : "/fxml/AdminView.fxml";

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            if (loggedInUser instanceof Student) {
                loader.<StudentViewController>getController().init((Student) loggedInUser);
            } else {
                loader.<AdminViewController>getController().init((Admin) loggedInUser);
            }
            primaryStage.setTitle("Agile Records");
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon/AgileRecords.png")));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    @Override
    public void stop() throws Exception {
        // Clean up logic (if any)
        super.stop();
    }

    private void updateLoginTime(User user) {
        user.setLastLoginTime(LocalDateTime.now());
        try (SQLConnection connection = new SQLConnection()) {
            connection.updateUser(user);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
