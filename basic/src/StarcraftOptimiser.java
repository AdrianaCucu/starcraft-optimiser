public class StarcraftOptimiser {
    public static void main(String[] args) {

        /**
         * Sets the goal from the command-line arguments;
         * The goal needs to be passed in as "<goal>"
         */
        GameState.goal = Goal.setGoal(args);

        GameState.initialiseGame();

        /**
         * Makes decisions until the goal is met.
         */
        while (!Goal.goalAchieved) {
            Decision.makeDecision();
        }

        /**
         * Prints the decisions using a lambda expression.
         * 
         * http://www.codenuclear.com/print-arraylist-using-lambda-expression-in-java-8/
         */
        Decision.decisionsMade.forEach(System.out::println);

        System.out.println();
        System.out.println("Total time: " + Decision.formatTime(GameState.time));
    }
}
