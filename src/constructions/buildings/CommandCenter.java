package constructions.buildings;

import constructions.units.*;
import java.util.*;

public class CommandCenter extends Building {

    public static final String IDENT = "command center";

    public static double mineralCost = 400;
    public static double gasCost = 0;
    public static int buildTime = 100;
    public static String dependentOn = "";
    public static ArrayList<String> units = new ArrayList<>(Arrays.asList(Worker.IDENT));

}
