package Lesson_3.CheckPass;

public class LowerCaseRule extends Rule {

    public LowerCaseRule () {
        super("LowerCaseRule");
    }

    @Override
    public boolean checkRule(String pass) {
        boolean res = false;

        for (Character ch: pass.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                res = true;
                break;
            }
        }

        return res;
    }
}

