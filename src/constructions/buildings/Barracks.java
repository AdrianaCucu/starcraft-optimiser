package constructions.buildings;

public class Barracks extends Building {

    public static final String IDENT = "barracks";

    public static double mineralCost = 150;
    public static double gasCost = 0;
    public static int buildTime = 65;
    public static String dependentOn = SupplyDepot.IDENT;
}