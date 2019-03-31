package constructions.buildings;

public class IntermediateFactory extends IntermediateBuilding {

    public static final String IDENT = "factory";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;
    public static String dependentOn = IntermediateBarracks.IDENT;
}
