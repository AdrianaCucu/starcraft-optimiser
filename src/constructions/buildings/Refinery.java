package constructions.buildings;

import java.util.*;

public class Refinery extends Building {

    public static final String IDENT = "refinery";

    public static int precedence = 0;

    public static double mineralCost = 75;
    public static double gasCost = 0;
    public static int buildTime = 30;
    public static String dependentOn = "";
    public static ArrayList<String> units = new ArrayList<>();

    public static void setPrecedence(int value) {
        precedence = value;
    }
}
