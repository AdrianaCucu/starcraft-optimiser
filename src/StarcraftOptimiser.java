import java.util.HashSet;
import java.util.Map;

public class StarcraftOptimiser {
    public static void main(String[] args) {

        // give the goal between quotation marks
        GameState.goal = Goal.setGoal(args);

        GameState.initialiseGame();

        while (!Goal.goalAchieved) {
            Decision.makeDecision();
        }

        // prints out the decisions
        for (String decision : Decision.decisionsMade) {
            System.out.println(decision);
        }


        // following for loops are for testing
        // that the necessary constructions are built
        System.out.println();
        System.out.println("Units:");
        for (Map.Entry<String, Integer> entry : GameState.units.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }

        System.out.println();
        System.out.println("Workers:");
        for (Map.Entry<String, Integer> entry : GameState.workers.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }

        System.out.println();
        System.out.println("Free buildings:");
        for (Map.Entry<String, Integer> entry : GameState.freeBuildings.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }


        System.out.println();
        System.out.println("Busy buildings:");
        for (Map.Entry<String, Integer> entry : GameState.busyBuildings.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }

        System.out.println();
        System.out.println("Total time: " + (GameState.time / 60) + " minutes and " + (GameState.time % 60) + " seconds.");
        System.out.println("Total gas: " + GameState.gas);
        System.out.println("Total minerals: " + GameState.minerals);
    }
}
