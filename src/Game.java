import constructions.*;
import base.*;

import java.util.*;

public class Game {
    private static boolean goalReached;

    private static HashMap<String, ArrayList<Construction>> currConstruction;
    private static int mineralsRequired;

    public static void setGoalReached(boolean goalReached) {
        Game.goalReached = goalReached;
    }


    /**
     * Calculates resources needed
     */
    public static void calcResources() {

    }

    /**
     * Optimises game.
     */
    public static void optimise() {
        while (!goalReached) {
            int count = 0;



            currConstruction = GameState.getCompletedConstructions();
            Base base = GameState.getBase();






            //temporary to avoid program from crashing
            if (count == 200) {
                break;
            }

            goalReached = isGoalReached();
            count++;
        }
        System.out.println("Goal Reached!");
    }

    /**
     * Constantly checks if all workers are optimally assigned.
     */
    public static void optimiseWorkers() {

        for (Construction worker : currConstruction.get(Worker.IDENT)) {
            if (((Worker) worker).getAssignedBaseUnit() == null) {
                GameState.incrementTime();
                ((Worker) worker).assignBaseUnit(base.checkBaseUnit());
            }
        }
    }




    /**
     * checks if goal is met. (Checks instances of constructions made and count)
     * @return - if goal is met
     */
    public static boolean isGoalReached() {
        currConstruction = GameState.getCompletedConstructions();

        for (Map.Entry<String, ArrayList<Construction>> entry : Goal.getGoal1().entrySet()) {
            if (currConstruction.containsKey(entry.getKey())) {
                if (currConstruction.get(entry.getKey()).size() != entry.getValue().size()) {
                    return false;
                }
            } else {
                return false;
            }

        }
        return true;
    }


}