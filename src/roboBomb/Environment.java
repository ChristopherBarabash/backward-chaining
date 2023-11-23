package roboBomb;

public class Environment {

    public enum Element {
        BOMB, WALL, ROBOT, EXIT
    }

    public enum Perception {
        BOMB_BEEP, WALL_BEEP, BOMB_TILE, BUMP
    }

    public enum Action {
        GO_FORWARD, TURN_LEFT, TURN_RIGHT, GRAB, EXIT, NOOP,
    }

    public enum Result {
        WIN, DEFEAT
    }

    protected static int getScore(Player player) {
        int sum = 0;
        // Score if have deceased
        if (player.isDead()) sum += -1000;
        // Score if have picked the gold
        if (player.hasBomb()) sum += +1000;
        // Calculate the score for each action
        for (Action action : player.getActions()) {
            switch (action) {
                case GO_FORWARD:
                case TURN_LEFT:
                case TURN_RIGHT:
                case GRAB:
                    sum += -1;
                    break;
//                case BUMP:
//                    sum += -10;
//                    break;
            }
        }
        return sum;
    }

}
