import java.util.*;

import constructions.units.*;

public class IntermediateGoal {

    private static HashMap<String, Integer> goal = new HashMap<>();

    public static boolean goalAchieved = false;

    /**
     * Sets the goal. The goal needs o be passed as "<goal>".
     * 
     * @param args - the goal
     * @return - the goal represented by a HashMap
     */
    public static HashMap<String, Integer> setIntermediateGoal(String[] args) {

        goal.put(IntermediateMarine.IDENT, 0);
        goal.put(IntermediateHellion.IDENT, 0);
        goal.put(IntermediateMedivac.IDENT, 0);
        goal.put(IntermediateViking.IDENT, 0);
        goal.put(IntermediateBanshee.IDENT, 0);
        goal.put(IntermediateMarauder.IDENT, 0);
        goal.put(IntermediateTank.IDENT, 0);
        goal.put(IntermediateThor.IDENT, 0);

        if (args.length == 1) {
            parseIntermediateGoal(args[0]);
        } else {
            System.out.println("Usage: java IntermediateStarcraftOptimiser <goal>");
            System.exit(1);
        }

        return goal;
    }

    /**
     * Parses the goals and sets the posiible decisions that are relevant for the goal.
     * 
     * @param input - the goal
     */
    public static void parseIntermediateGoal(String input) {

        Scanner sc = new Scanner(input);

        while (sc.hasNext()) {

            String element = sc.next();
            int num = 0;

            /**
             * Checks that every character is a digit.
             * If number starts with "0", it does not pick it up as an error.
             */
            if (element.matches("^[0-9]+$")) {
               num = Integer.parseInt(element);
            } else {
               handleSyntaxError(element);
            }

            /**
             * If not enough arguments have been entered, the program is stopped.
             */
            if (!sc.hasNext()) {
                handleSyntaxError(element);
            }

            element = sc.next();

            /**
             * Checks that every character is a letter with a possible "," at the end.
             */
            if (element.matches("^[a-zA-Z]+,?$")) {

                /**
                 * Removes the commas in order to validate the String.
                 */
                element = element.replaceAll(",", "");

                /**
                 * Turns the plural words into singular words in order to validate the String.
                 */
                if (element.endsWith("s")) {
                    element = element.substring(0, element.length() - 1);
                }

                /**
                 * Validates the String and sets the goals.
                 */
                if (element.equals(IntermediateMarine.IDENT) || element.equals(IntermediateHellion.IDENT)
                        || element.equals(IntermediateMedivac.IDENT) || element.equals(IntermediateViking.IDENT)
                        || element.equals(IntermediateBanshee.IDENT) || element.equals(IntermediateMarauder.IDENT)
                        || element.equals(IntermediateTank.IDENT) || element.equals(IntermediateThor.IDENT)) {

                    /**
                     * If multiple goals are entered for the same unit, picks it up as an error.
                     */
                    if (goal.get(element) == 0 && num > 0) {
                        goal.put(element, num);
                    } else {
                        handleSyntaxError(element);
                    }

                    IntermediateDecision.possibleIntermediateDecisions.put(element, getIndex(element));
                    updateSupply(element, num);
                }

                else {
                    handleSyntaxError(element);
                }
            }
        }

        /**
         * Sets the maximum supply that can be used to build workers.
         */
        IntermediateGameState.workersSupply = IntermediateGameState.maxSupply - IntermediateGameState.goalSupply;
        sc.close();
    }

    /**
     * Returns the index of the unit.
     * 
     * @param element - the identifier of the unit
     * @return - the index of the unit
     */
    private static int getIndex(String element) {

        switch (element) {

            case IntermediateMarine.IDENT: {
                return IntermediateMarine.INDEX;
            }
            case IntermediateHellion.IDENT: {
                return IntermediateHellion.INDEX;
            }
            case IntermediateMedivac.IDENT: {
                return IntermediateMedivac.INDEX;
            }
            case IntermediateViking.IDENT: {
                return IntermediateViking.INDEX;
            }
            case IntermediateBanshee.IDENT: {
                return IntermediateBanshee.INDEX;
            }
            case IntermediateMarauder.IDENT: {
                return IntermediateMarauder.INDEX;
            }
            case IntermediateTank.IDENT: {
                return IntermediateTank.INDEX;
            }
            default: {
                return IntermediateThor.INDEX;
            }
        }
    }

    /**
     * Updates the supply needed to meet the goal.
     *
     * @param element - the index of the unit
     * @param num - the number of units needed for the goal
     */
    private static void updateSupply(String element, int num) {

        switch (element) {

            case IntermediateMarine.IDENT: {
                IntermediateGameState.goalSupply += IntermediateMarine.supplyNeeded * num;
                break;
            }
            case IntermediateHellion.IDENT: {
                IntermediateGameState.goalSupply += IntermediateHellion.supplyNeeded * num;
                break;
            }
            case IntermediateMedivac.IDENT: {
                IntermediateGameState.goalSupply += IntermediateMedivac.supplyNeeded * num;
                break;
            }
            case IntermediateViking.IDENT: {
                IntermediateGameState.goalSupply += IntermediateViking.supplyNeeded * num;
                break;
            }
            case IntermediateBanshee.IDENT: {
                IntermediateGameState.goalSupply += IntermediateBanshee.supplyNeeded * num;
                break;
            }
            case IntermediateMarauder.IDENT: {
                IntermediateGameState.goalSupply += IntermediateMarauder.supplyNeeded * num;
                break;
            }
            case IntermediateTank.IDENT: {
                IntermediateGameState.goalSupply += IntermediateTank.supplyNeeded * num;
                break;
            }
            case IntermediateThor.IDENT: {
                IntermediateGameState.goalSupply += IntermediateThor.supplyNeeded * num;
                break;
            }
        }
    }

    /**
     * If the input is not valid, prints an informative error message.
     */
    private static void handleSyntaxError(String element) {
        System.out.println("Invalid goal entered. Error found near '" + element + "'.");
        System.exit(1);
    }
}
