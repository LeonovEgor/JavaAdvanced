package Lesson_2_Enum;

// Требуется реализовать enum DayOfWeek, который будет представлять дни недели.
// С его помощью необходимо решить задачу определения кол-ва рабочих часов до
// конца недели по заднному текущему дню.

// Возвращает кол-во оставшихся рабочих часов до конца
// недели по заданному текущему дню. Считается, что
// текущий день ещё не начался, и рабочие часы за него
// должны учитываться.

public class DayOfWeekMain {

    public static enum DayOfWeek {
        MONDAY("Понедельник", 40),
        TUESDAY("Вторник", 32),
        WEDNESDAY("Среда", 24),
        THURSDAY("Четверг", 16),
        FRIDAY("Пятница", 8),
        SATURDAY("Суббота", 0),
        SUNDAY("Воскресенье", 0);

        int hours;
        String russianName;

        DayOfWeek(String rusianName, int hours) {
            this.russianName = rusianName;
            this.hours = hours;
        }

        public int getWorkingHoursToEndOfWeek() {
            return hours;
        }
        public String getRussianName() {
            return russianName;
        }
    }

    public static void main(final String[] args) {
        for (DayOfWeek day: DayOfWeek.values()) {
            System.out.print("День недели " + day.getRussianName() + ". ");
            System.out.println(getWorkingHours(day));
        }
    }

    private static String getWorkingHours(DayOfWeek dayOfWeek) {
        int hours = dayOfWeek.getWorkingHoursToEndOfWeek();
        return hours == 0 ?
                "Выходной. Рабочих часов не осталось.":
                "Осталось отработать: " + hours + " часов.";

    }
}
