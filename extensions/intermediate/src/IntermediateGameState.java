import java.util.*;
import constructions.units.*;
import constructions.buildings.*;

public class IntermediateGameState {

    /**
     * Total supply for the current state.
     */
    public static int supply = 0;

    /**
     * The supply used so far.
     */
    public static int supplyUsed = 0;

    /**
     * Maximum supply allowed for a game.
     */
    public static final int maxSupply = 200;

    /**
     * The supply needed to reach the goal.
     * Depends on the goal.
     */
    public static int goalSupply = 0;

    /**
     * The supply available for building workers based on the goal.
     */
    public static int workersSupply = 0;

    public static int time = 0;

    public static double minerals;
    public static double gas;

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
    public static HashMap<String, Integer> freeIntermediateBuildings = new HashMap<>();

    /**
     * The buildings that are in the process of building a unit.
     */
    public static HashMap<String, Integer> busyIntermediateBuildings = new HashMap<>();

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

        IntermediateDecision.decisionsMade.add("(supply)");

        minerals = 50;
        gas = 0;
        supply = 10;

        constructionsBeingBuilt.put(IntermediateBarracks.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateCommandCenter.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateFactory.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateRefinery.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateStarport.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateSupplyDepot.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateHellion.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateMarine.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateMedivac.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateViking.IDENT, new ArrayList<Integer>());
        constructionsBeingBuilt.put(IntermediateWorker.IDENT, new ArrayList<Integer>());

        freeIntermediateBuildings.put(IntermediateBarracks.IDENT, 0);
        freeIntermediateBuildings.put(IntermediateCommandCenter.IDENT, 1);
        freeIntermediateBuildings.put(IntermediateFactory.IDENT, 0);
        freeIntermediateBuildings.put(IntermediateRefinery.IDENT, 0);
        freeIntermediateBuildings.put(IntermediateStarport.IDENT, 0);
        freeIntermediateBuildings.put(IntermediateSupplyDepot.IDENT, 0);

        busyIntermediateBuildings.put(IntermediateBarracks.IDENT, 0);
        busyIntermediateBuildings.put(IntermediateCommandCenter.IDENT, 0);
        busyIntermediateBuildings.put(IntermediateFactory.IDENT, 0);
        busyIntermediateBuildings.put(IntermediateRefinery.IDENT, 0);
        busyIntermediateBuildings.put(IntermediateStarport.IDENT, 0);
        busyIntermediateBuildings.put(IntermediateSupplyDepot.IDENT, 0);

        units.put(IntermediateHellion.IDENT, 0);
        units.put(IntermediateMarine.IDENT, 0);
        units.put(IntermediateMedivac.IDENT, 0);
        units.put(IntermediateViking.IDENT, 0);

        workers.put(IntermediateWorker.FREE, 6);
        workers.put(IntermediateWorker.GAS, 0);
        workers.put(IntermediateWorker.MINERALS, 0);
    }

    /**
     * Updates the buildings when a unit is finished being built.
     * 
     * @param builtFrom - the building that builds the unit
     */
    private static void finishedBuildingUnit(String builtFrom) {
        busyIntermediateBuildings.put(builtFrom, busyIntermediateBuildings.get(builtFrom) - 1);
        freeIntermediateBuildings.put(builtFrom, freeIntermediateBuildings.get(builtFrom) + 1);
    }

    /**
     * Updates the buildings based on whether they are starting or finishing building a unit.
     * 
     * @param key - the unit
     * @param action - "start" or "finish"
     */
    public static void updateIntermediateBuildings (String key, String action) {
        if (action.equals("start")) {
            busyIntermediateBuildings.put(key, busyIntermediateBuildings.get(key) + 1);
            freeIntermediateBuildings.put(key, freeIntermediateBuildings.get(key) - 1);
        }
        else if (action.equals("finish")) {
            switch (key) {
                case IntermediateHellion.IDENT: {
                    finishedBuildingUnit(IntermediateHellion.builtFrom);
                    break;
                }
                case IntermediateMarine.IDENT: {
                    finishedBuildingUnit(IntermediateMarine.builtFrom);
                    break;
                }
                case IntermediateMedivac.IDENT: {
                    finishedBuildingUnit(IntermediateMedivac.builtFrom);
                    break;
                }
                case IntermediateViking.IDENT: {
                    finishedBuildingUnit(IntermediateViking.builtFrom);
                    break;
                }
                case IntermediateWorker.IDENT: {
                    finishedBuildingUnit(IntermediateWorker.builtFrom);
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
        if (workers.get(IntermediateWorker.MINERALS) <= patches * 2) {
            minerals += workers.get(IntermediateWorker.MINERALS) * (41.0 / 60);
        } else {
            minerals += patches * 2 * (41.0/60) + (workers.get(IntermediateWorker.MINERALS) - patches * 2) * (20.0 / 60);
        }

        /**
         * 38 gas / minute
         */
        gas += workers.get(IntermediateWorker.GAS) * (38.0 / 60);
    }

    /**
     * Updates the constructions that were finished being built.
     * 
     * @param key - the idetifier of the construction
     */
    private static void updateCompletedConstructions(String key) {
        if (key.equals(IntermediateHellion.IDENT) || key.equals(IntermediateMarine.IDENT) || key.equals(IntermediateMedivac.IDENT) || key.equals(IntermediateViking.IDENT)) {
            units.put(key, units.get(key) + 1);
            updateIntermediateBuildings(key, "finish");
        }

        else if (key.equals(IntermediateWorker.IDENT)) {
            workers.put(IntermediateWorker.FREE, workers.get(IntermediateWorker.FREE) + 1);
            updateIntermediateBuildings(IntermediateWorker.IDENT, "finish");
        }

        else {
            freeIntermediateBuildings.put(key, freeIntermediateBuildings.get(key) + 1);

            /**
             * Updates the total supply.
             */
            if (key.equals(IntermediateCommandCenter.IDENT)) IntermediateGameState.supply += IntermediateCommandCenter.supplyProvided;
            else if (key.equals(IntermediateSupplyDepot.IDENT)) IntermediateGameState.supply += IntermediateSupplyDepot.supplyProvided;

            if (IntermediateGameState.supply > 200) IntermediateGameState.supply = 200;
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
    public static void updateIntermediateGameState () {
        updateResources();
        updateConstructionsBeingBuilt();
        IntermediateGoal.goalAchieved = goalMet();
    }
}
