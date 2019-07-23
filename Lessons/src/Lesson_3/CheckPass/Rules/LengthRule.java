package Lesson_3.CheckPass.Rules;

public class LengthRule extends Rule {
    private int min;
    private int max;

    public LengthRule(int min, int max) {
        super("LengthRule");
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean checkRule(String pass) {
        return pass.length() > min && pass.length() < max;
    }


    @Override
    public String toString() {
        return String.format("%s(from:%d, to:%d)", super.toString(), min, max);
    }
}
