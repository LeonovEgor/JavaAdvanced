package Lesson_1;

import Lesson_1.Marathon.*;
import Lesson_1.Participants.Cat;
import Lesson_1.Participants.Dog;
import Lesson_1.Participants.Human;
import Lesson_1.Participants.Team;

public class Main {
    public static void main(String[] args) {
        Team team = new Team(
                "Звездочка",
                new Human("Владлен"),
                new Human("Федора"),
                new Cat("Барсик"),
                new Dog("Бобик"));

        Course course = new Course(
                new Cross(80),
                new Wall(2),
                new Water(1),
                new Cross(120));

        System.out.println(team.info()); // Полный список команды

        course.doIt(team); // Просим команду пройти полосу
        System.out.println();

        System.out.println(team.showResults()); // Показываем результаты

    }
}