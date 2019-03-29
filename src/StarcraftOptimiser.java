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

        /**
         * Prints the decisions using a lambda expression.
         * 
         * http://www.codenuclear.com/print-arraylist-using-lambda-expression-in-java-8/
         */
        Decision.decisionsMade.forEach(System.out::println);
        /*
        for (String decision : Decision.decisionsMade) {
            System.out.println(decision);
        }
        */


        // following for loops are for testing
        // that the necessary constructions are built
        System.out.println();
        System.out.println("Units:");
        GameState.units.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Workers:");
        GameState.workers.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Free buildings:");
        GameState.freeBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Busy buildings:");
        GameState.busyBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Total time: " + GameState.time);
        System.out.println("Total gas: " + GameState.gas);
        System.out.println("Total minerals: " + GameState.minerals);
    }
}
