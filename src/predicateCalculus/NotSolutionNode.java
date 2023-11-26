package predicateCalculus;

public class NotSolutionNode extends AbstractSolutionNode {
    private AbstractSolutionNode operandSolutionNode = null;

    public NotSolutionNode(Not goal,
                           RuleSet rules,
                           SubstitutionSet parentSolution) throws CloneNotSupportedException {
        super(goal, rules, parentSolution);
        operandSolutionNode = goal.getFirstOperand().getSolver(rules, parentSolution);
    }

    public SubstitutionSet nextSolution() throws CloneNotSupportedException {
        SubstitutionSet operandSolution = operandSolutionNode.nextSolution();

        if (operandSolution == null) {
            // If there is no solution for the operand, then the negation is satisfied
            return new SubstitutionSet(); // or return an appropriate SubstitutionSet
        } else {
            // If there is a solution for the operand, negate the solution
            return null;
        }
    }
}
