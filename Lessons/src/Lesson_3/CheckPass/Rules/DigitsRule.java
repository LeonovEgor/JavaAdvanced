package Lesson_3.CheckPass.Rules;

public class DigitsRule extends Rule {

    public DigitsRule() {
        super("DigitsRule");
    }

    @Override
    public boolean checkRule(String pass) {
        boolean res = false;

        for (Character ch: pass.toCharArray()) {
            if (Character.isDigit(ch)) {
                res = true;
                break;
            }
        }

        return res;
    }
}