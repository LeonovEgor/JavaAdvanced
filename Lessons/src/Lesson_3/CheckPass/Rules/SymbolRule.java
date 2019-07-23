package Lesson_3.CheckPass.Rules;

import Lesson_3.CheckPass.Rules.Rule;

public class SymbolRule extends Rule {

    private Character[] pattern = new Character[] {'"', '!', '@', '#', '$', '%', ',', '^', '&',
            '*', '(', ')', '_', '+', '.', '\\', '[', ']', '{', '}', '`', '~'};

    public SymbolRule() {
        super("SymbolRule");
    }

    @Override
    public boolean checkRule(String pass) {
        boolean res = false;

        for (int i=0; i< pattern.length; i++) {
            if (pass.contains(pattern[i].toString())) {
                res = true;
                break;
            }
        }

        return res;
    }
}

