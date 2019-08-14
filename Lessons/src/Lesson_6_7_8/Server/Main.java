package Lesson_6_7_8.Server;

import Lesson_6_7_8.Server.NET.ChatServer;

import java.sql.SQLException;

public class Main {
    private static final int PORT = 8189;

    public static void main(String[] args) {
        try {
            new ChatServer(PORT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
