package Lesson_3.CheckPass;

public class PassChecker {
    public static boolean checkPass(String pass) {
        boolean res = true;

        RuleCollection col = new RuleCollection();

        col.add(new LowerCaseRule());
        col.add(new UpperCaseRule());
        col.add(new DigitsRule());
        col.add(new LengthRule(8, 20));
        col.add(new SymbolRule());

        System.out.println(String.format("Используемые паретны %s", col.toString()));

        for (Rule rule: col) {
            boolean curRes = rule.checkRule(pass);
            if (!curRes) {
                res = false;
                break;
            }
        }

        return res;
    }
}
