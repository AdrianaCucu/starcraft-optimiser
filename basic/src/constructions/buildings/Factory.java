package constructions.buildings;

public class Factory extends Building {
    public static final String IDENT = "factory";
    public static final double MINERAL_COST = 150;
    public static final double GAS_COST = 100;
    public static final int BUILD_TIME = 60;
    public static final String DEPENDENT_ON = Barracks.IDENT;
}
