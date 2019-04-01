package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;

public class IntermediateViking extends IntermediateUnit {

    public static final int supplyNeeded = 2;

    public static final String IDENT = "viking";
    public static final int INDEX = 3;

    public static double mineralCost = 150;
    public static double gasCost = 75;
    public static int buildTime = 42;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = IntermediateStarport.IDENT;
}
