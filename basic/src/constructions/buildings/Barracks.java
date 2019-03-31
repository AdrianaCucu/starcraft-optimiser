package constructions.buildings;

public class Barracks extends Building {
    public static final String IDENT = "barracks";
    public static final double MINERAL_COST = 150;
    public static final double GAS_COST = 0;
    public static final int BUILD_TIME = 65;
    public static final String DEPENDENT_ON = SupplyDepot.IDENT;
}
