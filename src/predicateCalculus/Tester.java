package predicateCalculus;

public class Tester {
    public static void main(String[] args) {
        try {
            // Set up the knowledge base.
            Constant parent = new Constant("parent"),
                    bill = new Constant("Bill"),
                    audrey = new Constant("Audrey"),
                    maria = new Constant("Maria"),
                    tony = new Constant("Tony"),
                    charles = new Constant("Charles"),
                    ancestor = new Constant("ancestor");
            Variable X = new Variable("X"),
                    Y = new Variable("Y"),
                    Z = new Variable("Z");

            RuleSet rules = new RuleSet(
                    new Rule(new SimpleSentence(parent, bill, audrey)),
                    new Rule(new SimpleSentence(parent, maria, bill)),
                    new Rule(new SimpleSentence(parent, tony, maria)),
                    new Rule(new SimpleSentence(parent, charles, tony)),
                    new Rule(new SimpleSentence(ancestor, X, Y),
                            new And(
                                    new SimpleSentence(parent, X, Y))),
                    new Rule(new SimpleSentence(ancestor, X, Y),
                            new And(
                                    new SimpleSentence(parent, X, Z),
                                    new SimpleSentence(ancestor, Z, Y))));

            // Define goal and root of the search space.
            SimpleSentence goal = new SimpleSentence(ancestor, charles, Y);
            AbstractSolutionNode root = goal.getSolver(rules, new SubstitutionSet());
            SubstitutionSet solution;

            // Print out results.
            System.out.println("Goal = " + goal);
            System.out.println("Solutions:");

            while ((solution = root.nextSolution()) != null) {
                System.out.println(" " + goal.replaceVariables(solution));
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException:" + e);
        }
    }
}
