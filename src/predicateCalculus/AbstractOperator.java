package predicateCalculus;

import java.util.ArrayList;
import java.util.Hashtable;

public abstract class AbstractOperator implements Goal, Cloneable {
    protected ArrayList<Goal> operands;

    public AbstractOperator(Goal... operands) {
        Goal[] operandArray = operands;
        this.operands = new ArrayList<Goal>();
        for (int i = 0; i < operandArray.length; i++) {
            this.operands.add(operandArray[i]);
        }
    }

    public AbstractOperator(ArrayList<Goal> operands) {
        this.operands = operands;
    }

    public void setOperands(ArrayList<Goal> operands) {
        this.operands = operands;
    }

    public int operandCount() {
        return operands.size();
    }

    public Goal getOperand(int i) {
        return operands.get(i);
    }

    public Goal getFirstOperand() {
        return operands.get(0);
    }

    public AbstractOperator getOperatorTail()
            throws CloneNotSupportedException {
        ArrayList<Goal> tail = new
                ArrayList<Goal>(operands);
        tail.remove(0);
        AbstractOperator tailOperator =
                (AbstractOperator) this.clone();
        tailOperator.setOperands(tail);
        return tailOperator;
    }

    public boolean isEmpty() {
        return operands.isEmpty();
    }

    public PCExpression
    replaceVariables(SubstitutionSet s) throws CloneNotSupportedException {
        ArrayList<Goal> newOperands =
                new ArrayList<Goal>();
        for (int i = 0; i < operandCount(); i++)
            newOperands.add((Goal) getOperand(i).replaceVariables(s));
        AbstractOperator copy = (AbstractOperator) this.clone();
        copy.setOperands(newOperands);
        return copy;
    }

    public PCExpression
    standardizeVariablesApart(
            Hashtable<Variable, Variable> newVars) throws CloneNotSupportedException {
        ArrayList<Goal> newOperands =
                new ArrayList<Goal>();
        for(int i = 0; i < operandCount(); i++)
            newOperands.add((Goal)getOperand(i).
                    standardizeVariablesApart(newVars));
        AbstractOperator copy =
                (AbstractOperator) this.clone();
        copy.setOperands(newOperands);
        return copy;
    }

}

