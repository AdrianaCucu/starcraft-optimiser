import java.util.*;
import constructions.*;
import base.*;

public class GameState {
    private static int minerals;
    private static int gas;
    private static int time;

    private static LinkedHashMap<Integer, ArrayList<Construction>> buildOrder;
    private static LinkedHashMap<Integer, ArrayList<Construction>> completionQueue;
    private static HashMap<String, ArrayList<Construction>> completedConstructions;
    public static Base base;

    public static HashMap<String, ArrayList<Construction>> getCompletedConstructions() {
        return completedConstructions;
    }

    private static Base getBase() {
        return base;
    }

    /**
     * Sets game state to initial game state.
     * Sets listed goals in spec in goal class.
     */
    public static void initialise() {
        Game.setGoalReached(false);
        Goal.setGoals();

        minerals = 0;
        gas = 0;
        time = 0;

        base = new Base();
        completedConstructions = new LinkedHashMap<>();

        ArrayList<Construction> commandCenters = new ArrayList<>();
        commandCenters.add(new CommandCenter());

        completedConstructions.put(CommandCenter.IDENT, commandCenters);

        ArrayList<Construction> workers = new ArrayList<>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());

        completedConstructions.put(Worker.IDENT, workers);

    }

    public static void incrementTime() {
        time++;
    }
}

