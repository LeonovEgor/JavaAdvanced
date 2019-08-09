package Lesson_6_7.Client.NET;

import Lesson_6_7.Client.Actions.AuthListenersRegistrator;
import Lesson_6_7.Client.Actions.MessageListenersRegistrator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements MessageSendable {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;
    private final String AUTH_OK = "/authok";

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
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
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitForAuthorizedMessage();
                    waitForMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void waitForMessage() throws IOException {
        while (true) {
            String strFromServer = in.readUTF();
            mlRegistrator.fireAction(strFromServer);
            if (strFromServer.equalsIgnoreCase("/end")) {
                closeConnection();
                break;
            }
        }
    }

    private void waitForAuthorizedMessage() throws IOException {
        while (true) {
            String str = in.readUTF();

            if (str.startsWith(AUTH_OK)) {
                nick = str.substring(str.indexOf(" ", 1), str.length());
                isAuthorized = true;
                alRegistrator.fireAction();
                break;
            } else {
                mlRegistrator.fireAction(str);
            }
        }
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

    @Override
    public boolean sendMessage(String message) {
        boolean result;

        try {
            out.writeUTF(message);
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
