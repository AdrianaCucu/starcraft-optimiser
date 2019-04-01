package constructions.units;

import constructions.buildings.*;

public class IntermediateTank extends IntermediateUnit{

    public static final int supplyNeeded = 3;

    public static final String IDENT = "tank";
    public static final int INDEX = 4;

    public static double mineralCost = 150;
    public static double gasCost = 125;
    public static int buildTime = 45;

    /**
     * We assume Tech Labs are attached.
     */
    public static String dependentOn = "";
    public static String builtFrom = IntermediateFactory.IDENT;
}