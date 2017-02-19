package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Launches application login screen.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Admin.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Agile Records");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Clean up logic (if any)
        super.stop();
    }
}
