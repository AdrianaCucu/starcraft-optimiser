import java.util.*;

import java.text.ParseException;

import constructions.units.*;

public class Goal {

    private static HashMap<String, Integer> goal = new HashMap<>();

    public static boolean goalAchieved = false;

    /**
     * Sets the goal. The goal needs o be passed as "<goal>".
     * 
     * @param args - the goal
     * @return - the goal represented by a HashMap
     */
    public static HashMap<String, Integer> setGoal(String[] args) {

        goal.put(Marine.IDENT, 0);
        goal.put(Hellion.IDENT, 0);
        goal.put(Medivac.IDENT, 0);
        goal.put(Viking.IDENT, 0);

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

            String element = sc.next().toLowerCase();
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

            element = sc.next().toLowerCase();

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
                if (element.equals(Marine.IDENT) || element.equals(Hellion.IDENT) 
                    || element.equals(Medivac.IDENT) || element.equals(Viking.IDENT)) {

                    /**
                     * If multiple goals are entered for the same unit, the biggest one is considered.
                     */
                    if (num > goal.get(element)) {
                        goal.put(element, num);
                    }

                    Decision.possibleDecisions.put(element, getIndex(element));
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

            case Marine.IDENT: {
                return Marine.INDEX;
            }
            case Hellion.IDENT: {
                return Hellion.INDEX;
            }
            case Medivac.IDENT: {
                return Medivac.INDEX;
            }
            default: {
                return Viking.INDEX;
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
