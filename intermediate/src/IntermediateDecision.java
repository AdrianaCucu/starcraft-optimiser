import constructions.buildings.*;
import constructions.units.*;

import java.util.*;

public class IntermediateDecision {

    private static Random random = new Random();

    /**
     * Used to make multiple decisions every second to increase efficiency.
     */
    private static boolean decisionMade;

    /**
     * Stores the build orders as Strings for printing.
     */
    public static ArrayList<String> decisionsMade = new ArrayList<>();

    /**
     * Stores the names and indices of the units that are valuable for the goal.
     */
    public static HashMap<String, Integer> possibleIntermediateDecisions = new HashMap<>();


    /**
     * Chooses the decisions to make until the goal is met.
     */
    public static void makeIntermediateDecision() {

        /**
         * Updates the game state (without the time) at every second of the game.
         * Reasons for doing this before making a decision:
         *     - more resources when trying to build something
         *     - the constructions that have finished building are updated, and they can be used
         */
        IntermediateGameState.updateIntermediateGameState();

        /**
         * Checks whether the goal has already been met before attempting to make a decision.
         */
        if (!IntermediateGoal.goalAchieved) {

            decisionMade = true;

            /**
             * Tries to make as many decisions as possible every second.
             * The loop keeps repeating until a decision is not made in one repetition.
             */
            while (decisionMade && !IntermediateGoal.goalAchieved) {

                decisionMade = false;

                /**
                 * Removes the unit from the possible decisions if the goal for that particular unit has been met.
                 * In this way, it does not build more units than required and the goal is met faster.
                 * 
                 * https://www.geeksforgeeks.org/remove-an-entry-using-key-from-hashmap-while-iterating-over-it/
                 */
                possibleIntermediateDecisions.entrySet().removeIf(entry -> (IntermediateGameState.goal.get(entry.getKey()) ==
                    IntermediateGameState.units.get(entry.getKey()) + IntermediateGameState.constructionsBeingBuilt.get(entry.getKey()).size()));

                /**
                 * Chooses a random index from the possible decisions.
                 */
                Object[] indices = possibleIntermediateDecisions.values().toArray();
                if (indices.length != 0) {
                    Integer index =  (Integer)indices[random.nextInt(indices.length)];

                    /**
                     * Makes a decision based on one of the possible decisions.
                     */
                    makeIntermediateDecision(index);
                }
            }

            /**
             * After it finishes making decisions, it updates the game time.
             */
            IntermediateGameState.time += 1;
        }
    }

    /**
     * Makes a decision based on the possible decisions at the current game time.
     */
    public static void makeIntermediateDecision(int index) {

        /**
         * Based on the index, tries to build a specific kind of unit.
         */
        switch (index) {

            // "build marine"
            case IntermediateMarine.INDEX: {

                /**
                 * Tries to build a IntermediateMarine, or (if not possible) a building relevant to IntermediateMarines.
                 */
                buildIntermediateMarine();

                /**
                 * If it is not possible to build a IntermediateMarine, makes a decision to improve the resources.
                 */
                if (!decisionMade) {

                    /**
                     * Gas is not needed for IntermediateMarines, but if the goal may contain other units.
                     */
                    if (possibleIntermediateDecisions.containsKey(IntermediateHellion.IDENT)
                        || possibleIntermediateDecisions.containsKey(IntermediateMedivac.IDENT)
                        || possibleIntermediateDecisions.containsKey(IntermediateViking.IDENT)) {
                        randomIntermediateDecision(5);
                    } else {
                        
                        /**
                         * If the goal only contains IntermediateMarines, makes a decision for minerals only.
                         * The method is built such that the first 3 cases in the switch statement are relevant to minerals.
                         */
                        randomIntermediateDecision(3);
                    }
                } 
                break;
            }

            // "build hellion"
            case IntermediateHellion.INDEX: {

                /**
                 * Tries to build a IntermediateHellion, or (if not possible) a building relevant to IntermediateHellions.
                 */
                buildIntermediateHellion();

                /**
                 * If it is not possible to build a IntermediateHellion, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomIntermediateDecision(5);
                break;
            }

            // "build medivac"
            case IntermediateMedivac.INDEX: {

                /**
                 * Tries to build a IntermediateMedivac, or (if not possible) a building relevant to IntermediateMedivacs.
                 */
                buildIntermediateMedivac();

                /**
                 * If it is not possible to build a IntermediateMedivac, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomIntermediateDecision(5);
                break;
            }

            // "build viking"
            case IntermediateViking.INDEX: {

                /**
                 * Tries to build a Viking, or (if not possible) a building relevant to Vikings.
                 */
                buildIntermediateViking();

                /**
                 * If it is not possible to build a Viking, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomIntermediateDecision(5);
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
    private static void randomIntermediateDecision(int number) {

        /**
         * Reassigning free workers to geysers or mineral patches is attempted whenever the method is called.
         * This is done to further optimise resource collection, because ideally we would not have free workers.
         */
        if(number == 5 && IntermediateGameState.workers.get(IntermediateWorker.FREE) > 0
            && IntermediateGameState.workers.get(IntermediateWorker.GAS) < IntermediateGameState.freeIntermediateBuildings.get(IntermediateRefinery.IDENT) * 3
            && IntermediateGameState.gas <= IntermediateGameState.minerals) {
            reassignIntermediateWorker(IntermediateWorker.FREE, IntermediateWorker.GAS);
        } 
            
        else if (IntermediateGameState.workers.get(IntermediateWorker.FREE) > 0
            && IntermediateGameState.workers.get(IntermediateWorker.MINERALS) < IntermediateGameState.patches * 3) {
            reassignIntermediateWorker(IntermediateWorker.FREE, IntermediateWorker.MINERALS);
        }

        int i = random.nextInt(number);

        /**
         * A random action is attempted based on the resources needed.
         */
        switch (i) {

            // "reassign worker from gas to minerals"
            case 0: {
                if (IntermediateGameState.workers.get(IntermediateWorker.GAS) > 0 && IntermediateGameState.workers.get(IntermediateWorker.MINERALS) < IntermediateGameState.patches * 3) {
                    reassignIntermediateWorker(IntermediateWorker.GAS, IntermediateWorker.MINERALS);
                }
                break;
            }

            // "build worker"
            case 1: {
                if (IntermediateGameState.workers.get(IntermediateWorker.FREE) + IntermediateGameState.workers.get(IntermediateWorker.MINERALS) < IntermediateGameState.patches * 3) {

                    boolean build = true;

                    /**
                     * Before attempting to build a IntermediateWorker, checks if any unit will be completed soon.
                     * That is because a more valuable decision could be made after the unit is finished, so the resources are preserved.
                     */
                    for (String unit: possibleIntermediateDecisions.keySet()) {
                        for (Integer seconds: IntermediateGameState.constructionsBeingBuilt.get(unit)) {
                            if (seconds < IntermediateWorker.buildTime) {
                                build = false;
                            }
                        }
                    }

                    if (build) {
                        buildIntermediateWorker();
                    }
                }
                break;
            }

            // "build command center"
            case 2: {
                buildIntermediateCommandCenter();
                break;
            }

            // "reassign worker from minerals to gas"
            case 3: {
                if (IntermediateGameState.workers.get(IntermediateWorker.MINERALS) > 0
                    && IntermediateGameState.workers.get(IntermediateWorker.GAS) < IntermediateGameState.freeIntermediateBuildings.get(IntermediateRefinery.IDENT) * 3) {
                    reassignIntermediateWorker(IntermediateWorker.MINERALS, IntermediateWorker.GAS);
                }
                break;
            }

            // "build refinery"
            case 4: {
                buildIntermediateRefinery();
                break;
            }
        }
    }

    private static void buildIntermediateMarine() {

        /**
         * If a free IntermediateBarracks exists and there are enough resources, a IntermediateMarine can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateBarracks.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateMarine.mineralCost && IntermediateGameState.gas >= IntermediateMarine.gasCost) {
                buildIntermediateUnit(IntermediateMarine.IDENT, IntermediateMarine.builtFrom, IntermediateMarine.buildTime, IntermediateMarine.mineralCost, IntermediateMarine.gasCost);
            }
        }

        /**
         * If a free IntermediateBarracks does not exist, tries to build one.
         * This happens when there is no IntermediateBarracks that is busy or that is being built.
         */
        else if ((IntermediateGameState.constructionsBeingBuilt.get(IntermediateBarracks.IDENT).size() == 0
            || IntermediateGameState.busyIntermediateBuildings.get(IntermediateBarracks.IDENT) == 0)) {
            buildIntermediateBarracks();
        }
    }

    private static void buildIntermediateHellion() {

        /**
         * If a free IntermediateFactory exists and there are enough resources, a IntermediateHellion can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateFactory.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateHellion.mineralCost && IntermediateGameState.gas >= IntermediateHellion.gasCost) {
                buildIntermediateUnit(IntermediateHellion.IDENT, IntermediateHellion.builtFrom, IntermediateHellion.buildTime, IntermediateHellion.mineralCost, IntermediateHellion.gasCost);
            }
        } 
            
        /**
         * If a free IntermediateFactory does not exist, tries to build one.
         * This happens when there is no IntermediateFactory that is busy or that is being built.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateFactory.IDENT).size() == 0
            || IntermediateGameState.busyIntermediateBuildings.get(IntermediateFactory.IDENT) == 0) {
            buildIntermediateFactory();
        }
    }

    private static void buildIntermediateMedivac() {

        /**
         * If a free IntermediateStarport exists and there are enough resources, a IntermediateMedivac can be built.
         */
         if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateStarport.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateMedivac.mineralCost && IntermediateGameState.gas >= IntermediateMedivac.gasCost) {
                buildIntermediateUnit(IntermediateMedivac.IDENT, IntermediateMedivac.builtFrom, IntermediateMedivac.buildTime, IntermediateMedivac.mineralCost, IntermediateMedivac.gasCost);
            }
        } 

        /**
         * If a free IntermediateStarport does not exist, tries to build one.
         * This happens when there is no IntermediateStarport that is busy or that is being built.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateStarport.IDENT).size() == 0
            || IntermediateGameState.busyIntermediateBuildings.get(IntermediateStarport.IDENT) == 0) {
                buildIntermediateStarport();
        }
    }

    private static void buildIntermediateViking() {

        /**
         * If a free IntermediateStarport exists and there are enough resources, a Viking can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateStarport.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateViking.mineralCost && IntermediateGameState.gas >= IntermediateViking.gasCost) {
                buildIntermediateUnit(IntermediateViking.IDENT, IntermediateViking.builtFrom, IntermediateViking.buildTime, IntermediateViking.mineralCost, IntermediateViking.gasCost);
            }
        } 

        /**
         * If a free IntermediateStarport does not exist, tries to build one.
         * This happens when there is no IntermediateStarport that is busy or that is being built.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateStarport.IDENT).size() == 0
        || IntermediateGameState.busyIntermediateBuildings.get(IntermediateStarport.IDENT) == 0) {
            buildIntermediateStarport();
        }
    }

    private static void buildIntermediateWorker() {

        /**
         * If a free Command Center exists and there are enough resources, a IntermediateWorker can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateCommandCenter.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateWorker.mineralCost && IntermediateGameState.gas >= IntermediateWorker.gasCost) {
                buildIntermediateUnit(IntermediateWorker.IDENT, IntermediateWorker.builtFrom, IntermediateWorker.buildTime, IntermediateWorker.mineralCost, IntermediateWorker.gasCost);
            }
        } 
    }

    private static void buildIntermediateBarracks() {

        /**
         * If there are enough resources and the dependency of the IntermediateBarracks is met, it can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateSupplyDepot.IDENT) > 0 || IntermediateGameState.busyIntermediateBuildings.get(IntermediateSupplyDepot.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateBarracks.mineralCost && IntermediateGameState.gas >= IntermediateBarracks.gasCost) {
                buildIntermediateBuilding(IntermediateBarracks.IDENT, IntermediateBarracks.buildTime, IntermediateBarracks.mineralCost, IntermediateBarracks.gasCost);
            }
        }
        
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateSupplyDepot.IDENT).size() == 0) {
            buildIntermediateSupplyDepot();
        }
    }

    private static void buildIntermediateCommandCenter() {

        /**
         * If there are enough resources for a Command Center, it can be built.
         * A maximum of 3 Command Centers can be build, otherwise efficiency decreases.
         */
        if (IntermediateGameState.minerals >= IntermediateCommandCenter.mineralCost && IntermediateGameState.gas >= IntermediateCommandCenter.gasCost
            && IntermediateGameState.freeIntermediateBuildings.get(IntermediateCommandCenter.IDENT)
            + IntermediateGameState.busyIntermediateBuildings.get(IntermediateCommandCenter.IDENT)
            + IntermediateGameState.constructionsBeingBuilt.get(IntermediateCommandCenter.IDENT).size() < 3) {

            buildIntermediateBuilding(IntermediateCommandCenter.IDENT, IntermediateCommandCenter.buildTime, IntermediateCommandCenter.mineralCost, IntermediateCommandCenter.gasCost);
        }
    }

    private static void buildIntermediateStarport() {

        /**
         * If there are enough resources and the dependency of the IntermediateStarport is met, it can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateFactory.IDENT) > 0 || IntermediateGameState.busyIntermediateBuildings.get(IntermediateFactory.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateStarport.mineralCost && IntermediateGameState.gas >= IntermediateStarport.gasCost) {
                buildIntermediateBuilding(IntermediateStarport.IDENT, IntermediateStarport.buildTime, IntermediateStarport.mineralCost, IntermediateStarport.gasCost);
            }
        }
        
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateFactory.IDENT).size() == 0) {
            buildIntermediateFactory();
        }
    }

    private static void buildIntermediateSupplyDepot() {

        /**
         * If there are enough resources for a Supply Depot, it can be built.
         */
        if (IntermediateGameState.minerals >= IntermediateSupplyDepot.mineralCost && IntermediateGameState.gas >= IntermediateSupplyDepot.gasCost) {
            buildIntermediateBuilding(IntermediateSupplyDepot.IDENT, IntermediateSupplyDepot.buildTime, IntermediateSupplyDepot.mineralCost, IntermediateSupplyDepot.gasCost);
        }
    }

    private static void buildIntermediateRefinery() {

        /**
         * If there are enough resources for a IntermediateRefinery, it can be built.
         * The maximum number of refineries that can be build is the number of geysers.
         */
        if (IntermediateGameState.minerals >= IntermediateRefinery.mineralCost && IntermediateGameState.gas >= IntermediateRefinery.gasCost
            && IntermediateGameState.freeIntermediateBuildings.get(IntermediateRefinery.IDENT)
            + IntermediateGameState.busyIntermediateBuildings.get(IntermediateRefinery.IDENT)
            + IntermediateGameState.constructionsBeingBuilt.get(IntermediateRefinery.IDENT).size() < IntermediateGameState.geysers) {

             buildIntermediateBuilding(IntermediateRefinery.IDENT, IntermediateRefinery.buildTime, IntermediateRefinery.mineralCost, IntermediateRefinery.gasCost);
        }
    }

    private static void buildIntermediateFactory() {

        /**
         * If there are enough resources and the dependency of the IntermediateFactory is met, it can be built.
         */
        if (IntermediateGameState.freeIntermediateBuildings.get(IntermediateBarracks.IDENT) > 0 || IntermediateGameState.busyIntermediateBuildings.get(IntermediateBarracks.IDENT) > 0) {
            if (IntermediateGameState.minerals >= IntermediateFactory.mineralCost && IntermediateGameState.gas >= IntermediateFactory.gasCost) {
                buildIntermediateBuilding(IntermediateFactory.IDENT, IntermediateFactory.buildTime, IntermediateFactory.mineralCost, IntermediateFactory.gasCost);
            }
        } 
                
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (IntermediateGameState.constructionsBeingBuilt.get(IntermediateBarracks.IDENT).size() == 0) {
            buildIntermediateBarracks();
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
    private static void buildIntermediateUnit(String unit, String builtFrom, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = IntermediateGameState.constructionsBeingBuilt.get(unit);
        durations.add(buildTime);
        IntermediateGameState.constructionsBeingBuilt.put(unit, durations);
        IntermediateGameState.updateIntermediateBuildings(builtFrom, "start");
        IntermediateGameState.gas -= gasCost;
        IntermediateGameState.minerals -= mineralCost;
        decisionsMade.add(formatIntermediateDecision("start building " + unit + " (finish at " + (IntermediateGameState.time + buildTime) + ")"));
        decisionMade = true;
    }

    /**
     * Builds a building based on the specified attributes.
     * 
     * @param building - the identifier of the building
     * @param buildTime - the time in seconds it takes to build the building
     * @param mineralCost - the mineralcost of the building
     * @param gasCost - the gas cost of the building
     */
    private static void buildIntermediateBuilding(String building, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = IntermediateGameState.constructionsBeingBuilt.get(building);
        durations.add(buildTime);
        IntermediateGameState.constructionsBeingBuilt.put(building, durations);
        IntermediateGameState.gas -= gasCost;
        IntermediateGameState.minerals -= mineralCost;
        decisionsMade.add(formatIntermediateDecision("start building " + building + " (finish at " + (IntermediateGameState.time + buildTime) + ")"));
        decisionMade = true;
    }

    /**
     * Reassigns workers between gas, minerals, and free workers.
     *
     * @param initialState - the initial state of the worker
     * @param finalState - the final state of the worker
     */
    private static void reassignIntermediateWorker (String initialState, String finalState){
        IntermediateGameState.workers.put(initialState, IntermediateGameState.workers.get(initialState) - 1);
        IntermediateGameState.workers.put(finalState, IntermediateGameState.workers.get(finalState) + 1);

        // reassigning workers not included in build order
        // decisionsMade.add(formatIntermediateDecision("reassign " + IntermediateWorker.IDENT + " from " + initialState + " to " + finalState));
        decisionMade = true;
    }

    /**
     * Formats the output elements with empty spaces for readability.
     * 
     * https://stackoverflow.com/questions/13475388/generate-fixed-length-strings-filled-with-whitespaces
     */
    public static String formatIntermediateDecision(String decision) {

        String output = String.format("%-5.5s", IntermediateGameState.time)
                        + String.format("%-45.45s", decision) 
                        + String.format("%-12.12s", " gas: " + Math.round(IntermediateGameState.gas))
                        + String.format("%-17.17s", " minerals: " + Math.round(IntermediateGameState.minerals));

        // number of units when each action is performed
        for (Map.Entry<String, Integer> entry: IntermediateGameState.units.entrySet()) {
            if (IntermediateGameState.goal.get(entry.getKey()) != 0) {
                output += String.format("%-13.13s", entry.getKey() + "s: " + entry.getValue());
            }
        }

        return output;
    }
}