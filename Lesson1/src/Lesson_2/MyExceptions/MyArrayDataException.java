package Lesson_2.MyExceptions;

public class MyArrayDataException extends NumberFormatException{
    private int col;
    private int row;
    private String symbol;

    public MyArrayDataException(int col, int row, String symbol) {
        this.col = col;
        this.row = row;
        this.symbol = symbol;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("В ячейке [")
                .append(col)
                .append("][")
                .append(row)
                .append("]")
                .append(" находится не числовое значение: ")
                .append(symbol);
        return sb.toString();
    }
}
