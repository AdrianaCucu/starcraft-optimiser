public class IntermediateStarcraftOptimiser {
    public static void main(String[] args) {

        /**
         * Sets the goal from the command-line arguments;
         * The goal needs to be passed in as "<goal>"
         */
        IntermediateGameState.goal = IntermediateGoal.setIntermediateGoal(args);

        /**
         * If the supply needed to complete the goal is bigger than 200, terminates.
         */
        if (IntermediateGameState.goalSupply > IntermediateGameState.maxSupply) {
            System.out.println("Maximum supply exceeded. Cannot meet goal.");
            System.exit(1);
        }

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

        System.out.println();
        System.out.println("Total time: " + IntermediateDecision.formatTime(IntermediateGameState.time));
    }
}
