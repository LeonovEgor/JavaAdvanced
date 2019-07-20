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
        MONDAY(40),
        TUESDAY(32),
        WEDNESDAY(24),
        THURSDAY(16),
        FRIDAY(8),
        SATURDAY(0),
        SUNDAY(0);

        int hours;

        DayOfWeek(int hours) {
            this.hours = hours;
        }

        public int getWorkingHoursToEndOfWeek() {
            return hours;
        }
    }

    public static void main(final String[] args) {
        for (DayOfWeek day: DayOfWeek.values()) {
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
