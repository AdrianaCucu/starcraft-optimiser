package constructions.units;

import constructions.buildings.*;

public class IntermediateMarauder extends IntermediateUnit{

    public static final int supplyNeeded = 2;

    public static final String IDENT = "marauder";
    public static final int INDEX = 6;

    public static double mineralCost = 100;
    public static double gasCost = 25;
    public static int buildTime = 30;

    /**
     * We assume Tech Labs are attached.
     */
    public static String dependentOn = "";
    public static String builtFrom = IntermediateBarracks.IDENT;
}