package Lesson_6_7_8.Client.FXUI;

import Lesson_6_7_8.Client.Actions.AuthListener;
import Lesson_6_7_8.Client.Actions.MessageListener;
import Lesson_6_7_8.Client.FXUtils.AlertHelper;
import Lesson_6_7_8.Client.NET.ChatMessage;
import Lesson_6_7_8.Client.NET.MessageSendable;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements MessageListener, AuthListener, Initializable {

    @FXML
    ListView<ChatMessage> lvHistory;
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

    private MessageSendable sender;

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

    public void tryToAuth() {
        if (!sender.isAuthorized())
            sender.Auth(loginfield.getText(),passwordfiled.getText());
    }

    @Override
    public void mlPerformAction(ChatMessage message) {
        if (message.getMessage().equals("/end")) {
            System.out.println("Приложение закрывается");
            Platform.exit();
        }
        else {
            lvHistory.getItems().add(message);
        }
    }

    @Override
    public void alPerformAction() {
        setAuthorized(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lvHistory.setCellFactory(chatListView -> new ChatListViewCell());
    }
}
