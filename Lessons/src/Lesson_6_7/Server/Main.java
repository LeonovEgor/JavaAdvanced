package Lesson_6_7.Server;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            new ChatServer();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
