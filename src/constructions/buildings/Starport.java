package constructions.buildings;

public class Starport extends Building {

    public static int score = 0;

    public static final String IDENT = "starport";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 50;
    public static String dependentOn = Factory.IDENT;

    public static void setScore(int number) {
        score = number;
        Factory.setScore(number);
    }
}
