package resources;

public class Resource {

    /**
     * Stores the resource gathered since the beginning of the game (so static).
     */
    public static double total = 0;

    public static void updateTotal(double value) {
        total += value;
    }
}
