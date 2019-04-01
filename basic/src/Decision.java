import constructions.buildings.*;
import constructions.units.*;

import java.util.*;

public class Decision {

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
        GameState.updateGameState();

        /**
         * Checks whether the goal has already been met before attempting to make a decision.
         */
        if (!Goal.goalAchieved) {

            decisionMade = true;

            /**
             * Tries to make as many decisions as possible every second.
             * The loop keeps repeating until a decision is not made in one repetition.
             */
            while (decisionMade && !Goal.goalAchieved) {

                decisionMade = false;

                /**
                 * Removes the unit from the possible decisions if the goal for that particular unit has been met.
                 * In this way, it does not build more units than required and the goal is met faster.
                 * 
                 * https://www.geeksforgeeks.org/remove-an-entry-using-key-from-hashmap-while-iterating-over-it/
                 */
                possibleDecisions.entrySet().removeIf(entry -> (GameState.goal.get(entry.getKey()) == 
                    GameState.units.get(entry.getKey()) + GameState.constructionsBeingBuilt.get(entry.getKey()).size()));

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
             * After it finishes making decisions, it updates the game time.
             */
            GameState.time += 1;
        }
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
            case Marine.INDEX: {

                /**
                 * Tries to build a Marine, or (if not possible) a building relevant to Marines.
                 */
                buildMarine();

                /**
                 * If it is not possible to build a Marine, makes a decision to improve the resources.
                 */
                if (!decisionMade) {

                    /**
                     * Gas is not needed for Marines, but if the goal may contain other units. 
                     */
                    if (possibleDecisions.containsKey(Hellion.IDENT) 
                        || possibleDecisions.containsKey(Medivac.IDENT) 
                        || possibleDecisions.containsKey(Viking.IDENT)) {
                        randomDecision(5);
                    } else {
                        
                        /**
                         * If the goal only contains Marines, makes a decision for minerals only.
                         * The method is built such that the first 3 cases in the switch statement are relevant to minerals.
                         */
                        randomDecision(3);
                    }
                } 
                break;
            }

            // "build hellion"
            case Hellion.INDEX: {

                /**
                 * Tries to build a Hellion, or (if not possible) a building relevant to Hellions.
                 */
                buildHellion();

                /**
                 * If it is not possible to build a Hellion, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomDecision(5);
                break;
            }

            // "build medivac"
            case Medivac.INDEX: {

                /**
                 * Tries to build a Medivac, or (if not possible) a building relevant to Medivacs.
                 */
                buildMedivac();

                /**
                 * If it is not possible to build a Medivac, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomDecision(5);
                break;
            }

            // "build viking"
            case Viking.INDEX: {

                /**
                 * Tries to build a Viking, or (if not possible) a building relevant to Vikings.
                 */
                buildViking();

                /**
                 * If it is not possible to build a Viking, makes a decision to improve the resources.
                 */
                if (!decisionMade) randomDecision(5);
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
         * Reassigning free workers to geysers or mineral patches is attempted whenever the method is called.
         * This is done to further optimise resource collection, because ideally we would not have free workers.
         */
        if(number == 5 && GameState.workers.get(Worker.FREE) > 0 
            && GameState.workers.get(Worker.GAS) < GameState.freeBuildings.get(Refinery.IDENT) * 3
            && GameState.gas <= GameState.minerals) {
            reassignWorker(Worker.FREE, Worker.GAS);
        } 
            
        else if (GameState.workers.get(Worker.FREE) > 0 
            && GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {
            reassignWorker(Worker.FREE, Worker.MINERALS);
        }

        int i = random.nextInt(number);

        /**
         * A random action is attempted based on the resources needed.
         */
        switch (i) {

            // "reassign worker from gas to minerals"
            case 0: {
                if (GameState.workers.get(Worker.GAS) > 0 && GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {
                    reassignWorker(Worker.GAS, Worker.MINERALS);
                }
                break;
            }

            // "build worker"
            case 1: {
                if (GameState.workers.get(Worker.FREE) + GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {

                    boolean build = true;

                    /**
                     * Before attempting to build a Worker, checks if any unit will be completed soon.
                     * That is because a more valuable decision could be made after the unit is finished, so the resources are preserved.
                     */
                    for (String unit: possibleDecisions.keySet()) {
                        for (Integer seconds: GameState.constructionsBeingBuilt.get(unit)) {
                            if (seconds < Worker.BUILD_TIME) {
                                build = false;
                            }
                        }
                    }

                    if (build) {
                        buildWorker();
                    }
                }
                break;
            }

            // "build command center"
            case 2: {
                buildCommandCenter();
                break;
            }

            // "reassign worker from minerals to gas"
            case 3: {
                if (GameState.workers.get(Worker.MINERALS) > 0
                    && GameState.workers.get(Worker.GAS) < GameState.freeBuildings.get(Refinery.IDENT) * 3) {
                    reassignWorker(Worker.MINERALS, Worker.GAS);
                }
                break;
            }

            // "build refinery"
            case 4: {
                buildRefinery();
                break;
            }
        }
    }

    private static void buildMarine() {

        /**
         * If a free Barracks exists and there are enough resources, a Marine can be built.
         */
        if (GameState.freeBuildings.get(Barracks.IDENT) > 0) {
            if (GameState.minerals >= Marine.MINERAL_COST && GameState.gas >= Marine.GAS_COST) {
                buildUnit(Marine.IDENT, Marine.BUILT_FROM, Marine.BUILD_TIME, Marine.MINERAL_COST, Marine.GAS_COST);
            }
        }

        /**
         * If a free Barracks does not exist, tries to build one.
         * This happens when there is no Barracks that is busy or that is being built.
         */
        else if ((GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0
            || GameState.busyBuildings.get(Barracks.IDENT) == 0)) {
            buildBarracks();
        }
    }

    private static void buildHellion() {

        /**
         * If a free Factory exists and there are enough resources, a Hellion can be built.
         */
        if (GameState.freeBuildings.get(Factory.IDENT) > 0) {
            if (GameState.minerals >= Hellion.MINERAL_COST && GameState.gas >= Hellion.GAS_COST) {
                buildUnit(Hellion.IDENT, Hellion.BUILT_FROM, Hellion.BUILD_TIME, Hellion.MINERAL_COST, Hellion.GAS_COST);
            }
        } 
            
        /**
         * If a free Factory does not exist, tries to build one.
         * This happens when there is no Factory that is busy or that is being built.
         */
        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0 
            || GameState.busyBuildings.get(Factory.IDENT) == 0) {
            buildFactory();
        }
    }

    private static void buildMedivac() {

        /**
         * If a free Starport exists and there are enough resources, a Medivac can be built.
         */
         if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
            if (GameState.minerals >= Medivac.MINERAL_COST && GameState.gas >= Medivac.GAS_COST) {
                buildUnit(Medivac.IDENT, Medivac.BUILT_FROM, Medivac.BUILD_TIME, Medivac.MINERAL_COST, Medivac.GAS_COST);
            }
        } 

        /**
         * If a free Starport does not exist, tries to build one.
         * This happens when there is no Starport that is busy or that is being built.
         */
        else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
            || GameState.busyBuildings.get(Starport.IDENT) == 0) {
                buildStarport();
        }
    }

    private static void buildViking() {

        /**
         * If a free Starport exists and there are enough resources, a Viking can be built.
         */
        if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
            if (GameState.minerals >= Viking.MINERAL_COST && GameState.gas >= Viking.GAS_COST) {
                buildUnit(Viking.IDENT, Viking.BUILT_FROM, Viking.BUILD_TIME, Viking.MINERAL_COST, Viking.GAS_COST);
            }
        } 

        /**
         * If a free Starport does not exist, tries to build one.
         * This happens when there is no Starport that is busy or that is being built.
         */
        else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
        || GameState.busyBuildings.get(Starport.IDENT) == 0) {
            buildStarport();
        }
    }

    private static void buildWorker() {

        /**
         * If a free Command Center exists and there are enough resources, a Worker can be built.
         */
        if (GameState.freeBuildings.get(CommandCenter.IDENT) > 0) {
            if (GameState.minerals >= Worker.MINERAL_COST && GameState.gas >= Worker.GAS_COST) {
                buildUnit(Worker.IDENT, Worker.BUILT_FROM, Worker.BUILD_TIME, Worker.MINERAL_COST, Worker.GAS_COST);
            }
        } 
    }

    private static void buildBarracks() {

        /**
         * If there are enough resources and the dependency of the Barracks is met, it can be built.
         */
        if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
            if (GameState.minerals >= Barracks.MINERAL_COST && GameState.gas >= Barracks.GAS_COST) {
                buildBuilding(Barracks.IDENT, Barracks.BUILD_TIME, Barracks.MINERAL_COST, Barracks.GAS_COST);
            }
        }
        
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
            buildSupplyDepot();
        }
    }

    private static void buildCommandCenter() {

        /**
         * If there are enough resources for a Command Center, it can be built.
         * A maximum of 3 Command Centers can be build, otherwise efficiency decreases.
         */
        if (GameState.minerals >= CommandCenter.MINERAL_COST && GameState.gas >= CommandCenter.GAS_COST
            && GameState.freeBuildings.get(CommandCenter.IDENT)
            + GameState.busyBuildings.get(CommandCenter.IDENT)
            + GameState.constructionsBeingBuilt.get(CommandCenter.IDENT).size() < 3) {

            buildBuilding(CommandCenter.IDENT, CommandCenter.BUILD_TIME, CommandCenter.MINERAL_COST, CommandCenter.GAS_COST);
        }
    }

    private static void buildStarport() {

        /**
         * If there are enough resources and the dependency of the Starport is met, it can be built.
         */
        if (GameState.freeBuildings.get(Factory.IDENT) > 0 || GameState.busyBuildings.get(Factory.IDENT) > 0) {
            if (GameState.minerals >= Starport.MINERAL_COST && GameState.gas >= Starport.GAS_COST) {
                buildBuilding(Starport.IDENT, Starport.BUILD_TIME, Starport.MINERAL_COST, Starport.GAS_COST);
            }
        }
        
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0) {
            buildFactory();
        }
    }

    private static void buildSupplyDepot() {

        /**
         * If there are enough resources for a Supply Depot, it can be built.
         */
        if (GameState.minerals >= SupplyDepot.MINERAL_COST && GameState.gas >= SupplyDepot.GAS_COST) {
            buildBuilding(SupplyDepot.IDENT, SupplyDepot.BUILD_TIME, SupplyDepot.MINERAL_COST, SupplyDepot.GAS_COST);
        }
    }

    private static void buildRefinery() {

        /**
         * If there are enough resources for a Refinery, it can be built.
         * The maximum number of refineries that can be build is the number of geysers.
         */
        if (GameState.minerals >= Refinery.MINERAL_COST && GameState.gas >= Refinery.GAS_COST
            && GameState.freeBuildings.get(Refinery.IDENT)
            + GameState.busyBuildings.get(Refinery.IDENT)
            + GameState.constructionsBeingBuilt.get(Refinery.IDENT).size() < GameState.geysers) {

             buildBuilding(Refinery.IDENT, Refinery.BUILD_TIME, Refinery.MINERAL_COST, Refinery.GAS_COST);
        }
    }

    private static void buildFactory() {

        /**
         * If there are enough resources and the dependency of the Factory is met, it can be built.
         */
        if (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0) {
            if (GameState.minerals >= Factory.MINERAL_COST && GameState.gas >= Factory.GAS_COST) {
                buildBuilding(Factory.IDENT, Factory.BUILD_TIME, Factory.MINERAL_COST, Factory.GAS_COST);
            }
        } 
                
        /**
         * If the dependency is not met, checks if it is in the process of being built.
         * If not, tries to build it.
         */
        else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0) {
            buildBarracks();
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
    private static void buildUnit(String unit, String builtFrom, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(unit);
        durations.add(buildTime);
        GameState.constructionsBeingBuilt.put(unit, durations);
        GameState.updateBuildings(builtFrom, "start");
        GameState.gas -= gasCost;
        GameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision(unit + " (finish at " + (formatTime(GameState.time + buildTime)) + ")"));
        decisionMade = true;

        /**
         * Tests that the unit is built properly.
         */
        // System.out.println("unit: " + unit);
        // System.out.println("minerals: " + GameState.minerals);
        // System.out.println("gas: " + GameState.gas);
    }

    /**
     * Builds a building based on the specified attributes.
     * 
     * @param building - the identifier of the building
     * @param buildTime - the time in seconds it takes to build the building
     * @param mineralCost - the mineralcost of the building
     * @param gasCost - the gas cost of the building
     */
    private static void buildBuilding(String building, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(building);
        durations.add(buildTime);
        GameState.constructionsBeingBuilt.put(building, durations);
        GameState.gas -= gasCost;
        GameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision(building + " (finish at " + (formatTime(GameState.time + buildTime)) + ")"));
        decisionMade = true;

        /**
         * Tests that the building is built properly.
         */
        // System.out.println("building: " + building);
        // System.out.println("minerals: " + GameState.minerals);
        // System.out.println("gas: " + GameState.gas);
    }

    /**
     * Reassigns workers between gas, minerals, and free workers.
     *
     * @param initialState - the initial state of the worker
     * @param finalState - the final state of the worker
     */
    private static void reassignWorker (String initialState, String finalState){
        GameState.workers.put(initialState, GameState.workers.get(initialState) - 1);
        GameState.workers.put(finalState, GameState.workers.get(finalState) + 1);

        // reassigning workers not included in build order
        // decisionsMade.add(formatDecision("reassign " + Worker.IDENT + " from " + initialState + " to " + finalState));
        decisionMade = true;
    }

    /**
     * Formats the time for printing.
     *
     * @param time - the time in seconds
     * @return - the formatted time as a String
     */
    public static String formatTime(int time) {

        String timestamp = "";
        int minutes = time / 60;
        int seconds = time % 60;

        if (minutes == 0) {
            timestamp += "0";
        } else {
            timestamp += minutes;
        }

        timestamp += ":";

        if (seconds < 10) {
            timestamp += "0";
            if (seconds == 0) {
                timestamp += "0";
            } else {
                timestamp += seconds;
            }

        } else {
            timestamp += seconds;
        }
        return timestamp;
    }

    /**
     * Formats the output elements with empty spaces for readability.
     * 
     * https://stackoverflow.com/questions/13475388/generate-fixed-length-strings-filled-with-whitespaces
     */
    public static String formatDecision(String decision) {

        String timestamp = formatTime(GameState.time);

        String output = String.format("%-32.32s", decision)
                        + String.format("%-6.6s", timestamp);

        return output;
    }
}