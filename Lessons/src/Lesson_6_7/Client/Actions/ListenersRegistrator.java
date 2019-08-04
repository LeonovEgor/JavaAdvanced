package Lesson_6_7.Client.Actions;

import java.util.ArrayList;
import java.util.List;

public class ListenersRegistrator {

    private List listeners = new ArrayList();

    public void addListener(MessageListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }

    public void fireAction(String message){
        for (Object listener1 : listeners) {
            MessageListener listener = (MessageListener) listener1;
            listener.performAction(message);
        }
    }
}