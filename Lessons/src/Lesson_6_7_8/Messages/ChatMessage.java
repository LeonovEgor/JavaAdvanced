package Lesson_6_7_8.Messages;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {

    public ChatMessage(Date date, String nickFrom, String nickTo, String message, boolean isMyMessage) {
        this.date = date;
        this.nickFrom = nickFrom;
        this.nickTo = nickTo;
        this.message = message;
        this.isMyMessage = isMyMessage;
    }

    private boolean isMyMessage;
    private Date date;
    private String nickFrom;
    private String nickTo;
    private String message;

    public boolean isMyMessage() {
        return isMyMessage;
    }

    public Date getDate() {
        return date;
    }

    public String getNickFrom() {
        return nickFrom;
    }

    public String getNickTo() {
        return nickTo;
    }

    public String getMessage() {
        return message;
    }
}


