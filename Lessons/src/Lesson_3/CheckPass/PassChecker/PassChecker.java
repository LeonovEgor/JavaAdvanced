package Lesson_3.CheckPass.PassChecker;

import Lesson_3.CheckPass.Rules.*;

public class PassChecker {
    private RuleCollection col = new RuleCollection();

    public boolean checkPass(String pass) {
        boolean res = true;

        for (Rule rule: col) {
            boolean curRes = rule.checkRule(pass);
            if (!curRes) {
                res = false;
                break;
            }
        }

        return res;
    }

    public void add(Patterns ... patterns) {
        for (Patterns patern: patterns) {
            switch (patern) {

                case LOWER_CASE_RULE:
                    col.add(new LowerCaseRule());
                    break;
                case UPPER_CASE_RULE:
                    col.add(new UpperCaseRule());
                    break;
                case SYMBOL_RULE:
                    col.add(new SymbolRule());
                    break;
                case DIGITS_RULE:
                    col.add(new DigitsRule());
                    break;
                case LENGTH_RULE:
                    col.add(new LengthRule(8, 20));
                    break;
            }
        }
    }

    public String info() {
        return String.format("Используемые правила %s", col.toString());
    }
}
