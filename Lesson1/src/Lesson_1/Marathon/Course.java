package Lesson_1.Marathon;

import Lesson_1.Participants.Team;

//3. Добавить класс Course (полоса препятствий), в котором будут находиться массив препятствий и метод,
// который будет просить команду пройти всю полосу;
public class Course {

    private Obstacle[] obstacleArray;

    public Course(Obstacle ... obstacles) {
        obstacleArray = new Obstacle[obstacles.length];
        System.arraycopy(obstacles, 0, this.obstacleArray, 0, obstacles.length);
    }

    public void doIt(Team team) {
        for (Competitor c : team.getCompetitors()) {
            for (Obstacle o : obstacleArray) {
                o.doIt(c);
                if (!c.isOnDistance()) break;
            }
        }
    }
}
