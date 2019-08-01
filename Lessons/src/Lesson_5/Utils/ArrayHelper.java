package Lesson_5.Utils;

public class ArrayHelper {

    public static void fillArray(float[] array, float fillValue) {
        for (int i=0; i< array.length-1; i++) {
            array[i] = fillValue;
        }
    }

    // array - исходный массив, arrayCount - число массивов на выходе
    // Возвращает массив разделенных массивов
    public static float[][] splitArray(float[] array, int arrayCount) {
        if (arrayCount <= 0) throw new IllegalArgumentException("Количество массивов должно быть больше нуля");

        float[][] arrayContainer = new float[arrayCount][];

        int elementsPerArray = array.length / arrayCount;

        for (int i = 0; i < arrayCount; i++) {
            if (i == arrayCount-1 && array.length % arrayCount != 0) // Если массив нельзя поделить нацело
                arrayContainer[i] = new float[array.length - elementsPerArray*i];
            else
                arrayContainer[i] = new float[elementsPerArray];

            System.arraycopy(array, elementsPerArray*i, arrayContainer[i], 0, arrayContainer[i].length);
        }

        return arrayContainer;
    }

    // array - исходный массив, arrayCount - число массивов на выходе
    // Возвращает массив разделенных массивов
    public static float[] mergeArray(float[][] arrayContainer) {

        int length = 0;
        for (float[] ar:arrayContainer) {
            length += ar.length;
        }

        float[] array = new float[length];

        int pos =0;
        for (int i = 0; i < arrayContainer.length; i++) {
            System.arraycopy( arrayContainer[i], 0, array, pos, arrayContainer[i].length);
            pos += arrayContainer[i].length;
        }

        return array;
    }

}
