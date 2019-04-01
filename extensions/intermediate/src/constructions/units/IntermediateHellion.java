package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;


public class IntermediateHellion extends IntermediateUnit{

    public static final int supplyNeeded = 2;

    public static final String IDENT = "hellion";
    public static final int INDEX = 1;

    public static double mineralCost = 100;
    public static double gasCost = 0;
    public static int buildTime = 30;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = IntermediateFactory.IDENT;
}
