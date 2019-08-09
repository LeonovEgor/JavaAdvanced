package Lesson_6_7_8.Client.NET;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {

    public ChatMessage(Date date, String nick, String message, boolean isMyMessage) {
        this.date = date;
        this.nick = nick;
        this.message = message;
        this.isMyMessage = isMyMessage;
    }

    private boolean isMyMessage;
    private Date date;
    private String nick;
    private String message;

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public Date getDate() {
        return date;
    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return message;
    }
}


