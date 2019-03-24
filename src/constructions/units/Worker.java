package constructions.units;

import constructions.buildings.*;

public class Worker extends Unit {

    public static final String IDENT = "worker";

    public static int precedence = 0;

    public static double mineralCost = 50;
    public static double gasCost = 0;
    public static int buildTime = 17;
    public static String dependentOn = "";
    public static String builtFrom = CommandCenter.IDENT;

    public static void setPrecedence(int value) {
        precedence = value;
        CommandCenter.setPrecedence(value);
    }
}
