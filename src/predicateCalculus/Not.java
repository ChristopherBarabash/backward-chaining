package predicateCalculus;

import java.util.ArrayList;

public class Not extends AbstractOperator {
    public Not(Goal... operands) {
        super(operands);
    }

    public Not(ArrayList<Goal> operands) {
        super(operands);
    }

    public String toString() {
        String result = new String("(NOT ");
        for (int i = 0; i < operandCount(); i++)
            result = result +
                    getOperand(i).toString();
        return result;
    }

    @Override
    public AbstractSolutionNode getSolver(RuleSet rules, SubstitutionSet parentSolution) throws CloneNotSupportedException {
        return new NotSolutionNode(this, rules, parentSolution);
    }
}
