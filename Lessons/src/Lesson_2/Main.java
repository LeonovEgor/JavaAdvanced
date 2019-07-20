package Lesson_2;

import Lesson_2.ArrayHelper.ArrayAdder;
import Lesson_2.MyExceptions.MyArrayDataException;
import Lesson_2.MyExceptions.MyArraySizeException;

public class Main {

    public static void main(String[] args) {
        String[][] array1 = new String[][] {
            {"1", "2", "4", "3"}, {"10", "20", "40", "30"}, {"100", "200", "400", "300"}, {"1000", "2000", "4000", "3000"}
        };

        String[][] array2 = new String[][] { // во второй строке 3 элемента вместо 4х.
                {"1", "2", "4", "3"}, {"10", "20", "40" }, {"100", "200", "400", "300"}, {"1000", "2000", "4000", "3000"}
        };

        String[][] array3 = new String[][] { // В ячейке [1][1] неверное значение
                {"1", "2", "4", "3"}, {"10", "dd", "40", "30"}, {"100", "200", "400", "300"}, {"1000", "2000", "4000", "3000"}
        };

        String[][][] myArrays = new String[][][] {array1, array2, array3};
        for (String[][] array: myArrays) {
            printArray(array);
            try {
                ArrayAdder arrayAdder = new ArrayAdder(array);
                int sum = arrayAdder.calculateSum();
                System.out.println("Сумма значений элементов массива = " + sum);
                System.out.println();
            }
            catch (MyArraySizeException ex) {
                System.out.println("Неверная длина массива: " + ex.getMessage());
                System.out.println();
            }
            catch (MyArrayDataException ex) {
                System.out.println("Неверные данные в массиве: " + ex.getMessage());
                System.out.println();
            }
        }
    }


    public static void printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
