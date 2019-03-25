package constructions.buildings;

public class Refinery extends Building {

    public static int score = 0;

    public static final String IDENT = "refinery";

    public static double mineralCost = 75;
    public static double gasCost = 0;
    public static int buildTime = 30;
    public static String dependentOn = "";

    public static void setScore(int number) {
        score = number;
    }
}
