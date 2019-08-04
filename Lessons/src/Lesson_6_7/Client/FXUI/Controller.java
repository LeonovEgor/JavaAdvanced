package Lesson_6_7.Client.FXUI;

import Lesson_6_7.Client.Actions.MessageListener;
import Lesson_6_7.Client.FXUtils.AlertHelper;
import Lesson_6_7.Client.NET.MessageSandable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    private MessageSandable sender;
    public void setSender(MessageSandable sender) {
        this.sender = sender;
    }

    public void sendMessage() {
        if (tfMessage.getText().trim().isEmpty()) return;

        boolean res = sender.sendMessage(tfMessage.getText());
        if (!res) {
            AlertHelper.ShowMessage(Alert.AlertType.WARNING,
                    "Проблема", "Не удалось отправить сообщение");
        }
        else {
            tfMessage.clear();
            tfMessage.requestFocus();
        }

    }

    @Override
    public void performAction(String message) {
        if (message.equals("/end")) {
            System.out.println("Приложение закрывается");
            Platform.exit();
        }
        else {
            taHistory.appendText(message + "\n");
        }
    }
}
