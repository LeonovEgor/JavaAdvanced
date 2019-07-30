package Lesson_3.PhoneBook;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {

    // <Телефон key, Фамилия value>
    private HashMap<String, String> map;


    public PhoneBook() {
        map = new HashMap<>();
    }

    public void add(String family, String ... numbers) {
        for (String number: numbers ) {
            if (!map.containsKey(number))
                map.put(number, family);
            else {
                System.out.println("номер " + number + " уже существует");
                // (или throw new IllegalArgumentException)
            }
        }
    }

    // Вернуть список телефонов по фамилии
    public ArrayList<String> get(String family) {
        ArrayList<String> phones = new ArrayList<>();

        for (String number : map.keySet()) {
            if (map.get(number).equals(family)) {
                phones.add(number);
            }
        }

        return phones;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
