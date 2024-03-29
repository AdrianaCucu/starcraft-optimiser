package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;

public class Worker extends Unit {

    public static final String IDENT = "worker";
    public static final String FREE = "free";
    public static final String GAS = "gas";
    public static final String MINERALS = "minerals";

    public static final double MINERAL_COST = 50;
    public static final double GAS_COST = 0;
    public static final int BUILD_TIME = 17;
    public static final String BUILT_FROM = CommandCenter.IDENT;
}
