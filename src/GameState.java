import java.lang.reflect.Array;
import java.util.*;
import units.*;
import constructions.*;

public class GameState {
    private static int minerals;
    private static int gas;
    private static int time;

    private static LinkedHashMap<Integer, ArrayList<Construction>> buildOrder;
    private static LinkedHashMap<Integer, ArrayList<Construction>> completionQueue;
    private static HashMap<String, ArrayList<Construction>> completedConstructions;

    public static HashMap<String, ArrayList<Construction>> getCompletedConstructions() {
        return completedConstructions;
    }


    public void initialiseGameState() {
        this.minerals = 50;
        this.gas = 0;
        this.time = 0;

        buildOrder = new LinkedHashMap<>();
        completionQueue = new LinkedHashMap<>();
        completedConstructions = new HashMap<>();

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

        completedConstructions.put(Worker.IDENT, workers);

    }


}