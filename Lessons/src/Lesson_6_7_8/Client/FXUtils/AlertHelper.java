package Lesson_6_7_8.Client.FXUtils;

import javafx.scene.control.Alert;

public class AlertHelper {
    public static void ShowMessage(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
