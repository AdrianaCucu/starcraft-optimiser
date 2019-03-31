import constructions.buildings.*;
import constructions.units.*;

import java.util.*;

public class DelayDecision {

    private static Random random = new Random();

    /**
     * Stores the build orders as Strings for printing.
     */
    public static ArrayList<String> decisionsMade = new ArrayList<>();

    /**
     * Stores the names and indices of the units that are valuable for the goal.
     */
    public static HashMap<String, Integer> possibleDecisions = new HashMap<>();


    /**
     * Chooses the decisions to make until the goal is met.
     */
    public static void makeDecision() {

        /**
         * Updates the game state (without the time) at every second of the game.
         * Reasons for doing this before making a decision:
         *     - more resources when trying to build something
         *     - the constructions that have finished building are updated, and they can be used
         */
        DelayGameState.updateGameState();

        /**
         * Checks whether the goal has already been met before attempting to make a decision.
         * Makes fewer decisions for more realistic timings.
         * Only makes decisions every 5 seconds for more realistic timings.
         */
        if (!DelayGoal.goalAchieved && DelayGameState.time % 5 == 0) {

            /**
             * Removes the unit from the possible decisions if the goal for that particular unit has been met.
             * In this way, it does not build more units than required and the goal is met faster.
             *
             * https://www.geeksforgeeks.org/remove-an-entry-using-key-from-hashmap-while-iterating-over-it/
             */
            possibleDecisions.entrySet().removeIf(entry -> (DelayGameState.goal.get(entry.getKey()) ==
                    DelayGameState.units.get(entry.getKey()) + DelayGameState.constructionsBeingBuilt.get(entry.getKey()).size()));

            /**
             * Chooses a random index from the possible decisions.
             */
            Object[] indices = possibleDecisions.values().toArray();
            if (indices.length != 0) {
                Integer index =  (Integer)indices[random.nextInt(indices.length)];

                /**
                 * Makes a decision based on one of the possible decisions.
                 */
                makeDecision(index);
            }
        }

        /**
         * Updates the game time.
         */
        DelayGameState.time += 1;
    }

    /**
     * Makes a decision based on the possible decisions at the current game time.
     */
    public static void makeDecision(int index) {

        /**
         * Based on the index, tries to build a specific kind of unit.
         */
        switch (index) {

            // "build marine"
            case DelayMarine.INDEX: {

                /**
                 * Tries to build a DelayMarine, or (if not possible) a building relevant to DelayMarines.
                 */
                buildDelayMarine();

                    /**
                     * Makes a decision to improve resources.
                     * Gas is not needed for DelayMarines, but if the goal may contain other units.
                     */
                    if (possibleDecisions.containsKey(DelayHellion.IDENT)
                            || possibleDecisions.containsKey(DelayMedivac.IDENT)
                            || possibleDecisions.containsKey(DelayViking.IDENT)) {
                        randomDecision(5);
                    } else {

                        /**
                         * If the goal only contains DelayMarines, makes a decision for minerals only.
                         * The method is built such that the first 3 cases in the switch statement are relevant to minerals.
                         */
                        randomDecision(3);
                    }
                break;
            }

            // "build hellion"
            case DelayHellion.INDEX: {

                /**
                 * Tries to build a DelayHellion, or (if not possible) a building relevant to Hellions.
                 */
                buildHellion();

                /**
                 * Makes a decision to improve the resources.
                 */
                randomDecision(5);
                break;
            }

            // "build medivac"
            case DelayMedivac.INDEX: {

                /**
                 * Tries to build a DelayMedivac, or (if not possible) a building relevant to DelayMedivacs.
                 */
                buildDelayMedivac();

                /**
                 * Makes a decision to improve the resources.
                 */
                randomDecision(5);
                break;
            }

            // "build viking"
            case DelayViking.INDEX: {

                /**
                 * Tries to build a DelayViking, or (if not possible) a building relevant to DelayVikings.
                 */
                buildDelayViking();

                /**
                 * Makes a decision to improve the resources.
                 */
                randomDecision(5);
                break;
            }
        }
    }

    /**
     * Makes a random decision to update the resources based on the number passed in.
     * If the number is 3, only minerals are needed.
     * If the number is 5, both gas and minerals are needed.
     *
     * @param number - the number that indicates the recources needed
     */
    private static void randomDecision(int number) {

        /**
         * Reassigning free workers is attempted whenever the method is called.
         * This is done to further optimise resource collection, because ideally we would not have free workers.
         */
        if (number == 5 && DelayGameState.workers.get(DelayWorker.FREE) > 0
                && DelayGameState.workers.get(DelayWorker.GAS) < DelayGameState.freeDelayBuildings.get(DelayRefinery.IDENT) * 3
                && DelayGameState.gas <= DelayGameState.minerals) {
            reassignDelayWorker(DelayWorker.FREE, DelayWorker.GAS);
        } else if (DelayGameState.workers.get(DelayWorker.FREE) > 0
                && DelayGameState.workers.get(DelayWorker.MINERALS) < DelayGameState.patches * 3) {
            reassignDelayWorker(DelayWorker.FREE, DelayWorker.MINERALS);
        }

            int i = random.nextInt(number);

            /**
             * A random action is attempted based on the resources needed.
             */
            switch (i) {

                // "reassign worker from gas to minerals"
                case 0: {
                    if (DelayGameState.workers.get(DelayWorker.GAS) > 0 && DelayGameState.workers.get(DelayWorker.MINERALS) < DelayGameState.patches * 3) {
                        reassignDelayWorker(DelayWorker.GAS, DelayWorker.MINERALS);
                    }
                    break;
                }

                // "build worker"
                case 1: {
                    if (DelayGameState.workers.get(DelayWorker.FREE) + DelayGameState.workers.get(DelayWorker.MINERALS) < DelayGameState.patches * 3) {

                        boolean build = true;

                        /**
                         * Before attempting to build a DelayWorker, checks if any unit will be completed soon.
                         * That is because a more valuable decision could be made after the unit is finished, so the resources are preserved.
                         */
                        for (String unit : possibleDecisions.keySet()) {
                            for (Integer seconds : DelayGameState.constructionsBeingBuilt.get(unit)) {
                                if (seconds < DelayWorker.buildTime) {
                                    build = false;
                                }
                            }
                        }

                        if (build) {
                            buildDelayWorker();
                        }
                    }
                    break;
                }

                // "build command center"
                case 2: {
                    buildDelayCommandCenter();
                    break;
                }

                // "reassign worker from minerals to gas"
                case 3: {
                    if (DelayGameState.workers.get(DelayWorker.MINERALS) > 0
                            && DelayGameState.workers.get(DelayWorker.GAS) < DelayGameState.freeDelayBuildings.get(DelayRefinery.IDENT) * 3) {
                        reassignDelayWorker(DelayWorker.MINERALS, DelayWorker.GAS);
                    }
                    break;
                }

                // "build refinery"
                case 4: {
                    buildDelayRefinery();
                    break;
                }
            }
    }

    private static void buildDelayMarine() {

        /**
         * If a free DelayBarracks exists and there are enough resources, a DelayMarine can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayBarracks.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayMarine.mineralCost && DelayGameState.gas >= DelayMarine.gasCost) {
                buildDelayUnit(DelayMarine.IDENT, DelayMarine.builtFrom, DelayMarine.buildTime, DelayMarine.mineralCost, DelayMarine.gasCost);
            }
        }

        /**
         * If a free DelayBarracks does not exist, tries to build one.
         * This happens when there is no DelayBarracks that is busy or that is being built.
         */
        else if ((DelayGameState.constructionsBeingBuilt.get(DelayBarracks.IDENT).size() == 0
                || DelayGameState.busyDelayBuildings.get(DelayBarracks.IDENT) == 0)) {
            buildDelayBarracks();
        }
    }

    private static void buildHellion() {

        /**
         * If a free DelayFactory exists and there are enough resources, a DelayHellion can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayFactory.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayHellion.mineralCost && DelayGameState.gas >= DelayHellion.gasCost) {
                buildDelayUnit(DelayHellion.IDENT, DelayHellion.builtFrom, DelayHellion.buildTime, DelayHellion.mineralCost, DelayHellion.gasCost);
            }
        }

        /**
         * If a free DelayFactory does not exist, tries to build one.
         * This happens when there is no DelayFactory that is busy or that is being built.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelayFactory.IDENT).size() == 0
                || DelayGameState.busyDelayBuildings.get(DelayFactory.IDENT) == 0) {
            buildDelayFactory();
        }
    }

    private static void buildDelayMedivac() {

        /**
         * If a free DelayStarport exists and there are enough resources, a DelayMedivac can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayStarport.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayMedivac.mineralCost && DelayGameState.gas >= DelayMedivac.gasCost) {
                buildDelayUnit(DelayMedivac.IDENT, DelayMedivac.builtFrom, DelayMedivac.buildTime, DelayMedivac.mineralCost, DelayMedivac.gasCost);
            }
        }

        /**
         * If a free DelayStarport does not exist, tries to build one.
         * This happens when there is no DelayStarport that is busy or that is being built.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelayStarport.IDENT).size() == 0
                || DelayGameState.busyDelayBuildings.get(DelayStarport.IDENT) == 0) {
            buildDelayStarport();
        }
    }

    private static void buildDelayViking() {

        /**
         * If a free DelayStarport exists and there are enough resources, a DelayViking can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayStarport.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayViking.mineralCost && DelayGameState.gas >= DelayViking.gasCost) {
                buildDelayUnit(DelayViking.IDENT, DelayViking.builtFrom, DelayViking.buildTime, DelayViking.mineralCost, DelayViking.gasCost);
            }
        }

        /**
         * If a free DelayStarport does not exist, tries to build one.
         * This happens when there is no DelayStarport that is busy or that is being built.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelayStarport.IDENT).size() == 0
                || DelayGameState.busyDelayBuildings.get(DelayStarport.IDENT) == 0) {
            buildDelayStarport();
        }
    }

    private static void buildDelayWorker() {

        /**
         * If a free Command Center exists and there are enough resources, a DelayWorker can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayCommandCenter.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayWorker.mineralCost && DelayGameState.gas >= DelayWorker.gasCost) {
                buildDelayUnit(DelayWorker.IDENT, DelayWorker.builtFrom, DelayWorker.buildTime, DelayWorker.mineralCost, DelayWorker.gasCost);
            }
        }
    }

    private static void buildDelayBarracks() {

        /**
         * If there are enough resources and the dependency of the DelayBarracks is met, it can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelaySupplyDepot.IDENT) > 0 || DelayGameState.busyDelayBuildings.get(DelaySupplyDepot.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayBarracks.mineralCost && DelayGameState.gas >= DelayBarracks.gasCost) {
                buildDelayBuilding(DelayBarracks.IDENT, DelayBarracks.buildTime, DelayBarracks.mineralCost, DelayBarracks.gasCost);
            }
        }

        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelaySupplyDepot.IDENT).size() == 0) {
            buildDelaySupplyDepot();
        }
    }

    private static void buildDelayCommandCenter() {

        /**
         * If there are enough resources for a Command Center, it can be built.
         * A maximum of 3 Command Centers can be build, otherwise efficiency decreases.
         */
        if (DelayGameState.minerals >= DelayCommandCenter.mineralCost && DelayGameState.gas >= DelayCommandCenter.gasCost
                && DelayGameState.freeDelayBuildings.get(DelayCommandCenter.IDENT)
                + DelayGameState.busyDelayBuildings.get(DelayCommandCenter.IDENT)
                + DelayGameState.constructionsBeingBuilt.get(DelayCommandCenter.IDENT).size() < 3) {

            buildDelayBuilding(DelayCommandCenter.IDENT, DelayCommandCenter.buildTime, DelayCommandCenter.mineralCost, DelayCommandCenter.gasCost);
        }
    }

    private static void buildDelayStarport() {

        /**
         * If there are enough resources and the dependency of the DelayStarport is met, it can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayFactory.IDENT) > 0 || DelayGameState.busyDelayBuildings.get(DelayFactory.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayStarport.mineralCost && DelayGameState.gas >= DelayStarport.gasCost) {
                buildDelayBuilding(DelayStarport.IDENT, DelayStarport.buildTime, DelayStarport.mineralCost, DelayStarport.gasCost);
            }
        }

        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelayFactory.IDENT).size() == 0) {
            buildDelayFactory();
        }
    }

    private static void buildDelaySupplyDepot() {

        /**
         * If there are enough resources for a Supply Depot, it can be built.
         */
        if (DelayGameState.minerals >= DelaySupplyDepot.mineralCost && DelayGameState.gas >= DelaySupplyDepot.gasCost) {
            buildDelayBuilding(DelaySupplyDepot.IDENT, DelaySupplyDepot.buildTime, DelaySupplyDepot.mineralCost, DelaySupplyDepot.gasCost);
        }
    }

    private static void buildDelayRefinery() {

        /**
         * If there are enough resources for a DelayRefinery, it can be built.
         * The maximum number of refineries that can be build is the number of geysers.
         */
        if (DelayGameState.minerals >= DelayRefinery.mineralCost && DelayGameState.gas >= DelayRefinery.gasCost
                && DelayGameState.freeDelayBuildings.get(DelayRefinery.IDENT)
                + DelayGameState.busyDelayBuildings.get(DelayRefinery.IDENT)
                + DelayGameState.constructionsBeingBuilt.get(DelayRefinery.IDENT).size() < DelayGameState.geysers) {

            buildDelayBuilding(DelayRefinery.IDENT, DelayRefinery.buildTime, DelayRefinery.mineralCost, DelayRefinery.gasCost);
        }
    }

    private static void buildDelayFactory() {

        /**
         * If there are enough resources and the dependency of the DelayFactory is met, it can be built.
         */
        if (DelayGameState.freeDelayBuildings.get(DelayBarracks.IDENT) > 0 || DelayGameState.busyDelayBuildings.get(DelayBarracks.IDENT) > 0) {
            if (DelayGameState.minerals >= DelayFactory.mineralCost && DelayGameState.gas >= DelayFactory.gasCost) {
                buildDelayBuilding(DelayFactory.IDENT, DelayFactory.buildTime, DelayFactory.mineralCost, DelayFactory.gasCost);
            }
        }

        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (DelayGameState.constructionsBeingBuilt.get(DelayBarracks.IDENT).size() == 0) {
            buildDelayBarracks();
        }
    }

    /**
     * Builds a unit based on the specified attributes.
     *
     * @param unit - the identifier of the unit
     * @param builtFrom - the building that builds the unit
     * @param buildTime - the time in seconds it takes to build the unit
     * @param mineralCost - the mineral cost of the unit
     * @param gasCost - the gas cost of the unit
     */
    private static void buildDelayUnit(String unit, String builtFrom, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = DelayGameState.constructionsBeingBuilt.get(unit);
        durations.add(buildTime);
        DelayGameState.constructionsBeingBuilt.put(unit, durations);
        DelayGameState.updateDelayBuildings(builtFrom, "start");
        DelayGameState.gas -= gasCost;
        DelayGameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision("start building " + unit + " (finish at " + (DelayGameState.time + buildTime) + ")"));
    }

    /**
     * Builds a building based on the specified attributes.
     *
     * @param building - the identifier of the building
     * @param buildTime - the time in seconds it takes to build the building
     * @param mineralCost - the mineralcost of the building
     * @param gasCost - the gas cost of the building
     */
    private static void buildDelayBuilding(String building, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = DelayGameState.constructionsBeingBuilt.get(building);
        durations.add(buildTime);
        DelayGameState.constructionsBeingBuilt.put(building, durations);
        DelayGameState.gas -= gasCost;
        DelayGameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision("build " + building + " (finish at " + (DelayGameState.time + buildTime) + ")"));
    }

    /**
     * Reassigns workers between gas, minerals, and free workers.
     *
     * @param initialState - the initial state of the worker
     * @param finalState - the final state of the worker
     */
    private static void reassignDelayWorker (String initialState, String finalState){
        DelayGameState.workers.put(initialState, DelayGameState.workers.get(initialState) - 1);
        DelayGameState.workers.put(finalState, DelayGameState.workers.get(finalState) + 1);

        // reassigning workers not included in build order
        decisionsMade.add(formatDecision("reassign " + DelayWorker.IDENT + " from " + initialState + " to " + finalState));
    }

    /**
     * Formats the output elements with empty spaces for readability.
     *
     * https://stackoverflow.com/questions/13475388/generate-fixed-length-strings-filled-with-whitespaces
     */
    public static String formatDecision(String decision) {

        String output = String.format("%-5.5s", DelayGameState.time)
                + String.format("%-45.45s", decision)
                + String.format("%-12.12s", " gas: " + Math.round(DelayGameState.gas))
                + String.format("%-17.17s", " minerals: " + Math.round(DelayGameState.minerals));

        // number of units when each action is performed
        for (Map.Entry<String, Integer> entry: DelayGameState.units.entrySet()) {
            if (DelayGameState.goal.get(entry.getKey()) != 0) {
                output += String.format("%-13.13s", entry.getKey() + "s: " + entry.getValue());
            }
        }

        return output;
    }
}