package Lesson_3.Game.Game;

import Lesson_3.Game.Heroes.Hero;

import java.util.Random;

public class Team {
    private Hero[] team;
    private String name;

    public Team(String name, Hero[] team) {
        this.name = name;
        this.team = team;
    }

    public int getLength() {
        return team.length;
    }

    public Hero getHero(int index) {
        return team[index];
    }

    // Определяет жив ли хоть кто нибуль в команде
    public Boolean isTeamDeath() {
        boolean result = true;

        for (Hero hero: team) {
            if (hero.getHealth() > 0 ) {
                result = false;
                break;
            }
        }

        return result;
    }

    public Hero getRandomHero() {
        Random rnd = new Random();
        int index;

        do {
            index = rnd.nextInt(team.length);
        } while(team[index].getHealth() < 0);

        return team[index];
    }

    public void info() {
        System.out.println("======================");
        System.out.println(String.format("Команда %s: Состояние", name));
        System.out.println("======================");

        for (Hero hero: team) {
            System.out.println(hero.info());
        }
        System.out.println("----------------------");


    }
}
