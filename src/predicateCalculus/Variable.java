package predicateCalculus;

import java.util.Hashtable;

public class Variable implements Unifiable {
    private String printName = null;
    private static int nextId = 1;
    private int id;

    public Variable() {
        this.id = nextId++;
    }

    public Variable(String printName) {
        this();
        this.printName = printName;
    }

    public Variable(Variable variable) {
        this.id = variable.id;
        this.printName = variable.printName;
    }

    public String toString() {
        if (printName != null)
            return printName + "_" + id;
        return "V" + id;
    }


    public SubstitutionSet unify(Unifiable p,
                                 SubstitutionSet s) {
        if (this == p) return s;
        if (s.isBound(this))
            return s.getBinding(this).unify(p, s);
        SubstitutionSet sNew = new SubstitutionSet(s);
        sNew.add(this, p);
        return sNew;
    }

    public PCExpression replaceVariables(
            SubstitutionSet s) throws CloneNotSupportedException {
        if (s.isBound(this))
            return
                    s.getBinding(this).replaceVariables(s);
        else
            return this;
    }

    public PCExpression standardizeVariablesApart(Hashtable<Variable, Variable> newVars) {
        Variable newVar = newVars.get(this);
        // Check if the expression already has
        // a substitute variable.
        if (newVar == null) // if not create one.
        {
            newVar = new Variable(this);
            newVars.put(this, newVar);
        }
        return newVar;
    }

}
