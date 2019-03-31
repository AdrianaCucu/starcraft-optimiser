package constructions.buildings;

public class DelayFactory extends DelayBuilding {

    public static final String IDENT = "factory";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;
    public static String dependentOn = DelayBarracks.IDENT;
}
