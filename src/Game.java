import constructions.*;
import base.*;

import java.util.HashMap;
import java.util.ArrayList;

public class Game {
    private static boolean goalReached;
    private static int mineralsRequired;

    public static void setGoalReached(boolean goalReached) {
        Game.goalReached = goalReached;
    }

    public static void optimise() {

        while (!goalReached) {
            int count = 0;

            HashMap<String, ArrayList<Construction>> completedConstructions = GameState.getCompletedConstructions();
            Base base = GameState.getBase();

            for (Goal.)


            for (Construction worker : completedConstructions.get(Worker.IDENT)) {
                if (((Worker) worker).getAssignedBaseUnit() == null) {

                }
            }


            if (count == 200) {
                break;
            }
        }
    }

    public static void checkBaseUnits() {

    }
}