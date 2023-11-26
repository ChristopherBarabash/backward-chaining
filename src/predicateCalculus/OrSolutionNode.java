package predicateCalculus;

public class OrSolutionNode extends AbstractSolutionNode {
    private AbstractSolutionNode headSolutionNode = null;
    private AbstractSolutionNode tailSolutionNode = null;
    private AbstractOperator operatorTail = null;

    public OrSolutionNode(Or goal,
                          RuleSet rules,
                          SubstitutionSet parentSolution) throws CloneNotSupportedException {
        super(goal, rules, parentSolution);
        headSolutionNode = goal.getFirstOperand().getSolver(rules, parentSolution);
        operatorTail = goal.getOperatorTail();
    }

    protected AbstractSolutionNode getHeadSolutionNode() {
        return headSolutionNode;
    }

    protected AbstractSolutionNode getTailSolutionNode() {
        return tailSolutionNode;
    }

    public SubstitutionSet nextSolution() throws CloneNotSupportedException {
        SubstitutionSet solution;

        // Check if there is a solution in the tailSolutionNode
        if (tailSolutionNode != null) {
            solution = tailSolutionNode.nextSolution();
            if (solution != null) {
                return solution;
            }
        }

        // Iterate through the headSolutionNode
        while ((solution = headSolutionNode.nextSolution()) != null) {
            // Check if operatorTail is empty
            if (operatorTail.isEmpty()) {
                return solution;
            } else {
                // Get the solver from operatorTail
                tailSolutionNode = operatorTail.getSolver(getRuleSet(), solution);

                // Get the next solution from tailSolutionNode
                SubstitutionSet tailSolution = tailSolutionNode.nextSolution();

                // Check if there is a solution
                if (tailSolution != null) {
                    return tailSolution;
                }
            }
        }

        // No more solutions found
        return null;
    }
}
