package Lesson_6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
    private Vector<ClientHandler> clients;

    ServerSocket serverSocket = null;
    private Socket socket = null;

    public ChatServer() {
        clients = new Vector<>();

        try {
            serverSocket = new ServerSocket(8189);
            System.out.println("Сервер запущен, ожидаем подключения...");

            while (true) {
                socket = serverSocket.accept();

                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    }

    public void broadcastMsg(String msg) {
        for (ClientHandler o: clients) {
            o.sendMsg(msg);
        }
    }

    public void addClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        System.out.println("Подключился клиент " + clientHandler.getClientName());
        System.out.println(String.format("В текущий момент клиентов %d: %s", clients.size(), getClientList()));
    }

    public void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Отключился клиент " + clientHandler.getClientName());
        System.out.println(String.format("Осталось клиентов %d: %s", clients.size(), getClientList()));
    }

    private String getClientList() {
        StringBuilder sb = new StringBuilder();
        for (ClientHandler handler: clients) {
            sb.append(handler.getClientName()).append(", ");
        }
        return sb.toString();
    }
}
