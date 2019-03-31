public class RealisticTimings {
    public static void main(String[] args) {

        /**
         * Sets the goal from the command-line arguments;
         * The goal needs to be passed in as "<goal>"
         */
        DelayGameState.goal = DelayGoal.setGoal(args);

        DelayGameState.initialiseGame();

        /**
         * Makes decisions until the goal is met.
         */
        while (!DelayGoal.goalAchieved) {
            DelayDecision.makeDecision();
        }

        /**
         * Prints the decisions using a lambda expression.
         *
         * http://www.codenuclear.com/print-arraylist-using-lambda-expression-in-java-8/
         */
        DelayDecision.decisionsMade.forEach(System.out::println);


        // following for loops are for testing
        // that the necessary constructions are built
        System.out.println();
        System.out.println("DelayUnits:");
        DelayGameState.units.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("DelayWorkers:");
        DelayGameState.workers.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Free buildings:");
        DelayGameState.freeDelayBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Busy buildings:");
        DelayGameState.busyDelayBuildings.forEach((key, value) -> System.out.println(key + ": " + value));

        System.out.println();
        System.out.println("Total time: " + DelayGameState.time);
        System.out.println("Total gas: " + DelayGameState.gas);
        System.out.println("Total minerals: " + DelayGameState.minerals);
    }
}