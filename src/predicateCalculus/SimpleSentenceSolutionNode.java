package predicateCalculus;

public class SimpleSentenceSolutionNode extends
        AbstractSolutionNode
{
    private SimpleSentence goal;
    private AbstractSolutionNode child = null;
    public SimpleSentenceSolutionNode(
            SimpleSentence goal,
            RuleSet rules,
            SubstitutionSet parentSolution)
            throws CloneNotSupportedException
    {
        super(goal, rules, parentSolution);
        this.goal = goal; // Make sure to initialize the goal variable

    }
    public SubstitutionSet nextSolution() throws CloneNotSupportedException {
        SubstitutionSet solution;
        if(child != null)
        {
            solution = child.nextSolution();
            if (solution != null)
                return solution;
        }
        child = null;
        Rule rule;
        while(hasNextRule())
        {
            rule = nextRule();
            SimpleSentence head = rule.getHead();
            solution = goal.unify(head,
                    getParentSolution());
            if(solution != null)
            {
                Goal tail = rule.getBody();
                if(tail == null)
                    return solution;
                child = tail.getSolver
                        (getRuleSet(),solution);
                SubstitutionSet childSolution =
                        child.nextSolution();
                if(childSolution != null)
                    return childSolution;
            }
        }
        return null;
    }
    public AbstractSolutionNode getChild()
    {
        return child;
    }
}
