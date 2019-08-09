package Lesson_6_7.Client.FXUI;

import Lesson_6_7.Client.Actions.AuthListener;
import Lesson_6_7.Client.Actions.MessageListener;
import Lesson_6_7.Client.FXUtils.AlertHelper;
import Lesson_6_7.Client.NET.MessageSendable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class Controller implements MessageListener, AuthListener {

    @FXML
    TextArea taHistory;
    @FXML
    TextField tfMessage;
    @FXML
    Button btnSend;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginfield;

    @FXML
    PasswordField passwordfiled;

    public void setAuthorized(boolean isAuthorized) {
        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    private MessageSendable sender;
    public void setSender(MessageSendable sender) {
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
    public void mlPerformAction(String message) {
        if (message.equals("/end")) {
            System.out.println("Приложение закрывается");
            Platform.exit();
        }
        else {
            taHistory.appendText(message + "\n");
        }
    }

    public void tryToAuth() {
        if (!sender.isAuthorized())
            sender.Auth(loginfield.getText(),passwordfiled.getText());
    }

    @Override
    public void alPerformAction() {
        setAuthorized(true);
    }
}
