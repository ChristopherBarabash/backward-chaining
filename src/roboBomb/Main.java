package roboBomb;

import agents.AgentHeuristic;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        try {
            // Create a 4x4 world
            World world = new World(4, 4);
            world.setMaxSteps(100);

            // Print the game title
            System.out.println("Find the bomb!");

            // Start and execute the AI agent
            Agent agent = new AgentHeuristic(world.getWidth(), world.getHeight());
            world.execute(agent);


            System.out.format("Results for %s:%n", world.getAgentName());
            System.out.println(world.renderScore());
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}