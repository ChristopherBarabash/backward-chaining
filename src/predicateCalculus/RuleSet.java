package predicateCalculus;

import java.util.Hashtable;

public class RuleSet {
    private Rule[] rules;

    public RuleSet(Rule... rules) {
        this.rules = rules;
    }

    public Rule getRuleStandardizedApart(int i) throws CloneNotSupportedException {
        Rule rule =
                (Rule) rules[i].
                        standardizeVariablesApart(
                                new Hashtable<Variable, Variable>());
        return rule;
    }

    public Rule getRule(int i) {
        return rules[i];
    }

    public int getRuleCount() {
        return rules.length;
    }
}