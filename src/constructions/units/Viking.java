package constructions.units;

import constructions.buildings.*;

public class Viking extends Unit {

    public static final String IDENT = "viking";

    public static int precedence = 0;

    public static double mineralCost = 150;
    public static double gasCost = 75;
    public static int buildTime = 42;
    public static String dependentOn = "";
    public static String builtFrom = Starport.IDENT;

}
