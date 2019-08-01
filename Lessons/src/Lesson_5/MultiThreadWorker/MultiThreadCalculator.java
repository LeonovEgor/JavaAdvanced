package Lesson_5.MultiThreadWorker;

public class MultiThreadCalculator implements Runnable {

    private float[] array;

    public MultiThreadCalculator(float[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        for (int i = 0; i < array.length-1; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

}