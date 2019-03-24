package constructions.units;

import constructions.buildings.*;


public class Medivac extends Unit{

    public static final String IDENT = "medivac";

    public static int precedence = 0;

    public static double mineralCost = 100;
    public static double gasCost = 100;
    public static int buildTime = 42;
    public static String dependentOn = "";
    public static String builtFrom = Starport.IDENT;

}
