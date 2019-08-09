package Lesson_6_7.Client.NET;

public interface MessageSendable {
    public boolean isAuthorized();
    public boolean sendMessage(String message);
    public void Auth(String login, String pass);
}
