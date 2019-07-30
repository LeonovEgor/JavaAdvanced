package Lesson_3.CheckPass.Rules;

public abstract class Rule {

    private String name;

    public Rule (String name) {
        this.name = name;
    }

    public abstract boolean checkRule(String pass);

    @Override
    public String toString() {
        return name;
    }
}

