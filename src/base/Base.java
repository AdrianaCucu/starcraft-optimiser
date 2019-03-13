package base;

public class Base {

    private int numberOfWorkers = 0;

    /**
     * Maximum 3 workers at the same time.
     */
    private final int MAX_WORKERS = 3;

    public void incrementWorkers() {
        numberOfWorkers += 1;
    }

    public int getWorkers() {
        return numberOfWorkers;
    }
}
