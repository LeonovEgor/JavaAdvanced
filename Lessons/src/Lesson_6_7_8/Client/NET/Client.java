package Lesson_6_7_8.Client.NET;

import Lesson_6_7_8.Client.Actions.AuthListenersRegistrator;
import Lesson_6_7_8.Client.Actions.MessageListenersRegistrator;
import Lesson_6_7_8.Messages.ChatMessage;
import Lesson_6_7_8.Messages.MessageType;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Client implements MessageSendable {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private MessageListenersRegistrator messageRegistrator;
    private AuthListenersRegistrator authRegistrator;

    private boolean isAuthorized = false;
    private String nick;

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    public Client(MessageListenersRegistrator messageRegistrator, AuthListenersRegistrator authRegistrator) {
        this.messageRegistrator = messageRegistrator;
        this.authRegistrator = authRegistrator;
    }

    public void openConnection(String serverAddr, int port) throws IOException {
        socket = new Socket(serverAddr, port);
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

    private void closeConnection() {
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
                messageRegistrator.fireAction(new ChatMessage(MessageType.AUTH_ERROR, nick,
                        "Невозможно определить результат авторизации."));
                continue;
            }
            if (message.getMessageType().equals(MessageType.AUTH_OK)) {
                nick = message.getNickFrom();
                isAuthorized = true;
                authRegistrator.fireAction(nick);
                break;
            } else {
                messageRegistrator.fireAction(message);
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
                messageRegistrator.fireAction(new ChatMessage(MessageType.ERROR_MESSAGE, nick,
                        "Поступило сообщение, которое не может быть разобрано."));
                continue;
            }

            messageRegistrator.fireAction(message);
            if (message.getMessageType().equals(MessageType.END)) {
                closeConnection();
                break;
            }
        }
    }

    public boolean sendObject(ChatMessage message) {
        boolean result;

        try {
            out.writeObject(message);
            out.flush();
            result = true;
        } catch (IOException e1) {
            e1.printStackTrace();
            result = false;
        }
        return result;
    }

    @Override
    public boolean sendMessage(String text) {
        ChatMessage message;

        if (text.equals("/end")) { // конец
            message = new ChatMessage(MessageType.END, nick, "");
        }
        else if (text.startsWith("/blacklist")) { // добавление в черный список
            String[] token = text.split(" ", 2);
            message = new ChatMessage(new Date(), this.nick, token[1], MessageType.ADD_BLOCK, "");
        }
        else if (text.startsWith("/w") && text.length() > 4) { // приватное сообщение
            String[] token = text.split(" ", 3);
            message = new ChatMessage(new Date(), this.nick, token[1], MessageType.PRIVATE_MESSAGE, token[2]);
        }
        else { // broadcast
            message = new ChatMessage(new Date(), this.nick, "", MessageType.BROADCAST_MESSAGE, text);
        }

        return sendObject(message);
    }

    @Override
    public void Auth(String login, int passHash) {
        sendObject(new ChatMessage(login, passHash));
    }

}
