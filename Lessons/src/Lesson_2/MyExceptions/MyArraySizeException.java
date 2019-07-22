package Lesson_2.MyExceptions;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException {
    private String error;

    public MyArraySizeException(int dimension, int input) {
        StringBuilder sb = new StringBuilder();
        sb.append("Неверная длина массива. Задано ")
                .append(input)
                .append(". Требуется ")
                .append(dimension);

        error = sb.toString();
    }
    public MyArraySizeException(int dimension, int input, int row) {
        StringBuilder sb = new StringBuilder();
        sb.append("Неверный размер строки массива. В строке [")
                .append(row)
                .append("] размер ")
                .append(input)
                .append(". Требуется ")
                .append(dimension);
        error = sb.toString();
    }

    @Override
    public String getMessage() {
        return error;

    }
}
