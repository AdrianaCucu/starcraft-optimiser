package constructions.buildings;

public class DelayStarport extends DelayBuilding {
    
    public static final String IDENT = "starport";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 50;
    public static String dependentOn = DelayFactory.IDENT;
}
