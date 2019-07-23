package Lesson_3.Game.Game;

import Lesson_3.Game.Heroes.Doctor;
import Lesson_3.Game.Heroes.Hero;

import java.util.Random;

public class Game {
    private Team team1;
    private Team team2;

    public Game(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public void play() {

        Random randomStep = new Random();
        Random randomHealing = new Random();

        int i=0;
        do {
            System.out.println(String.format("---- Раунд %s  ----", ++i));
            Hero hero = team1.getRandomHero();

            if (hero instanceof Doctor)
               hero.healing(team1.getRandomHero());
             else
              hero.hit(team2.getRandomHero());

            if (team2.isTeamDeath()) break;
            hero = team2.getRandomHero();

            if (hero instanceof Doctor)
                hero.healing(team2.getRandomHero());
             else
                hero.hit(team1.getRandomHero());

            System.out.println("---------------");

            team1.info();
            team2.info();

        } while (!team1.isTeamDeath() && !team2.isTeamDeath());

        if (team1.isTeamDeath()) System.out.println("Победила команда 2");
        else System.out.println("Победила команда 1");

    }
}
