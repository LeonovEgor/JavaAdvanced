package Lesson_6_7.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

public class ChatServer {
    private final int PORT = 8189;
    private Vector<ClientHandler> clients;

    public Vector<ClientHandler> getClients() {
        return clients;
    };

    ServerSocket serverSocket = null;
    private Socket socket = null;

    public ChatServer() throws SQLException {
        clients = new Vector<>();

        try {
            AuthService.connect();
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен, ожидаем подключения...");

            while (true) {
                socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeServer();
            AuthService.disconnect();
        }
    }

    private void closeServer() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendBroadcastMessage(String fromNick, String message) {
        for (ClientHandler handler: clients) {
            sendPrivateMessage(handler, fromNick, message);
        }
    }
    public void sendPrivateMessage(ClientHandler handler, String fromNick, String message) {
        handler.sendMsg(String.format("[%tT] %s: %s", Calendar.getInstance(), fromNick, message));
    }


    public void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("Подключился клиент " + clientHandler.info());
        System.out.println(String.format("В текущий момент клиентов %d: %s", clients.size(), getClientListText()));
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Отключился клиент " + clientHandler.info());
        System.out.println(String.format("Осталось клиентов %d: %s", clients.size(), getClientListText()));
    }

    private String getClientListText() {
        StringBuilder sb = new StringBuilder();
        for (ClientHandler handler: clients) {
            sb.append(handler.info()).append(", ");
        }
        return sb.toString();
    }

    public boolean isAlreadyConnected(String nick) {
        boolean result = false;

        for (ClientHandler handler: clients) {
            if (handler.getNick().equals(nick)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public ClientHandler consistClient(String privateName) {
        ClientHandler result = null;
        for (ClientHandler handler: clients) {
            if (handler.getNick().equals(privateName)) {
                result = handler;
                break;
            }
        }
        return result;

    }

}
