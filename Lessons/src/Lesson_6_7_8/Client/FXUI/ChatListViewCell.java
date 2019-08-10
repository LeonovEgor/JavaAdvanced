package Lesson_6_7_8.Client.FXUI;


import Lesson_6_7_8.Messages.ChatMessage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class ChatListViewCell extends ListCell<ChatMessage> {

    @FXML
    private Label lblNick;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblMessage;

    @FXML
    private VBox vBoxCell;

    @FXML
    private HBox captionPanel;


    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(ChatMessage chatMessage, boolean empty) {
        super.updateItem(chatMessage, empty);

        if(empty || chatMessage == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/Lesson_6_7_8/Client/FXUI/ChatCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            lblNick.setText(chatMessage.getNickFrom());
            lblTime.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(chatMessage.getDate()));
            lblMessage.setText(chatMessage.getMessage());

            if (chatMessage.isMyMessage()) {
                vBoxCell.setAlignment(Pos.CENTER_RIGHT);
                captionPanel.setAlignment(Pos.CENTER_RIGHT);
            }

            setText(null);
            setGraphic(vBoxCell);
        }

    }
}
