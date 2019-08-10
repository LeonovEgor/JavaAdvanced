package Lesson_6_7_8.Client;

import Lesson_6_7_8.Client.Actions.AuthListener;
import Lesson_6_7_8.Client.Actions.AuthListenersRegistrator;
import Lesson_6_7_8.Client.Actions.MessageListenersRegistrator;
import Lesson_6_7_8.Client.FXUI.Controller;
import Lesson_6_7_8.Client.NET.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application implements AuthListener {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;
    private static final int MIN_HEIGHT = 300;
    private static final int MIN_WIDTH = 200;

    private Client client;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Lesson_6_7_8/Client/FXUI/Chat.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Simple Messenger");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);

        //////
        MessageListenersRegistrator messageListenersRegistrator = new MessageListenersRegistrator();
        AuthListenersRegistrator authListenersRegistrator = new AuthListenersRegistrator();

        client = new Client(messageListenersRegistrator, authListenersRegistrator);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();
        controller.setAuthorized(false);
        messageListenersRegistrator.addListener(controller);
        authListenersRegistrator.addListener(controller);
        authListenersRegistrator.addListener(this);
        controller.setSender(client);

        ///////

        primaryStage.show();

        // Предотвращение закрытия окна для выхода с оповещением сервера
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            client.sendMessage("/end");
        });
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void alPerformAction() {
        Platform.runLater((() -> primaryStage.setTitle("Simple Messenger - " + client.getNick())));
    }
}
