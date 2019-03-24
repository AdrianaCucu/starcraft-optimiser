package constructions.buildings;

import java.util.*;

public class SupplyDepot extends Building {

    public static final String IDENT = "supply depot";

    public static int precedence = 0;

    public static double mineralCost = 100;
    public static double gasCost = 30;
    public static int buildTime = 30;
    public static String dependentOn = "";
    public static ArrayList<String> units = new ArrayList<>();

    public static void setPrecedence(int value) {
        precedence = value;
    }
}
