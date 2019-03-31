package constructions.units;

import constructions.buildings.*;

import java.util.ArrayList;


public class DelayMedivac extends DelayUnit{

    public static final String IDENT = "medivac";
    public static final int INDEX = 2;

    public static double mineralCost = 100;
    public static double gasCost = 100;
    public static int buildTime = 42;
    public static ArrayList<String> dependentOn = new ArrayList<>();
    public static String builtFrom = DelayStarport.IDENT;
}
