package Lesson_6.Client;

import Lesson_6.Client.Actions.ListenersRegistrator;
import Lesson_6.Client.Actions.MessageListener;
import Lesson_6.Client.FXUI.Controller;
import Lesson_6.Client.NET.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new FXMLLoader(getClass().getResource("/Lesson_6/Client/FXUI/chat.fxml"));
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
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
