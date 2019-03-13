import base.*;
import constructions.*;

public class Game {
    private static boolean goalReached;

    public static void setGoalReached(boolean goalReached) {
        Game.goalReached = goalReached;
    }

    public static void optimise() {
        while (!goalReached) {
            int count =0;

            for (Construction worker : GameState.getCompletedConstructions().get(Worker.IDENT)) {

            }


            if (count == 200) {
                break;
            }
        }
    }

    public static void assignWorkers() {

    }
}