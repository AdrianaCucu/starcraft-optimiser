import java.util.*;
import constructions.units.*;
import constructions.buildings.*;

public class GameState {

    public static int time = 0;

    public static double minerals = 50;
    public static double gas = 0;

    public static final int geysers = 2;
    public static final int patches = 8;

    // the goal that needs to be met
    public static HashMap<String, Integer> goal = new HashMap<>();

    // buildings and units that are in the process of being built
    // the array list of integers stores the number of seconds remaining
    // for each instance of the construction
    // (if we have multiple instances of the same kind being built at the same time)
    public static HashMap<String, ArrayList<Integer>> constructionsBeingBuilt = new HashMap<>();


    // identifiers of buildings and number of free buildings for each
    public static HashMap<String, Integer> freeBuildings = new HashMap<>();

    // identifiers of buildings and number of busy buildings for each
    // no need to store what the building is building, because we only need the numbers
    public static HashMap<String, Integer> busyBuildings = new HashMap<>();

    // units without workers
    public static HashMap<String, Integer> units = new HashMap<>();

    // the String is what they are assigned to
    // "free" / "gas" / "minerals"
    public static HashMap<String, Integer> workers = new HashMap<>();


    // initial state of the game in terms of buildings, units, and time
    public static void initialiseGame() {

        constructionsBeingBuilt.put(Barracks.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(CommandCenter.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Factory.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Refinery.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Starport.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(SupplyDepot.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Hellion.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Marine.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Medivac.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Viking.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(Worker.IDENT, new ArrayList<Integer>());

        freeBuildings.put(Barracks.IDENT, 0);
        freeBuildings.put(CommandCenter.IDENT, 1);
        freeBuildings.put(Factory.IDENT, 0);
        freeBuildings.put(Refinery.IDENT, 0);
        freeBuildings.put(Starport.IDENT, 0);
        freeBuildings.put(SupplyDepot.IDENT, 0);

        busyBuildings.put(Barracks.IDENT, 0);
        busyBuildings.put(CommandCenter.IDENT, 0);
        busyBuildings.put(Factory.IDENT, 0);
        busyBuildings.put(Refinery.IDENT, 0);
        busyBuildings.put(Starport.IDENT, 0);
        busyBuildings.put(SupplyDepot.IDENT, 0);

        units.put(Hellion.IDENT, 0);
        units.put(Marine.IDENT, 0);
        units.put(Medivac.IDENT, 0);
        units.put(Viking.IDENT, 0);

        workers.put("free", 6);
        workers.put("gas", 0);
        workers.put("minerals", 0);
    }

    public static void updateBuildings (String key, String action) {
        if (action.equals("start")) {
            Integer busyNumber = busyBuildings.get(key) + 1;
            busyBuildings.put(key, busyNumber);
            Integer freeNumber = freeBuildings.get(key) - 1;
            freeBuildings.put(key, freeNumber);
        }
        else if (action.equals("finish")) {
            switch (key) {
                case "hellion": {
                    Integer busyNumber = busyBuildings.get(Hellion.builtFrom) - 1;
                    busyBuildings.put(Hellion.builtFrom, busyNumber);
                    Integer freeNumber = freeBuildings.get(Hellion.builtFrom) + 1;
                    freeBuildings.put(Hellion.builtFrom, freeNumber);
                    break;
                }
                case "marine": {
                    Integer busyNumber = busyBuildings.get(Marine.builtFrom) - 1;
                    busyBuildings.put(Marine.builtFrom, busyNumber);
                    Integer freeNumber = freeBuildings.get(Marine.builtFrom) + 1;
                    freeBuildings.put(Marine.builtFrom, freeNumber);
                    break;
                }
                case "medivac": {
                    Integer busyNumber = busyBuildings.get(Medivac.builtFrom) - 1;
                    busyBuildings.put(Medivac.builtFrom, busyNumber);
                    Integer freeNumber = freeBuildings.get(Medivac.builtFrom) + 1;
                    freeBuildings.put(Medivac.builtFrom, freeNumber);
                    break;
                }
                case "viking": {
                    Integer busyNumber = busyBuildings.get(Viking.builtFrom) - 1;
                    busyBuildings.put(Viking.builtFrom, busyNumber);
                    Integer freeNumber = freeBuildings.get(Viking.builtFrom) + 1;
                    freeBuildings.put(Viking.builtFrom, freeNumber);
                    break;
                }
                case "worker": {
                    Integer busyNumber = busyBuildings.get(Worker.builtFrom) - 1;
                    busyBuildings.put(Worker.builtFrom, busyNumber);
                    Integer freeNumber = freeBuildings.get(Worker.builtFrom) + 1;
                    freeBuildings.put(Worker.builtFrom, freeNumber);
                    break;
                }
            }
        }
    }

    // updating resources each second
    // when updating resources, we assume workers are assigned
    // in the most efficient way within a collecting place
    // e.g.: if we have 4 workers on mineral patches,
    // we will have 2 and 2 on different ones to maximise profit
    // 41 minerals / minute or 20 minerals / minute
    // 38 gas / minute
    private static void updateResources() {
        if (workers.get("minerals") <= 16) {
            minerals += workers.get("minerals") * (41.0 / 60);
        } else {
            minerals += 16 * (41.0/60) + (workers.get("minerals") - 16) * (20.0 / 60);
        }
        gas += workers.get("gas") * (38.0 / 60);
    }

    private static void updateCompletedConstructions(String key) {
        if (key.equals("hellion") || key.equals("marine") || key.equals("medivac") || key.equals("viking")) {
            Integer number = units.get(key) + 1;
            units.put(key, number);
            updateBuildings(key, "finish");
        }

        else if (key.equals("worker")) {
            Integer number = workers.get("free") + 1;
            workers.put("free", number);
            updateBuildings("worker", "finish");
        }

        else {
            Integer number = freeBuildings.get(key) + 1;
            freeBuildings.put(key, number);
        }
    }

    private static void updateConstructionsBeingBuilt() {

        if (constructionsBeingBuilt.size() != 0) {

            // iterates through each key (construction identifier) in the map
            for (String key : constructionsBeingBuilt.keySet()) {
                
                ArrayList<Integer> durations = constructionsBeingBuilt.get(key);

                // iterates through each duration for the specific construction
                // if it is 1, adds a new construction to its specific map
                // (because next second will be 0, so it will be finished)
                // if it is different to 1, it subtracts a second to update the duration
                for (int i = 0; i < durations.size(); i++) {
                    if (durations.get(i) == 0) {
                        updateCompletedConstructions(key);
                    }
                    else {
                        durations.add(durations.get(i) - 1);
                    }
                    durations.remove(durations.get(i));
                }
                constructionsBeingBuilt.put(key, durations);
            }
        }
    }

    public static void updateGameState () {
        time += 1;
        updateResources();
        updateConstructionsBeingBuilt();

         // checks if the goal has been met and updates it accordingly
        boolean goalMet = true;

        for (String key : goal.keySet()) {
            if (key.equals("worker") && ((workers.get("free") + workers.get("gas") + workers.get("minerals")) < goal.get(key))) {
                goalMet = false;
            }
            else if (!key.equals("worker") && (units.get(key) < goal.get(key))){
                goalMet = false;
            }
        }

        Goal.goalAchieved = goalMet;
    }
}
