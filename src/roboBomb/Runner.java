package roboBomb;

import roboBomb.Environment.Action;
import roboBomb.Environment.Result;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Runner implements Iterable<Player>, Iterator<Player> {
    private final World world;
    private int iterations = 0;
    private int maxIterations;

    /**
     * The runner constructor.
     *
     * @param world The world instance.
     */
    public Runner(World world) {
        this.world = world;
        this.maxIterations = world.getMaxSteps();
    }

    /**
     * Returns the iterator that can be user in a loop.
     *
     * @return Itself
     */
    public Iterator<Player> iterator() {
        return this;
    }

    /**
     * Check if the game has ended.
     *
     * @return
     */
    public boolean hasNext() {
        Player player = world.getPlayer();
        return iterations < maxIterations && world.getResult() != Result.WIN &&
                player.isAlive() && player.getLastAction() != Action.EXIT;
    }

    /**
     * Get player instance to calculate the next iteration.
     *
     * @return The current player instance
     */
    public Player next() {
        if (!hasNext());
        iterations++;
        return world.getPlayer();
    }

}
