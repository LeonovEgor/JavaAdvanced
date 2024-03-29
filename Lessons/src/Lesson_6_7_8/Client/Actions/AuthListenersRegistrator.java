package Lesson_6_7_8.Client.Actions;

import java.util.ArrayList;
import java.util.List;

public class AuthListenersRegistrator {

    private List<AuthListener> listeners = new ArrayList<>();

    public void addListener(AuthListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(AuthListener listener) {
        listeners.remove(listener);
    }

    public void fireAction(String nick){
        for (AuthListener listener : listeners) {
            listener.alPerformAction(nick);
        }
    }
}