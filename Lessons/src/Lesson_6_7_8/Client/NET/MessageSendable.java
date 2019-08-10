package Lesson_6_7_8.Client.NET;

public interface MessageSendable {
    public boolean isAuthorized();
    public boolean sendMessage(String message, String nickTo);
    public void Auth(String login, String pass);
}
