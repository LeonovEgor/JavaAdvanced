package Lesson_6_7.Client;

import Lesson_6_7.Client.Actions.AuthListener;
import Lesson_6_7.Client.Actions.AuthListenersRegistrator;
import Lesson_6_7.Client.Actions.MessageListenersRegistrator;
import Lesson_6_7.Client.FXUI.Controller;
import Lesson_6_7.Client.NET.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application implements AuthListener {
    private Client client;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Lesson_6_7/Client/FXUI/chat.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Simple Messenger");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(200);

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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

            @Override
            public void handle(WindowEvent event) {
                event.consume();
                client.sendMessage("/end");
            }

        });
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void alPerformAction() {
        //Platform.runLater((() -> primaryStage.setTitle("Simple Messenger - " + client.getNick())));
        Platform.runLater(
            new Runnable() {
                @Override
                public void run() {
                    primaryStage.setTitle("Simple Messenger - " + client.getNick());
                }
            });
    }
}
