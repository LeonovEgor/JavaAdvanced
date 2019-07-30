package Lesson_3.Game;

import Lesson_3.Game.Game.Game;
import Lesson_3.Game.Game.Team;
import Lesson_3.Game.Heroes.Assasin;
import Lesson_3.Game.Heroes.Doctor;
import Lesson_3.Game.Heroes.Hero;
import Lesson_3.Game.Heroes.Warrior;

import java.util.Random;

public class Main {
    public static void main(String[] args) {


        Hero[] team1 = new Hero[]{new Warrior(250, "Тигрил", 50, 0)
                , new Assasin(150, "Акали", 70, 0)
                , new Doctor(120, "Жанна", 0, 10)};


        Hero[] team2 = new Hero[]{new Warrior(290, "Минотавр", 60, 0)
                , new Assasin(160, "Джинкс", 90, 0)
                , new Doctor(110, "Зои", 0, 10)};

        Game game = new Game(new Team("1", team1), new Team("2", team2));
        game.play();
    }

}
