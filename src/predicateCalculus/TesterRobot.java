package predicateCalculus;

public class TesterRobot {
    public static void main(String[] args) {
        try {
            // Set up the knowledge base.
            Constant jest = new Constant("jest"),
                    robot = new Constant("Robot"),
                    bomb = new Constant("Bomb"),
                    wall = new Constant("Wall"),
                    stink = new Constant("stink"),
                    potentialBomb = new Constant("PotentialBomb"),
                    S11 = new Constant("S11"),
                    S12 = new Constant("S12"),
                    S21 = new Constant("S21"),
                    S22 = new Constant("S22"),
                    neighbour = new Constant("neighbour");


            Variable X = new Variable("X"),
                    Y = new Variable("Y"),
                    Z = new Variable("Z");

            RuleSet rules = new RuleSet(
                    new Rule(new SimpleSentence(jest, robot, X),
                            new SimpleSentence(jest, bomb, X)),
                    new Rule(new SimpleSentence(jest, bomb, S22)),
                    new Rule(new SimpleSentence(jest, robot, S11)),
                    new Rule(new SimpleSentence(neighbour, S11, S12)),
                    new Rule(new SimpleSentence(neighbour, S11, S21)),
                    new Rule(new SimpleSentence(neighbour, S22, S21)),
                    new Rule(new SimpleSentence(neighbour, S22, S12)),

                    new Rule(new SimpleSentence(neighbour, X, Y),
                            new SimpleSentence(neighbour, Y, X)));



            // Define goal and root of the search space.
            SimpleSentence goal = new SimpleSentence(neighbour, S21, Y);
            AbstractSolutionNode root = goal.getSolver(rules, new SubstitutionSet());
            SubstitutionSet solution;

            // Print out results.
            System.out.println("Goal = " + goal);
            System.out.println("Solutions:");

            while ((solution = root.nextSolution()) != null) {
                System.out.println(" " + goal.replaceVariables(solution));
            }
            // Print out the rule set.
            System.out.println("Rule Set:");
            for (int i = 0; i < rules.getRuleCount(); i++) {
                System.out.println("Rule " + (i + 1) + ": " + rules.getRule(i));
            }
        } catch (CloneNotSupportedException e) {
            System.out.println("CloneNotSupportedException:" + e);
        }
    }
}
