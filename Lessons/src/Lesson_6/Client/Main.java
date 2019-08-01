package Lesson_6.Client;

import Lesson_6.Client.Actions.ListenersRegistrator;
import Lesson_6.Client.Actions.MessageListener;
import Lesson_6.Client.NET.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/Lesson_6/Client/Chat/chat.fxml"));
        primaryStage.setTitle("Simple Messenger");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(200);

        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        ListenersRegistrator registrator = new ListenersRegistrator();

        Client client = new Client(registrator);
        client.openConnection();

        launch(args);
        registrator.addListener((MessageListener) FXMLLoader.getDefaultClassLoader());



    }
}
