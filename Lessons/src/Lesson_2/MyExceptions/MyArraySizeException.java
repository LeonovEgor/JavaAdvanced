package Lesson_2.MyExceptions;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException {
    private int dimension;
    private int input;

    public MyArraySizeException(int dimension, int input) {
        this.dimension = dimension;
    }

    @Override
    public String getMessage() {
        return "Размерность массива должна быть " + dimension + ". Переданное значение " + input;

    }
}
