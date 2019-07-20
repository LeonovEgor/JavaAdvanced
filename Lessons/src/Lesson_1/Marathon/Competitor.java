package Lesson_1.Marathon;

// Состязания
public interface Competitor {
    void run(int dist);
    void swim(int dist);
    void jump(int height);
    boolean isOnDistance();
    String info();
}