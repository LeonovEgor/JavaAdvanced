package Lesson_6_7.Client;

import Lesson_6_7.Client.Actions.ListenersRegistrator;
import Lesson_6_7.Client.FXUI.Controller;
import Lesson_6_7.Client.NET.Client;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {
    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new FXMLLoader(getClass().getResource("/Lesson_6_7/Client/FXUI/chat.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Simple Messenger");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(200);

        //////
        ListenersRegistrator registrator = new ListenersRegistrator();

        Client client = new Client(registrator);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Controller controller = loader.getController();
        registrator.addListener(controller);
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
}
