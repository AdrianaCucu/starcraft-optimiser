package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;


public class IntermediateMarine extends IntermediateUnit {

    public static final int supplyNeeded = 1;

    public static final String IDENT = "marine";
    public static final int INDEX = 0;

    public static double mineralCost = 50;
    public static double gasCost = 0;
    public static int buildTime = 25;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = IntermediateBarracks.IDENT;
}
