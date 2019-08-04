package Lesson_6_7.Client.Actions;

import java.util.ArrayList;
import java.util.List;

public class MessageListenersRegistrator {

    private List<MessageListener> listeners = new ArrayList<>();

    public void addListener(MessageListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    public void fireAction(String message){
        for (MessageListener listener : listeners) {
            listener.mlPerformAction(message);
        }
    }
}