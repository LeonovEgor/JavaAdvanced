package Lesson_1.Participants;

import Lesson_1.Marathon.Competitor;

// 2. Добавить класс Team, который будет содержать название команды, массив из четырех участников
// (в конструкторе можно сразу указыватьвсех участников ), метод для вывода информации о членах команды,
// прошедших дистанцию, метод вывода информации обо всех членах команды.
public class Team {
    private String name;
    private Creature[] creaturesArray;

    public Creature[] getCompetitors() {
        Creature[] CreaturesArrayCopy = new Creature[creaturesArray.length];
        System.arraycopy(creaturesArray, 0, CreaturesArrayCopy, 0, creaturesArray.length);
        return CreaturesArrayCopy;
    }



    public Team(String name, Creature ... creatures) {
        this.name = name;
        creaturesArray = new Creature[creatures.length];
        System.arraycopy(creatures, 0, this.creaturesArray, 0, creatures.length);
    }




    // 1. ... метод вывода информации обо всех членах команды.
    // Вывод информации о команде
    public String info() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Команда: ").append(name).append("\n");

        for (Creature creature: creaturesArray) {
            buffer.append(creature.info()).append("\n");
        }

        return buffer.toString();
    }



    // 1. ... метод для вывода информации о членах команды, прошедших дистанцию ...
    // Вывод информации о участниках, которые не выбыли с дистанции
    public String showResults() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Участники команды ").append(name).append("оставшиеся на дистанции:\n");

        for (Creature creature: creaturesArray) {
            if (creature.isOnDistance())
                buffer.append(creature.info()).append("\n");
        }

        return buffer.toString();
    }

}
