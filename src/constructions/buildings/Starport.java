package constructions.buildings;

import constructions.units.*;
import java.util.*;

public class Starport extends Building {

    public static final String IDENT = "starport";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 50;
    public static String dependentOn = Factory.IDENT;
    public static ArrayList<String> units = new ArrayList<>(Arrays.asList(Medivac.IDENT, Viking.IDENT));

}
