package Lesson_6_7.Server;

import java.sql.*;

public class AuthService {
    private static final String NICK_NAME = "nickname";
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) throws SQLException {

        String qry = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);
        ResultSet rs = stmt.executeQuery(qry);

        if (rs.next()) {
            return rs.getString(NICK_NAME);
        }

        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
