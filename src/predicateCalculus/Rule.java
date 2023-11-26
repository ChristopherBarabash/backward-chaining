package predicateCalculus;

import java.util.ArrayList;
import java.util.Hashtable;

public class Rule implements PCExpression {

    protected ArrayList<Goal> operands;

    private SimpleSentence head;
    private Goal body;

    public Rule(SimpleSentence head) {
        this(head, null);
    }

    public Rule(SimpleSentence head, Goal body) {
        this.head = head;
        this.body = body;
    }

    public SimpleSentence getHead() {
        return head;
    }

    public Goal getBody() {
        return body;
    }

    public int operandCount() {
        return operands.size();
    }

    public Goal getOperand(int i) {
        return operands.get(i);
    }

    @Override
    public PCExpression standardizeVariablesApart(Hashtable<Variable, Variable> newVars) throws CloneNotSupportedException {
        SimpleSentence newHead = (SimpleSentence) head.standardizeVariablesApart(newVars);
        Goal newBody = null;
        if (body != null)
            newBody = (Goal) body.standardizeVariablesApart(newVars);
        return new Rule(newHead, newBody);
    }

    public PCExpression replaceVariables(SubstitutionSet s) throws CloneNotSupportedException {
        ArrayList<Goal> newOperands = new ArrayList<Goal>();
        for (int i = 0; i < operandCount(); i++)
            newOperands.add((Goal) getOperand(i).replaceVariables(s));
        AbstractOperator copy = (AbstractOperator) this.clone();
        copy.setOperands(newOperands);
        return copy;
    }

    public String toString() {
        if (body == null)
            return head.toString();
        return head + " :- " + body;
    }
}