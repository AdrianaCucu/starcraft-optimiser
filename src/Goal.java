import java.util.*;

import javax.lang.model.element.Element;

import java.text.ParseException;

import constructions.units.*;

public class Goal {

    private static Scanner sc;
    private static String element;
    private static HashMap<String, Integer> goal = new HashMap<>();

    public static boolean goalAchieved = false;

    // goals are given as:
    // "16 Marines, 8 Hellions, 3 Medivacs"
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

    public static void parseGoal(String input) {

        int num = 0;
        String construction;

        sc = new Scanner(input);
        while (sc.hasNext()) {
            element = sc.next();

            //checks that every character is a number
            if (element.matches("^[0-9]+$")) {
               num = Integer.parseInt(element);
            } else {
               handleSyntaxError(element);
            }

            element = sc.next();
            
            // element is only made up of characters with a possible "," at the end
            if (element.matches("^[a-zA-Z]+,?$")) {

                element = element.replaceAll(",", "").toLowerCase();

                if (element.equals(Marine.IDENT) || element.equals(Hellion.IDENT) 
                    || element.equals(Medivac.IDENT) || element.equals(Viking.IDENT)) {

                        // if multiple goals are entered for the same unit, sets the biggest one
                        // doesn't pick it up as an error
                        if (num > goal.get(element)) {
                            goal.put(element, num);
                        }

                        Decision.possibleDecisions.put(element, getIndex(element));
                }

                else if (element.equals(Marine.IDENT + "s") || element.equals(Hellion.IDENT + "s") 
                    || element.equals(Medivac.IDENT + "s") || element.equals(Viking.IDENT + "s")){

                        String singular = element.substring(0, element.length() - 1);

                        // removes the "s" from the end
                        if (num > goal.get(singular)) {
                            goal.put(singular, num);
                        }

                        Decision.possibleDecisions.put(singular, getIndex(singular));
                }

                else {
                    handleSyntaxError(element);
                }
            }
        }
       //System.out.println(goal.entrySet());
    }

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

    private static void handleSyntaxError(String element) {
        System.out.println("Invalid goal entered. Error found near '" + element + "'.");
        System.exit(1);
    }
}
