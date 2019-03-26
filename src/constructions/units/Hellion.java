package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;


public class Hellion extends Unit{

    public static final String IDENT = "hellion";

    public static double mineralCost = 50;
    public static double gasCost = 0;
    public static int buildTime = 25;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = Factory.IDENT;
}
