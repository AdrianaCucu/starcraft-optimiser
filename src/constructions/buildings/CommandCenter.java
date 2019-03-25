package constructions.buildings;

public class CommandCenter extends Building {

    public static int score = 0;

    public static final String IDENT = "command center";

    public static double mineralCost = 400;
    public static double gasCost = 0;
    public static int buildTime = 100;
    public static String dependentOn = "";

    public static void setScore(int number) {
        score = number;
    }
}
