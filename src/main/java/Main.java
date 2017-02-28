package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.controller.LoginController;
import main.java.users.User;
import main.java.users.students.Student;
import main.java.util.window.WindowUtil;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Launches application login screen.
        Stage loginStage = new Stage();
        loginStage.setTitle("Agile Records");
        loginStage.setResizable(false);
        LoginController controller = WindowUtil.showWindowAndWait("/fxml/Login.fxml", loginStage);

        // If login was successful, open the correct primary window.
        User loggedInUser = controller != null ? controller.getLoggedInUser() : null;
        if (loggedInUser != null) {
            String path = loggedInUser instanceof Student ?
                    "/fxml/StudentView.fxml" : "/fxml/AdminView.fxml";

            // TODO: Allow controller to initialize user object before executing.

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            primaryStage.setTitle("Agile Records");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    @Override
    public void stop() throws Exception {
        // Clean up logic (if any)
        super.stop();
    }
}
