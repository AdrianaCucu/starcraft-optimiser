package constructions.units;

import constructions.buildings.*;

public class IntermediateBanshee extends IntermediateUnit{

    public static final int supplyNeeded = 3;

    public static final String IDENT = "banshee";
    public static final int INDEX = 7;

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;

    /**
     * We assume Tech Labs are attached.
     */
    public static String dependentOn = "";
    public static String builtFrom = IntermediateStarport.IDENT;
}