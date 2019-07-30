package Lesson_3.Game.Heroes;

import java.util.Random;

public class Assasin extends Hero {

    int cricitalHit;
    Random random = new Random();

    public Assasin(int heal, String name, int damage, int addHeal) {
        super(heal, name, damage, addHeal);
        this.cricitalHit = random.nextInt(50);
    }

    @Override
    public void hit(Hero hero) {
        if (hero != this) {
            if(health < 0) {
                System.out.println("Герой погиб и бить не может!");
            } else {
                int fullDamage = damage + cricitalHit;
                hero.causeDamage(fullDamage);
                System.out.println(this.name + " нанес урон " + fullDamage + " " + hero.name);
            }

        }
    }

    @Override
    public void healing(Hero hero) {
        System.out.println("Убийцы не умеют лечить!");
    }
}
