package constructions.buildings;

public class IntermediateArmory extends IntermediateBuilding {

    public static final int supplyProvided = 0;

    public static final String IDENT = "armory";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 65;
    public static String dependentOn = IntermediateSupplyDepot.IDENT;
}
