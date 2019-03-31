package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;

public class IntermediateWorker extends IntermediateUnit {

    public static final String IDENT = "worker";

    public static final String FREE = "free";
    public static final String GAS = "gas";
    public static final String MINERALS = "minerals";

    public static final double mineralCost = 50;
    public static final double gasCost = 0;
    public static final int buildTime = 17;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static final String builtFrom = IntermediateCommandCenter.IDENT;
}
