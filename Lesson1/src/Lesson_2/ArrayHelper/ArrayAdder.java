package Lesson_2.ArrayHelper;

import Lesson_2.MyExceptions.MyArrayDataException;
import Lesson_2.MyExceptions.MyArraySizeException;

public class ArrayAdder {
    public final int DIMENSION = 4;
    public String[][] strArray;

    public ArrayAdder(String[][] array) {
        if (array.length != DIMENSION) throw new MyArraySizeException(DIMENSION, array.length);

        for (int i=0; i<array.length; i++) {
            if (array[i].length != DIMENSION) throw new MyArraySizeException(DIMENSION, array.length);
        }

        strArray = array;
    }

    public int calculateSum() {
        int result = 0;

        for (int i = 0; i < strArray.length; i++) {
            for (int j = 0; j < strArray[i].length; j++) {
                try {
                    int num = Integer.parseInt(strArray[i][j]);  // Пытаемся преобразовать в число
                    result += num;
                } catch (NumberFormatException ex) {
                    throw new MyArrayDataException(i, j, strArray[i][j]);
                }
            }
        }
        return result;
    }
}
