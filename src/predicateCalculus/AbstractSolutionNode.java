package predicateCalculus;

public abstract class AbstractSolutionNode {
    private RuleSet rules;
    private Rule currentRule = null;
    private Goal goal= null;
    private SubstitutionSet parentSolution;
    private int ruleNumber = 0;
    public AbstractSolutionNode(Goal goal,
                                RuleSet rules,
                                SubstitutionSet parentSolution)
    {
        this.rules = rules;
        this.parentSolution = parentSolution;
        this.goal = goal;
    }
    public abstract SubstitutionSet nextSolution()
            throws CloneNotSupportedException;
    protected void reset(SubstitutionSet
                                 newParentSolution)
    {
        parentSolution = newParentSolution;
        ruleNumber = 0;
    }
    public Rule nextRule() throws
            CloneNotSupportedException
    {
        if(hasNextRule())
            currentRule =
                    rules.getRuleStandardizedApart(
                            ruleNumber++);
        else
            currentRule = null;
        return currentRule; }
    protected boolean hasNextRule()
    {
        return ruleNumber < rules.getRuleCount();
    }

    protected SubstitutionSet getParentSolution()
    {
        return parentSolution;
    }
    protected RuleSet getRuleSet()
    {
        return rules;
    }
    public Rule getCurrentRule()
    {
        return currentRule;
    }
    public Goal getGoal()
    {
        return goal;
    }
}
