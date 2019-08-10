package Lesson_6_7_8.Client.NET;

import Lesson_6_7_8.Client.Actions.AuthListenersRegistrator;
import Lesson_6_7_8.Client.Actions.MessageListenersRegistrator;
import Lesson_6_7_8.Messages.ChatMessage;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Client implements MessageSendable {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private final String AUTH_OK = "/authok";

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private MessageListenersRegistrator mlRegistrator;
    private AuthListenersRegistrator alRegistrator;

    private boolean isAuthorized = false;
    private String nick;

    public String getNick() {
        return nick != null? nick: "";
    }


    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    public Client(MessageListenersRegistrator mlRegistrator, AuthListenersRegistrator alRegistrator) {
        this.mlRegistrator = mlRegistrator;
        this.alRegistrator = alRegistrator;
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());


        new Thread(() -> {
            try {
                waitForAuthorizedMessage();
                waitForMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForAuthorizedMessage() throws IOException {
        while (true) {
            ChatMessage message;
            try {
                message = (ChatMessage) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                mlRegistrator.fireAction(new ChatMessage(new Date(),"Приложение",
                        "Невозможно определить результат авторизации.", true));
                continue;
            }
            if (message.getMessage().startsWith(AUTH_OK)) {
                nick = message.getNickFrom();
                isAuthorized = true;
                alRegistrator.fireAction();
                break;
            } else {
                mlRegistrator.fireAction(message);
            }
        }
    }

    private void waitForMessage() throws IOException {
        while (true) {
            ChatMessage message;

            try {
                message = (ChatMessage) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                mlRegistrator.fireAction(new ChatMessage(new Date(), "Приложение",
                        "Поступило сообщение, которое не может быть разобрано.", true));
                continue;
            }

            mlRegistrator.fireAction(message);
            if (message.getMessage().equalsIgnoreCase("/end")) {
                closeConnection();
                break;
            }
        }
    }


    @Override
    public boolean sendMessage(String message) {
        boolean result;

        try {
            out.writeObject(new ChatMessage(new Date(), this.nick, message, false));
            out.flush();
            result = true;
        } catch (IOException e1) {
            e1.printStackTrace();
            result = false;
        }

        return result;
    }

    @Override
    public void Auth(String login, String pass) {
        sendMessage(String.format("/auth %s %s",login, pass));
    }

}
