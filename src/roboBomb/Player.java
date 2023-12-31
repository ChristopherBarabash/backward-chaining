package roboBomb;

import java.util.ArrayList;
import roboBomb.Environment.*;

public class Player extends Object{
    public enum Direction {
        N, E, S, W
    }

    private final World world;
    private int x, y;
    private int time = 0;

    private Tile tile;

    private ArrayList<Environment.Perception> perceptions = new ArrayList<Environment.Perception>();
    private ArrayList<Environment.Action> actions = new ArrayList<Environment.Action>();
    private Direction direction = Direction.E;
    private boolean alive = true;
    private boolean bomb = false;

    public Player(World world) {
        this.world = world;
    }

    /**
     * Get the horizontal position of the player at the board.
     * @return The X position
     */
    public int getX() {
        return tile.getX();
    }

    /**
     * Get the vertical position of the player at the board.
     * @return The Y position
     */
    public int getY() {
        return tile.getY();
    }

    /**
     * Resets the player state.
     */
    protected void reset() {
        bomb = false;
        direction = Direction.E;
        actions.clear();
    }

    /**
     * Returns the current tile instance.
     * @return The roboBomb.Tile instance
     */
    protected Tile getTile() {
        return tile;
    }

    /**
     * Set the current tile of the agent, un-setting the last one and recalculating all perceptions
     * sensed from the new tile.
     */
    protected void setTile(int index) {
        // Remove the Hunter from the
        if (tile != null) {
            tile.remove(Element.ROBOT);
        }
        tile = world.getPosition(index);
        tile.setItem(Environment.Element.ROBOT);
        // 2D coordinates
        x = tile.getX();
        y = tile.getY();
        // Check if player is still alive
        //jest ściana i robot ma bombę
        alive = !(tile.contains(Element.WALL) || hasBomb());
    }

    /**
     * Returns the current direction of the agent.
     * @return The direction
     */
    public Direction getDirection() { return direction; }

    /**
     * Interacts with the world executing an action.
     * @param action The action to take
     */
    protected void setAction(Action action) {
        actions.add(action);
        // Execute the action
        switch (action) {
            case GO_FORWARD:
                int[] neighbors = tile.getNeighbors();
                switch (direction) {
                    case N:
                        if (neighbors[0] > -1) setTile(neighbors[0]);
                        break;
                    case E:
                        if (neighbors[1] > -1) setTile(neighbors[1]);
                        break;
                    case S:
                        if (neighbors[2] > -1) setTile(neighbors[2]);
                        break;
                    case W:

                        if (neighbors[3] > -1) setTile(neighbors[3]);
                        break;
                }
                break;
            case TURN_LEFT:
                // Mover counter clockwise
                switch (direction) {
                    case N: direction = Direction.W; break;
                    case W: direction = Direction.S; break;
                    case S: direction = Direction.E; break;
                    case E: direction = Direction.N; break;
                }
                break;
            case TURN_RIGHT:
                // Mover clockwise
                switch (direction) {
                    case N: direction = Direction.E; break;
                    case E: direction = Direction.S; break;
                    case S: direction = Direction.W; break;
                    case W: direction = Direction.N; break;
                }
                break;
            case GRAB:
                // If tile has gold store and remove from the tile
                if (tile.contains(Element.BOMB)) {
                    tile.remove(Element.BOMB);
                    bomb = true;
                }
                return;
        }
        // Reprocess all events
        setPerceptions();
    }

    /**
     * Returns the player actions so far.
     * @return The list of actions
     */
    public ArrayList<Action> getActions() {
        return actions;
    }

    /**
     * Returns the last player action or null if none taken.
     * @return The last action
     */
    public Action getLastAction() {
        if (actions.size() == 0) return null;
        return actions.get(actions.size() - 1);
    }

    /**
     * Returns the player score until this point.
     * @return The current score
     */
    public int getScore() {
        return Environment.getScore(this);
    }

    /**
     * Get the list of perceptions sensed from the current tile.
     * @return The list of perceptions
     */
    protected ArrayList<Perception> getPerceptions() {
        return perceptions;
    }

    /**
     * Sets the list of perceptions sensed from the current tile.
     */
    protected void setPerceptions() {
        perceptions.clear();
        // Senses in the current tile
        if (tile.contains(Element.BOMB)) {
            perceptions.add(Perception.BOMB_TILE);
        }
        // Get the neighbors and find the senses
        int[] neighbors = tile.getNeighbors();
        for (int i = 0; i < neighbors.length; i++) {
            // Sense bumps
            if (neighbors[i] == -1) {
                if (    (i == 0 && direction == Direction.N) ||
                        (i == 1 && direction == Direction.E) ||
                        (i == 2 && direction == Direction.S) ||
                        (i == 3 && direction == Direction.W)) {
                    perceptions.add(Perception.BUMP);
                }
            } else {
                Tile neighbor = world.getPosition(neighbors[i]);
                // Sense a breeze when near a pit
                if (neighbor.contains(Element.WALL)) {
                    perceptions.add(Perception.WALL_BEEP);
                }
                // Sense a stench when near a Wumpus
                if (neighbor.contains(Element.BOMB)) {
                    perceptions.add(Perception.BOMB_BEEP);
                }
            }
        }
    }
    /**
     * Sets the list of perceptions sensed from the current tile.
     * @param value The perception to add to the list
     */
    protected void setPerceptions(Perception value) {
        setPerceptions();
        perceptions.add(value);
    }

    /**
     * Returns weather if the player is alive or not
     * @return <tt>true</tt> if player's alive.
     */
    public boolean isAlive() { return alive; }

    /**
     * Returns weather the player is dead or not.
     * @return <tt>true</tt> if player's dead.
     */
    public boolean isDead() { return !alive; }

     /**
      * Returns if player have picked the gold.
      * @return <tt>true</tt> if has the gold.
      */
    public boolean hasBomb() { return bomb; }

    /**
     * Returns if agent feels or not a bump.
     * @return If has a bump perception
     */
    public boolean hasBump() {
        return perceptions.contains(Perception.BUMP);
    }

    /**
     * Returns if agent feels or not a breeze.
     * @return <tt>true</tt> if player's feels a breeze
     */
    public boolean hasBreeze() {
        return perceptions.contains(Perception.WALL_BEEP);
    }

    /**
     * Returns if agent feels or not a steche.
     * @return <tt>true</tt> if player's feels a stench
     */
    public boolean hasStench() {
        return perceptions.contains(Perception.BOMB_BEEP);
    }

    /**
     * Returns if agent sees or not a glitter.
     * @return <tt>true</tt> if player's sees the glitter.
     */
    public boolean hasGlitter() {
        return perceptions.contains(Perception.BOMB_TILE);
    }

    /**
     * Returns the player current statuses.
     * @return The debug string
     */
    public String log() {
        StringBuilder output = new StringBuilder();
        // Position and direction
        output.append("Position: ").append("(").append(x).append(",").append(y).append(",")
                .append(direction).append(")").append("\n");
        // Score
        output.append("Score: ").append(getScore()).append("\n");
        // Perceptions
        output.append("Perceptions: ").append(perceptions.toString());

        return output.toString();
    }

}
