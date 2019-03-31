import java.util.*;

import java.text.ParseException;

import constructions.units.*;

public class DelayGoal {

    private static HashMap<String, Integer> goal = new HashMap<>();

    public static boolean goalAchieved = false;

    /**
     * Sets the goal. The goal needs o be passed as "<goal>".
     *
     * @param args - the goal
     * @return - the goal represented by a HashMap
     */
    public static HashMap<String, Integer> setGoal(String[] args) {

        goal.put(DelayMarine.IDENT, 0);
        goal.put(DelayHellion.IDENT, 0);
        goal.put(DelayMedivac.IDENT, 0);
        goal.put(DelayViking.IDENT, 0);

        if (args.length == 1) {
            parseGoal(args[0]);
        } else {
            System.out.println("Usage: java StarcraftOptimiser <goal>");
            System.exit(1);
        }

        return goal;
    }

    /**
     * Parses the goals and sets the posiible decisions that are relevant for the goal.
     *
     * @param input - the goal
     */
    public static void parseGoal(String input) {

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
            if (! sc.hasNext()) {
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
                if (element.equals(DelayMarine.IDENT) || element.equals(DelayHellion.IDENT)
                        || element.equals(DelayMedivac.IDENT) || element.equals(DelayViking.IDENT)) {

                    /**
                     * If multiple goals are entered for the same unit, the biggest one is considered.
                     */
                    if (num > goal.get(element)) {
                        goal.put(element, num);
                    }

                    DelayDecision.possibleDecisions.put(element, getIndex(element));
                }

                else {
                    handleSyntaxError(element);
                }
            }
        }
        sc.close();
    }

    /**
     * Returns the index of the unit.
     *
     * @param element -
     * @return
     */
    private static int getIndex(String element) {

        switch (element) {

            case DelayMarine.IDENT: {
                return DelayMarine.INDEX;
            }
            case DelayHellion.IDENT: {
                return DelayHellion.INDEX;
            }
            case DelayMedivac.IDENT: {
                return DelayMedivac.INDEX;
            }
            default: {
                return DelayViking.INDEX;
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
