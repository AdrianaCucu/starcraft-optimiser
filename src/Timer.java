import java.sql.Time;

public abstract class Timer {
    private static int secondsElapsed;

    public static void setSecondsElapsed(int secondsElapsed) {
        Timer.secondsElapsed = secondsElapsed;
    }

    /**
     * increments time elapsed
     */
    public static void incrementTime() {
        secondsElapsed++;
    }


}
