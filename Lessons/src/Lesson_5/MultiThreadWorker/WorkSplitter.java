package Lesson_5.MultiThreadWorker;

import Lesson_5.Utils.ArrayHelper;

public class WorkSplitter {
    private int threadsCount;
    private float initialArrayValue;
    private float[] array;
    public float[] getArray() {
        return array;
    }

    public WorkSplitter(float[] array, float initialArrayValue, int threadsCount) {
        this.array = array;
        this.threadsCount = threadsCount;
        this.initialArrayValue = initialArrayValue;
    }

    // Возвращает время, затраченное на вычисления в миллисекундах
    public long Do() throws InterruptedException {

        ArrayHelper.fillArray(array, initialArrayValue);

        long startTime = System.currentTimeMillis();

        float[][] arrayContainer = ArrayHelper.splitArray(array, threadsCount);

        Thread[] threadContainer = new Thread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threadContainer[i] = new Thread(new MultiThreadCalculator(arrayContainer[i]));
            threadContainer[i].start();
        }

        for (Thread thread : threadContainer) {
            thread.join();
        }

        array = ArrayHelper.mergeArray(arrayContainer);

        return System.currentTimeMillis()- startTime;
    }

}
