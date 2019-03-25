package constructions.buildings;

public class Factory extends Building {

    public static int score = 0;

    public static final String IDENT = "factory";

    public static double mineralCost = 150;
    public static double gasCost = 100;
    public static int buildTime = 60;
    public static String dependentOn = Barracks.IDENT;

    public static void setScore(int number) {
        score = number;
        Barracks.setScore(number);

        //we need a refinery to be able to collect gas
        Refinery.setScore(number);
    }
}
