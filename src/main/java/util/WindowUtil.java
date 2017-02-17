package main.java.util;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contains tools for window management.
 */
public abstract class WindowUtil {
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

    public static void closeWindow(final Event event){
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
