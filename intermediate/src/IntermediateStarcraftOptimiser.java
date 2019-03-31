public class IntermediateStarcraftOptimiser {
    public static void main(String[] args) {

        /**
         * Sets the goal from the command-line arguments;
         * The goal needs to be passed in as "<goal>"
         */
        IntermediateGameState.goal = IntermediateGoal.setIntermediateGoal(args);

        IntermediateGameState.initialiseGame();

        /**
         * Makes decisions until the goal is met.
         */
        while (!IntermediateGoal.goalAchieved) {
            IntermediateDecision.makeIntermediateDecision();
        }

        /**
         * Prints the decisions using a lambda expression.
         * 
         * http://www.codenuclear.com/print-arraylist-using-lambda-expression-in-java-8/
         */
        IntermediateDecision.decisionsMade.forEach(System.out::println);


        // following for loops are for testing
        // that the necessary constructions are built
        System.out.println();
        System.out.println("IntermediateUnits:");
        IntermediateGameState.units.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("IntermediateWorkers:");
        IntermediateGameState.workers.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Free buildings:");
        IntermediateGameState.freeIntermediateBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Busy buildings:");
        IntermediateGameState.busyIntermediateBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Total time: " + IntermediateGameState.time);
        System.out.println("Total gas: " + IntermediateGameState.gas);
        System.out.println("Total minerals: " + IntermediateGameState.minerals);
    }
}
