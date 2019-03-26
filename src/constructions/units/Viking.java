package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;

public class Viking extends Unit {

    public static final String IDENT = "viking";

    public static double mineralCost = 150;
    public static double gasCost = 75;
    public static int buildTime = 42;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = Starport.IDENT;
}
