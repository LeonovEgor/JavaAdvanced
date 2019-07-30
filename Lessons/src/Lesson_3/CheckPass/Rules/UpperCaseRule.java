package Lesson_3.CheckPass.Rules;

import Lesson_3.CheckPass.Rules.Rule;

public class UpperCaseRule extends Rule {

    public UpperCaseRule() {
        super("UpperCaseRule");
    }

    @Override
    public boolean checkRule(String pass) {
        boolean res = false;

        for (Character ch: pass.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                res = true;
                break;
            }
        }

        return res;
    }
}
