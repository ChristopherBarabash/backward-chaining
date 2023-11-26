package predicateCalculus;

import java.util.ArrayList;

public class And extends AbstractOperator {
    public And(Goal... operands) {
        super(operands);
    }

    public And(ArrayList<Goal> operands) {
        super(operands);
    }

    public String toString() {
        String result = "(AND ";
        for (int i = 0; i < operandCount(); i++)
            result = result +
                    getOperand(i).toString();
        return result;
    }

    @Override
    public AbstractSolutionNode getSolver(RuleSet rules, SubstitutionSet parentSolution) throws CloneNotSupportedException {
        return new AndSolutionNode(this, rules, parentSolution);
    }

}
