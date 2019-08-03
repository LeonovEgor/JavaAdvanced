package Lesson_6.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private ChatServer server;
    private ClientHandler mySelf;


    public ClientHandler(ChatServer server, Socket socket) {
        mySelf = this;
        this.socket = socket;
        this.server = server;
        String socketName = socket.toString();

        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            server.addClient(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            System.out.println(String.format("Client %s: %s", socketName, str));
                            if (str.equals("/end")) {
                                sendMsg(str);
                                server.removeClient(mySelf);
                                server.broadcastMsg(String.format("Клиент %s отключился", socketName));
                                break;
                            }
                            server.broadcastMsg(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnection();
                        System.out.println(String.format("Соединение %s закрыто", socketName));
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public String getClientName() {
        return socket != null ? socket.toString(): "Socket не создан!";
    }
}
