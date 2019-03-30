import java.util.*;
import constructions.units.*;
import constructions.buildings.*;

public class GameState {

    public static int time = 0;

    public static double minerals = 50;
    public static double gas = 0;

    public static final int geysers = 2;
    public static final int patches = 8;

    /**
     * The goal that needs to be met.
     * Stored as the identifier of the unit and the number required for each type of unit.
     */
    public static HashMap<String, Integer> goal = new HashMap<>();

    /**
     * Stores the buildings and units that are in the process of being built.
     * The ArrayList stores the numbers of seconds remaining for each instance of the construction.
     */
    public static HashMap<String, ArrayList<Integer>> constructionsBeingBuilt = new HashMap<>();

    /**
     * The buildings that are not busy building a unit.
     */
    public static HashMap<String, Integer> freeBuildings = new HashMap<>();

    /**
     * The buildings that are in the process of building a unit.
     */
    public static HashMap<String, Integer> busyBuildings = new HashMap<>();

    /**
     * The units that were already created (excluding workers).
     */
    public static HashMap<String, Integer> units = new HashMap<>();

    /**
     * The workers that were already created.
     * Stored based on the action they are performing (collecting gas, collecting minerals, and nothing).
     */
    public static HashMap<String, Integer> workers = new HashMap<>();


    /**
     * The initial state of the game in terms of buildings and units.
     */
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

        workers.put(Worker.FREE, 6);
        workers.put(Worker.GAS, 0);
        workers.put(Worker.MINERALS, 0);
    }

    /**
     * Updates the buildings when a unit is finished being built.
     * 
     * @param builtFrom - the building that builds the unit
     */
    private static void finishedBuildingUnit(String builtFrom) {
        busyBuildings.put(builtFrom, busyBuildings.get(builtFrom) - 1);
        freeBuildings.put(builtFrom, freeBuildings.get(builtFrom) + 1);
    }

    /**
     * Updates the buildings based on whether they are starting or finishing building a unit.
     * 
     * @param key - the unit
     * @param action - "start" or "finish"
     */
    public static void updateBuildings (String key, String action) {
        if (action.equals("start")) {
            busyBuildings.put(key, busyBuildings.get(key) + 1);
            freeBuildings.put(key, freeBuildings.get(key) - 1);
        }
        else if (action.equals("finish")) {
            switch (key) {
                case Hellion.IDENT: {
                    finishedBuildingUnit(Hellion.builtFrom);
                    break;
                }
                case Marine.IDENT: {
                    finishedBuildingUnit(Marine.builtFrom);
                    break;
                }
                case Medivac.IDENT: {
                    finishedBuildingUnit(Medivac.builtFrom);
                    break;
                }
                case Viking.IDENT: {
                    finishedBuildingUnit(Viking.builtFrom);
                    break;
                }
                case Worker.IDENT: {
                    finishedBuildingUnit(Worker.builtFrom);
                    break;
                }
            }
        }
    }

    /**
     * Updates the resources at each second of the game.
     * Resources are collected in the most efficient way possible for the current configuration.
     * e.g.: If we have 4 workers on mineral patches, we will have 2 and 2 on different ones to maximise collection.
     */
    private static void updateResources() {

        /**
         * 41 minerals / minute or 20 minerals / minute
         */
        if (workers.get(Worker.MINERALS) <= patches * 2) {
            minerals += workers.get(Worker.MINERALS) * (41.0 / 60);
        } else {
            minerals += patches * 2 * (41.0/60) + (workers.get(Worker.MINERALS) - patches * 2) * (20.0 / 60);
        }

        /**
         * 38 gas / minute
         */
        gas += workers.get(Worker.GAS) * (38.0 / 60);
    }

    /**
     * Updates the constructions that were finished being built.
     * 
     * @param key - the idetifier of the construction
     */
    private static void updateCompletedConstructions(String key) {
        if (key.equals(Hellion.IDENT) || key.equals(Marine.IDENT) || key.equals(Medivac.IDENT) || key.equals(Viking.IDENT)) {
            units.put(key, units.get(key) + 1);
            updateBuildings(key, "finish");
        }

        else if (key.equals(Worker.IDENT)) {
            workers.put(Worker.FREE, workers.get(Worker.FREE) + 1);
            updateBuildings(Worker.IDENT, "finish");
        }

        else {
            freeBuildings.put(key, freeBuildings.get(key) + 1);
        }
    }

    /**
     * Updates the constructions that are in the process of being built.
     */
    private static void updateConstructionsBeingBuilt() {

        if (constructionsBeingBuilt.size() != 0) {

            /**
             * Iterates through all constructions in the map.
             */
            for (String key : constructionsBeingBuilt.keySet()) {
                
                /**
                 * The remaining times before being updated.
                 */
                ArrayList<Integer> durations = constructionsBeingBuilt.get(key);

                /**
                 * Stores the updated times remaining for the constructions in the process of being built.
                 */
                ArrayList<Integer> newDurations = new ArrayList<>();

                /**
                 * Iterates through the remaining durations for each construction.
                 * If it is 1, the construction will be completed, and the game state is updated accordingly.
                 * Otherwise, the updated times are added to newDurations and the HashMap is updated.
                 */
                for (int duration: durations) {
                    if (duration == 1) {
                        updateCompletedConstructions(key);
                    }
                    else {
                        newDurations.add(duration - 1);
                    }
                }
                constructionsBeingBuilt.put(key, newDurations);
            }
        }
    }

    /**
     * Returns true if the goal has been met and false otherwise.
     */
    private static boolean goalMet() {
        for (String key : goal.keySet()) {
            if (units.get(key) < goal.get(key)){
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the game state after every second.
     * The time is not updated here, because it is increased after decisions are being made, not before.
     */
    public static void updateGameState () {
        updateResources();
        updateConstructionsBeingBuilt();
        Goal.goalAchieved = goalMet();
    }
}
