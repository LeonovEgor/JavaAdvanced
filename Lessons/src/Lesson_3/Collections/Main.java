package Lesson_3;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        String[] array = new String[] {"Кошка", "Собака", "Кошка", "Белка", "Заяц", "Кошка", "Собака", "Белка",
                "Кошка", "Конь", "Петух", "Заяц", "Мышь", "Крыса", "Волк",
                "Кошка", "Верблюд", "Собака", "Белка"};

        System.out.println(GetUniqueMap(array));
    }

    private static HashMap<String, Integer> GetUniqueMap(String[] array) {
        HashMap<String, Integer> map = new HashMap<>();

        for(String word: array) {
            Integer count = map.get(word);
            map.put(word, count == null ? 1 : count + 1);
        }

        return map;
    }

}
