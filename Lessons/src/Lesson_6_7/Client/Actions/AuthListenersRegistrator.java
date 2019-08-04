package Lesson_6_7.Client.Actions;

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

    public void fireAction(){
        for (AuthListener listener : listeners) {
            listener.alPerformAction();
        }
    }
}