package roboBomb;

public interface Agent {
    /**
     * Executes every play to determine the next action.
     *
     * @param player The player instance
     * @return The action to execute
     */
    Environment.Action getAction(Player player);

    /**
     * Executes before takes the action.
     *
     * @param player The player instance
     */
    void beforeAction(Player player);

    /**
     * Executes after taking the action.
     *
     * @param player The player instance
     */
    void afterAction(Player player);
}
