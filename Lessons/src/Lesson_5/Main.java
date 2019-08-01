package Lesson_5;

import Lesson_5.MultiThreadWorker.WorkSplitter;
import Lesson_5.Utils.ArrayHelper;

public class Main {
    private static final int SIZE = 10000000;
    private static final int THREAD_COUNTS = 5;
    private static final float INITIAL_VALUE = 1;

    private static float[] arr = new float[SIZE];

    public static void main(String[] args) {
        System.out.println("Начало обработки в один поток");
        long timeSpent = oneThreadWork(arr);
        System.out.println(String.format("Обработка заняла: %,d милисекунд" ,timeSpent));

        System.out.println(String.format("Начало обработки в %d поток(ов)", THREAD_COUNTS));
        WorkSplitter splitter = new WorkSplitter(arr, INITIAL_VALUE, THREAD_COUNTS);
        try {
            timeSpent = splitter.Do();
        }
        catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        System.out.println(String.format("Обработка заняла: %,d милисекунд(ы)" ,timeSpent));
    }


    public static long oneThreadWork (float[] array) {

        ArrayHelper.fillArray(array, INITIAL_VALUE);

        long start = System.currentTimeMillis();
        for (int i = 0; i < array.length-1; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        return System.currentTimeMillis() - start;
    }

}
