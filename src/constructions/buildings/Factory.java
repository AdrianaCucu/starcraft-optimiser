package constructions.buildings;

public class Factory extends Building {

    public static final String IDENT = "factory";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;
    public static String dependentOn = Barracks.IDENT;
}
