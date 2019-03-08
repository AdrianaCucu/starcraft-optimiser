public class Timer {

    private static int secondsElapsed = 0;

    public static int getSecondsElapsed() {
        return secondsElapsed;
    }

    /**
     * increments time elapsed
     */
    public static void incrementTime() {
        secondsElapsed++;
    }
}
