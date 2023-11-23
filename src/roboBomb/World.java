package roboBomb;


import roboBomb.Environment.Action;
import roboBomb.Environment.Element;
import roboBomb.Environment.Result;

import java.util.HashMap;
import java.util.Random;


public class World {

    private static final int DEFAULT_MAX_STEPS = 200;


    private final int width;
    private final int height;
    private final int startPosition;

    private int maxSteps = DEFAULT_MAX_STEPS;

    private HashMap<Integer, Element> items = new HashMap<Integer, Element>();


    private String agentName;
    private final Player player;
    private final Tile[] tiles;


    /**
     * Creates a new world with given dimensions.
     *
     * @param width  The horizontal constraint of the board
     * @param height The vertical constraint of the board
     * @throws InterruptedException
     * @throws InternalError
     */
    public World(int width, int height) throws InterruptedException,
            InternalError {
        if (width == 1 && height == 1) {
            throw new InternalError("The world size must be greater than 1x1.");
        }
        this.width = width;
        this.height = height;
        // Generate the board matrix (WxH)
        tiles = new Tile[width * height];
        for (int i = 0; i < width * height; i++) {
            tiles[i] = new Tile(i, width, height);
        }
        // Saves the start position to check the objective
        startPosition = getIndex(0, height - 1);
        // Set the player
        player = new Player(this);

        //PIERWSZY ŚWIAT
        setItem(Element.BOMB, 2,2);
        setItem(Element.WALL, 1,1);
        setItem(Element.WALL, 2,1);
        setItem(Element.WALL, 3,1);

    }

    /**
     * Execute an agent that plays the game automatically.
     *
     * @param agent The agent instance
     * @throws InterruptedException
     */
    public void execute(Agent agent) throws InterruptedException {
        agentName = agent.getClass().getName();

        for (Player player : run()) {
            agent.beforeAction(player);
            Action actions = agent.getAction(player);
            player.setAction(actions);
            agent.afterAction(player);
        }
    }


    /**
     * Returns the current agent class name.
     * @return The agent name
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * Returns the maximum steps the player can make before ending the game.
     * @return The max steps allowed
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Sets the maximus steps to finish the game.
     * @param value
     */
    public void setMaxSteps(int value) {
        maxSteps = value;
    }


    /**
     * Sets a pit at given coordinate.
     * @param x The horizontal coordinate
     * @param y The vertical coordinate
     */
    public void setWall(int x, int y) {
        setItem(Element.WALL, x, y);
    }


    /**
     * Sets a Wumpus at given coordinate.
     * @param x The horizontal position
     * @param y The vertical position
     */
    public void setBomb(int x, int y) {
        setItem(Element.BOMB, x, y);
    }


    /**
     * Sets the element at given coordinates and saves it for later retrieval.
     *
     * @param element The element to plate
     * @param x       The horizontal position
     * @param y       The vertical position
     */
    private void setItem(Element element, int x, int y) {
        Tile tile = getPosition(x, y);
        if (tile.isEmpty()) {
            tile.setItem(element);
        } else {
            throw new InternalError("Tile is not empty!");
        }
        // Saves the items position for later retrieval
        items.put(tile.getIndex(), element);
    }


    /**
     * Resets the board.
     *
     * @throws InterruptedException
     */
    public void reset() throws InterruptedException {
        // Reset all blocks
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].clear();
        }
        // Reset the player agent
        player.setTile(startPosition);
        player.reset();
        // Set the dangers

        for (int index : items.keySet()) {
            Tile tile = getPosition(index);
            tile.setItem(items.get(index));
        }
    }

    /**
     * Starts playing until game reachs its end.
     *
     * @return The plays iteration
     * @throws InterruptedException
     */
    private Runner run() throws InterruptedException {
        reset();
        return new Runner(this);
    }

    /**
     * Returns the index from a given 2D position.
     *
     * @param x The horizontal position
     * @param y The vertical position
     * @return The index
     */
    public int getIndex(int x, int y) {
        return (x + y * width);
    }

    /**
     * Returns the board block at given linear position.
     *
     * @param index The block position
     * @return The block instance
     */
    public Tile getPosition(int index) {
        return tiles[index];
    }

    /**
     * Returns the board block at given 2D position.
     *
     * @param x The horizontal position
     * @param y The vertical position
     * @return The block instance
     */
    public Tile getPosition(int x, int y) {
        int i = getIndex(x, y);
        return tiles[i];
    }

    /**
     * Returns the current player.
     *
     * @return The player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the board width.
     *
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the board height.
     *
     * @return The height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Returns if the player have win or loose the game.
     *
     * @return The outcome of the game
     */
    public Result getResult() {
        if (player.isAlive() && player.hasBomb() && player.getTile().getIndex() == startPosition) {
            return Result.WIN;
        }
        return Result.DEFEAT;
    }


    /**
     * Renders the score table as a ASCII string.
     *
     * @return The score table
     */
    public String renderScore() {
        String score = String.format("| %-7s | %8d | %5d |%n" + getResult().toString(), player.getScore(), player.getActions().size());
        return score;
    }
}
