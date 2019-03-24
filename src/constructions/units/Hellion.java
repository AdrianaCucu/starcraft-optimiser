package constructions.units;

import constructions.buildings.*;


public class Hellion extends Unit{

    public static final String IDENT = "hellion";

    public static int precedence = 0;

    public static double mineralCost = 50;
    public static double gasCost = 0;
    public static int buildTime = 25;
    public static String dependentOn = "";
    public static String builtFrom = Factory.IDENT;

}
