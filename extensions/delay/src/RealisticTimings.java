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

        System.out.println();
        System.out.println("Total time: " + DelayDecision.formatTime(DelayGameState.time));
    }
}