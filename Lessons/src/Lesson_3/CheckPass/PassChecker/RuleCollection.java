package Lesson_3.CheckPass.PassChecker;

import Lesson_3.CheckPass.Rules.Rule;

import java.util.HashSet;
import java.util.Iterator;

public class RuleCollection implements Iterable<Rule> {
    private HashSet<Rule> rules = new HashSet<>();

    void add(Rule rule) {
        rules.add(rule);
    }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    @Override
    public String toString() {
        return rules.toString();
    }
}
