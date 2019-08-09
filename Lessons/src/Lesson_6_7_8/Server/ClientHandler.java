package Lesson_6_7_8.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private final String END = "/end";
    private final String AUTH_OK = "/authok";
    private final String AUTH = "/auth";

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
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
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        waitForAuth();
                        if (!isEnding) waitForMessage();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnection();
                        System.out.println(String.format("Соединение  закрыто: %s", info()));
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForMessage() throws IOException {
        while (true) {
            String str = in.readUTF();
            System.out.println(String.format("Client %s: %s", info(), str));

            if (str.equals(END)) {
                ending();
                break;
            }

            if (isPrivateMessage(str)) {
                ClientHandler privateHandler = server.consistClient(getPrivateName(str));
                if (privateHandler != null) {
                    String message = str.substring(str.indexOf(" ", 4), str.length());
                    server.sendPrivateMessage(this, nick, message); // себе
                    server.sendPrivateMessage(privateHandler, nick, message); // кому сообщение
                }
                else {
                    server.sendPrivateMessage(this, nick, "Пользователь с таким ником не подключен"); // себе
                }
            }
            else server.sendBroadcastMessage(nick, str); // всем
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
        sendMsg(END);
        server.removeClient(ClientHandler.this);
        server.sendBroadcastMessage("Server", String.format("Клиент отключился: %s", info()));
    }

    private void waitForAuth() throws IOException, SQLException {
        while (true) {
            String str = in.readUTF();

            if (str.equals(END)) {
                ending();
                break;
            }

            if (str.startsWith(AUTH)) {
                String[] tokens = str.split(" ");
                if (tokens.length != 3) continue;

                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                if (newNick != null) {

                    if (server.isAlreadyConnected(newNick)) {
                        sendMsg("Повторное подключение запрещено");
                        continue;
                    }

                    nick = newNick;
                    server.addClient(ClientHandler.this);
                    sendMsg(String.format("%s %s", AUTH_OK, nick) );
                    break;
                } else {
                    sendMsg("Неверный логин или пароль!");
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

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
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
