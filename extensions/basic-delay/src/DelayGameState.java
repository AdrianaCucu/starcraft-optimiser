import java.util.*;
import constructions.units.*;
import constructions.buildings.*;

public class DelayGameState {

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
    public static HashMap<String, Integer> freeDelayBuildings = new HashMap<>();

    /**
     * The buildings that are in the process of building a unit.
     */
    public static HashMap<String, Integer> busyDelayBuildings = new HashMap<>();

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

        constructionsBeingBuilt.put(DelayBarracks.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayCommandCenter.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayFactory.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayRefinery.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayStarport.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelaySupplyDepot.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayHellion.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayMarine.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayMedivac.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayViking.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(DelayWorker.IDENT, new ArrayList<Integer>());

        freeDelayBuildings.put(DelayBarracks.IDENT, 0);
        freeDelayBuildings.put(DelayCommandCenter.IDENT, 1);
        freeDelayBuildings.put(DelayFactory.IDENT, 0);
        freeDelayBuildings.put(DelayRefinery.IDENT, 0);
        freeDelayBuildings.put(DelayStarport.IDENT, 0);
        freeDelayBuildings.put(DelaySupplyDepot.IDENT, 0);

        busyDelayBuildings.put(DelayBarracks.IDENT, 0);
        busyDelayBuildings.put(DelayCommandCenter.IDENT, 0);
        busyDelayBuildings.put(DelayFactory.IDENT, 0);
        busyDelayBuildings.put(DelayRefinery.IDENT, 0);
        busyDelayBuildings.put(DelayStarport.IDENT, 0);
        busyDelayBuildings.put(DelaySupplyDepot.IDENT, 0);

        units.put(DelayHellion.IDENT, 0);
        units.put(DelayMarine.IDENT, 0);
        units.put(DelayMedivac.IDENT, 0);
        units.put(DelayViking.IDENT, 0);

        workers.put(DelayWorker.FREE, 6);
        workers.put(DelayWorker.GAS, 0);
        workers.put(DelayWorker.MINERALS, 0);
    }

    /**
     * Updates the buildings when a unit is finished being built.
     *
     * @param builtFrom - the building that builds the unit
     */
    private static void finishedDelayBuildingDelayUnit(String builtFrom) {
        busyDelayBuildings.put(builtFrom, busyDelayBuildings.get(builtFrom) - 1);
        freeDelayBuildings.put(builtFrom, freeDelayBuildings.get(builtFrom) + 1);
    }

    /**
     * Updates the buildings based on whether they are starting or finishing building a unit.
     *
     * @param key - the unit
     * @param action - "start" or "finish"
     */
    public static void updateDelayBuildings (String key, String action) {
        if (action.equals("start")) {
            busyDelayBuildings.put(key, busyDelayBuildings.get(key) + 1);
            freeDelayBuildings.put(key, freeDelayBuildings.get(key) - 1);
        }
        else if (action.equals("finish")) {
            switch (key) {
                case DelayHellion.IDENT: {
                    finishedDelayBuildingDelayUnit(DelayHellion.builtFrom);
                    break;
                }
                case DelayMarine.IDENT: {
                    finishedDelayBuildingDelayUnit(DelayMarine.builtFrom);
                    break;
                }
                case DelayMedivac.IDENT: {
                    finishedDelayBuildingDelayUnit(DelayMedivac.builtFrom);
                    break;
                }
                case DelayViking.IDENT: {
                    finishedDelayBuildingDelayUnit(DelayViking.builtFrom);
                    break;
                }
                case DelayWorker.IDENT: {
                    finishedDelayBuildingDelayUnit(DelayWorker.builtFrom);
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
        if (workers.get(DelayWorker.MINERALS) <= patches * 2) {
            minerals += workers.get(DelayWorker.MINERALS) * (41.0 / 60);
        } else {
            minerals += patches * 2 * (41.0/60) + (workers.get(DelayWorker.MINERALS) - patches * 2) * (20.0 / 60);
        }

        /**
         * 38 gas / minute
         */
        gas += workers.get(DelayWorker.GAS) * (38.0 / 60);
    }

    /**
     * Updates the constructions that were finished being built.
     *
     * @param key - the idetifier of the construction
     */
    private static void updateCompletedConstructions(String key) {
        if (key.equals(DelayHellion.IDENT) || key.equals(DelayMarine.IDENT) || key.equals(DelayMedivac.IDENT) || key.equals(DelayViking.IDENT)) {
            units.put(key, units.get(key) + 1);
            updateDelayBuildings(key, "finish");
        }

        else if (key.equals(DelayWorker.IDENT)) {
            workers.put(DelayWorker.FREE, workers.get(DelayWorker.FREE) + 1);
            updateDelayBuildings(DelayWorker.IDENT, "finish");
        }

        else {
            freeDelayBuildings.put(key, freeDelayBuildings.get(key) + 1);
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
        DelayGoal.goalAchieved = goalMet();
    }
}
