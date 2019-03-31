package constructions.units;

import constructions.buildings.*;

public class IntermediateThor extends IntermediateUnit{

    public static final int supplyNeeded = 6;

    public static final String IDENT = "thor";
    public static final int INDEX = 5;

    public static double mineralCost = 300;
    public static double gasCost = 200;
    public static int buildTime = 60;

    /**
     * We assume Tech Labs are attached.
     */
    public static String dependentOn = IntermediateArmory.IDENT;
    public static String builtFrom = IntermediateFactory.IDENT;
}