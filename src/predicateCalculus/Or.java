package predicateCalculus;

import java.util.ArrayList;

public class Or extends AbstractOperator {
    public Or(Goal... operands) {
        super(operands);
    }

    public Or(ArrayList<Goal> operands) {
        super(operands);
    }

    public String toString() {
        String result = new String("(OR ");
        for (int i = 0; i < operandCount(); i++)
            result = result +
                    getOperand(i).toString();
        return result;
    }

    @Override
    public AbstractSolutionNode getSolver(RuleSet rules, SubstitutionSet parentSolution) throws CloneNotSupportedException {
        return new OrSolutionNode(this, rules, parentSolution);
    }
}
