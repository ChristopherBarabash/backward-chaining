package predicateCalculus;

public interface PCExpression {
    public PCExpression standardizeVariablesApart(java.util.Hashtable<Variable, Variable> newVars) throws CloneNotSupportedException;

    public PCExpression replaceVariables(SubstitutionSet s) throws CloneNotSupportedException;
}
