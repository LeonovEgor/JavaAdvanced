package Lesson_6.Client.Chat;

import Lesson_6.Client.Actions.MessageListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller implements MessageListener {

    @FXML
    TextArea taHistory;
    @FXML
    TextField tfMessage;
    @FXML
    Button btnSend;

    public Controller() {

    }

    public void sendMessage() {
        taHistory.appendText(tfMessage.getText() + "\n");
        tfMessage.clear();
        tfMessage.requestFocus();
    }

    @Override
    public void performAction(String message) {
        taHistory.appendText(message);
    }
}
