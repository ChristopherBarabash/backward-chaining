package predicateCalculus;

public interface Goal extends PCExpression{
    public AbstractSolutionNode getSolver(
            RuleSet rules,
            SubstitutionSet parentSolution) throws CloneNotSupportedException;
}
