package constructions.buildings;

public class SupplyDepot extends Building {

    public static int score = 0;

    public static final String IDENT = "supply depot";

    public static double mineralCost = 100;
    public static double gasCost = 0;
    public static int buildTime = 30;
    public static String dependentOn = "";

    public static void setScore(int number) {
        score = number;
    }
}
