package Lesson_6_7_8.Client.Actions;

import Lesson_6_7_8.Client.NET.ChatMessage;

public interface MessageListener {
    void mlPerformAction(ChatMessage message);
}
