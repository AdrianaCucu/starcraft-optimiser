package constructions.buildings;

import constructions.units.*;
import java.util.*;

public class Factory extends Building {

    public static final String IDENT = "factory";

    public static int precedence = 0;

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;
    public static String dependentOn = Barracks.IDENT;
    public static ArrayList<String> units = new ArrayList<>(Arrays.asList(Hellion.IDENT));

}
