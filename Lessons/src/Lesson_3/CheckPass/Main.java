package Lesson_3.CheckPass;

// Создать метод для проверки пароля
//1 Обязательно есть хоть 1 цифра
//2 Не менее 8 символов и не более 20
//3 Должны быть большие и маленькие буквы
//4 Обязательно дожен быть спец символ

import Lesson_3.CheckPass.PassChecker.PassChecker;
import Lesson_3.CheckPass.PassChecker.Patterns;

public class Main {

    public static void main(String[] args) {

        String pass = "Password12$";
        System.out.println(String.format("Пароль %s", pass));

        // Вариант 1 свой
        System.out.println("Вариант 1:");
        PassChecker checker = new PassChecker();
        checker.add(
                Patterns.DIGITS_RULE,
                Patterns.LENGTH_RULE,
                Patterns.LOWER_CASE_RULE,
                Patterns.SYMBOL_RULE,
                Patterns.UPPER_CASE_RULE
        );
        System.out.println(checker.info());
        System.out.println(checker.checkPass(pass) ? "Пароль подходит": "Пароль не подходит");

        // Вариант 2 регулярные выражения (делал с использованием Интернет)
        System.out.println("Вариант 2:");
        String pattern = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+'.,`]).{8,20}";
        if (pass.matches(pattern)) {
            System.out.println("Пароль подходит");
        } else {
            System.out.println("Пароль не подходит");
        }


    }


}
