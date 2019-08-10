package Lesson_6_7_8.Server;

import Lesson_6_7_8.Messages.ChatMessage;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Date;

public class ClientHandler {
    private final String END = "/end";
    private final String AUTH_OK = "/authok";
    private final String AUTH = "/auth";

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ChatServer server;
    private String nick;
    private boolean isEnding = false;

    public String getNick() {
        return nick != null ? nick : "" ;
    };

    public ClientHandler(ChatServer server, Socket socket) {
        if (socket == null) throw new IllegalArgumentException("Socket не может быть null");
        this.socket = socket;
        this.server = server;


        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            new Thread(() -> {
                try {
                    waitForAuth();
                    if (!isEnding) waitForMessage();
                } catch (IOException | SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    closeConnection();
                    System.out.println(String.format("Соединение  закрыто: %s", info()));
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForMessage() throws IOException {
        while (true) {
            ChatMessage message = null;
            try {
                message = (ChatMessage) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Сообщение принято, но не может быть десериализовано.");
                continue;
            }
            System.out.println(String.format("Client %s: %s ", info(), message.getMessage()));

            if (message.getMessage().equals(END)) {
                ending();
                break;
            }

            if (isPrivateMessage(message.getMessage())) {
                ClientHandler privateHandler = server.consistClient(getPrivateName(message.getMessage()));
                if (privateHandler != null) {
                    server.sendPrivateMessage(this, nick, message.getMessage()); // себе
                    server.sendPrivateMessage(privateHandler, nick, message.getMessage()); // кому сообщение
                }
                else {
                    server.sendPrivateMessage(this, nick, "Пользователь с таким ником не подключен"); // себе
                }
            }
            else server.sendBroadcastMessage(nick, message.getMessage()); // всем
        }
    }

    private String getPrivateName(String str) {
        return str.substring(3, str.indexOf(" ", 3));
    }

    private boolean isPrivateMessage(String str) {
        return (str.startsWith("/w") && str.length() > 4);
    }

    private void ending() {
        isEnding = true;
        sendMsg(new ChatMessage(new Date(), nick, END, true));
        server.removeClient(ClientHandler.this);
        server.sendBroadcastMessage("Server", String.format("Клиент отключился: %s", info()));
    }

    private void waitForAuth() throws IOException, SQLException, ClassNotFoundException {
        while (true) {
            ChatMessage message = (ChatMessage) in.readObject();

            if (message.getMessage().equals(END)) {
                ending();
                break;
            }

            if (message.getMessage().startsWith(AUTH)) {
                String[] tokens = message.getMessage().split(" ");
                if (tokens.length != 3) continue;

                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                if (newNick != null) {

                    if (server.isAlreadyConnected(newNick)) {
                        sendMsg(new ChatMessage(new Date(), nick,
                                "Повторное подключение запрещено", true));
                        continue;
                    }

                    nick = newNick;
                    server.addClient(ClientHandler.this);
                    sendMsg(new ChatMessage(new Date(), nick, String.format("%s %s", AUTH_OK, nick), true));
                    break;
                } else {
                    sendMsg(new ChatMessage(new Date(), nick, "Неверный логин или пароль!", true));
                }
            }
        }
    }

    private boolean isAlreadyConnected(String nick) {
        boolean result = false;
        for (ClientHandler handler: server.getClients() ) {
            if (handler.nick.equals(nick)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void closeConnection() {
        try {
            if (in != null) in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (out != null) out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(ChatMessage message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String info() {
        return socket != null ?
                String.format("Клиент: адрес: %s ник: %s", socket.getInetAddress(), nick)
                : "Socket не создан!";
    }
}
