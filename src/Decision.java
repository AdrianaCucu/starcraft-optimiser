import constructions.buildings.*;
import constructions.units.*;

import java.util.*;

// CHECK THE TIME LEFT BEFORE A BUILDING IS FREE WHEN IT TRIES TO BUILD THAT BUILDING AGAIN!!!!!

public class Decision {

    private static Random random = new Random();

    private static boolean decisionMade;

    // the decisions in order (for printing)
    public static ArrayList<String> decisionsMade = new ArrayList<>();

    // names of the units and indices of the decisions that are valuable for the goal
    public static HashMap<String, Integer> possibleDecisions = new HashMap<>();

    public static void makeDecision() {

        // updates the resources and checks if the goal has been met
        GameState.updateGameState();

        if (!Goal.goalAchieved) {

            decisionMade = true;

            while (decisionMade) {

                decisionMade = false;

                // removes decision if the particular unit has achieved the required number
                Iterator it = possibleDecisions.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry)it.next();
                    if (GameState.goal.get(entry.getKey()) 
                        == GameState.units.get(entry.getKey()) + GameState.constructionsBeingBuilt.get(entry.getKey()).size()) {
                            it.remove();
                        }
                }

                // to choose a random index from the possible decisions
                Object[] indices = possibleDecisions.values().toArray();
                if (indices.length != 0) {
                    Integer index =  (Integer)indices[random.nextInt(indices.length)];

                    updateState(index);
                }
            }

            // updates the game time
            GameState.time += 1;
        }
    }

    public static void updateState(int index) {

        switch (index) {

            // "build marine"
            case Marine.INDEX: {

                buildMarine();

                if (!decisionMade) {

                    // first 3 cases are relevant to a marine
                    randomDecision(3);
                } 
                break;
            }

            // "build hellion"
            case Hellion.INDEX: {

                buildHellion();

                if (!decisionMade) {

                    if (GameState.freeBuildings.get(Factory.IDENT) == 0) {

                        // need gas to build a factory
                        randomDecision(5);
                    } else {

                        // first 3 cases are relevant to a hellion
                        randomDecision(3);
                    }

                }
                break;
            }

            // "build medivac"
            case Medivac.INDEX: {

                buildMedivac();

                if (!decisionMade) {

                    // first 5 cases are relevant to a medivac
                    randomDecision(5);
                }
                break;
            }

            // "build viking"
            case Viking.INDEX: {

                buildViking();

                if (!decisionMade) {

                    // first 5 cases are relevant to a viking
                    randomDecision(5);
                }
                break;
            }
        }
    }
        

    private static void randomDecision(int number) {

            int i;
        
            if (GameState.workers.get(Worker.FREE) > 0) {

                i = random.nextInt(2);

                switch (i) {

                    case 0: {
                        if (number == 5 && GameState.workers.get(Worker.FREE) > 0 
                            && GameState.workers.get(Worker.GAS) < GameState.freeBuildings.get(Refinery.IDENT) * 3) {
                            reassignWorker(Worker.FREE, Worker.GAS);
                        }
                    }

                    case 1: {
                        if (GameState.workers.get(Worker.FREE) > 0 && GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {
                            reassignWorker(Worker.FREE, Worker.MINERALS);
                        }
                    }
                }
            }

            i = random.nextInt(number);

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
                        buildWorker();
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

        //checks if a free barracks exists to build a marine
        if (GameState.freeBuildings.get(Barracks.IDENT) > 0) {
            if (GameState.minerals >= Marine.mineralCost && GameState.gas >= Marine.gasCost) {
                buildUnit(Marine.IDENT, Marine.builtFrom, Marine.buildTime, Marine.mineralCost, Marine.gasCost);
                decisionMade = true;
            }
        }

        //if a free barracks does not exist, checks if it is in the process of being built or busy
        //if not, tries to build a barracks
        else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0
            || GameState.busyBuildings.get(Barracks.IDENT) == 0) {
            buildBarracks();
        }
    }

    private static void buildHellion() {

        //checks if a free factory exists to build a hellion
        if (GameState.freeBuildings.get(Factory.IDENT) > 0) {
            if (GameState.minerals >= Hellion.mineralCost && GameState.gas >= Hellion.gasCost) {
                buildUnit(Hellion.IDENT, Hellion.builtFrom, Hellion.buildTime, Hellion.mineralCost, Hellion.gasCost);
            }
        } 
            
        //if a free factory does not exist, checks if it is in the process of being built or busy
        //if not, tries to build a factory
        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0 
            || GameState.busyBuildings.get(Factory.IDENT) == 0) {
            buildFactory();
        }
    }

    private static void buildMedivac() {

         //checks if a free starport exists to build a medivac
         if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
            if (GameState.minerals >= Medivac.mineralCost && GameState.gas >= Medivac.gasCost) {
                buildUnit(Medivac.IDENT, Medivac.builtFrom, Medivac.buildTime, Medivac.mineralCost, Medivac.gasCost);
            }
        } 

        //if a free starport does not exist, checks if it is in the process of being built or busy
        //if not, tries to build a starport
        else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
            || GameState.busyBuildings.get(Starport.IDENT) == 0) {
                buildStarport();
        }
    }

    private static void buildViking() {

        //checks if a free starport exists to build a viking
        if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
            if (GameState.minerals >= Viking.mineralCost && GameState.gas >= Viking.gasCost) {
                buildUnit(Viking.IDENT, Viking.builtFrom, Viking.buildTime, Viking.mineralCost, Viking.gasCost);
            }
        } 

        //if a free starport does not exist, checks if it is in the process of being built or busy
        //if not, tries to build a starport
        else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
            || GameState.busyBuildings.get(Starport.IDENT) == 0) {
            buildStarport();
        }
    }

    private static void buildWorker() {

        //checks if a free command center exists to build a worker
        if (GameState.freeBuildings.get(CommandCenter.IDENT) > 0) {
            if (GameState.minerals >= Worker.mineralCost && GameState.gas >= Worker.gasCost) {
                buildUnit(Worker.IDENT, Worker.builtFrom, Worker.buildTime, Worker.mineralCost, Worker.gasCost);
            }
        } 
    }

    private static void buildBarracks() {

         //if a supply depot exists, can build a barracks
         if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
            if (GameState.minerals >= Barracks.mineralCost && GameState.gas >= Barracks.gasCost) {
                buildBuilding(Barracks.IDENT, Barracks.buildTime, Barracks.mineralCost, Barracks.gasCost);
            }
        }
        
        //if a supply depot does not exist, checks if it is in the porcess of being built
        //if not, tries to build one
        else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
            buildSupplyDepot();
        }
    }

    private static void buildCommandCenter() {

        //max 3 command centers for now
        if (GameState.minerals >= CommandCenter.mineralCost && GameState.gas >= CommandCenter.gasCost
        && GameState.freeBuildings.get(CommandCenter.IDENT)
            + GameState.busyBuildings.get(CommandCenter.IDENT)
            + GameState.constructionsBeingBuilt.get(CommandCenter.IDENT).size() < 3) {

            buildBuilding(CommandCenter.IDENT, CommandCenter.buildTime, CommandCenter.mineralCost, CommandCenter.gasCost);
        }
    }

    private static void buildStarport() {

         //if a factory exists, can build a starport
         if (GameState.freeBuildings.get(Factory.IDENT) > 0 || GameState.busyBuildings.get(Factory.IDENT) > 0) {
            if (GameState.minerals >= Starport.mineralCost && GameState.gas >= Starport.gasCost) {
                buildBuilding(Starport.IDENT, Starport.buildTime, Starport.mineralCost, Starport.gasCost);
            }
        }
        
        //if a factory does not exist, checks if it is in the process of being built
        //if not, tries to build a factory
        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0) {
            buildFactory();
        }
    }

    private static void buildSupplyDepot() {

        if (GameState.minerals >= SupplyDepot.mineralCost && GameState.gas >= SupplyDepot.gasCost) {
            buildBuilding(SupplyDepot.IDENT, SupplyDepot.buildTime, SupplyDepot.mineralCost, SupplyDepot.gasCost);
        }
    }

    private static void buildRefinery() {

        //max number of refineries is number of geysers
        if (GameState.minerals >= Refinery.mineralCost && GameState.gas >= Refinery.gasCost
            && GameState.freeBuildings.get(Refinery.IDENT)
            + GameState.busyBuildings.get(Refinery.IDENT)
            + GameState.constructionsBeingBuilt.get(Refinery.IDENT).size() < GameState.geysers) {

             buildBuilding(Refinery.IDENT, Refinery.buildTime, Refinery.mineralCost, Refinery.gasCost);
        }
    }

    private static void buildFactory() {

         //if a barracks exists, can build a factory
         if (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0) {
            if (GameState.minerals >= Factory.mineralCost && GameState.gas >= Factory.gasCost) {
                buildBuilding(Factory.IDENT, Factory.buildTime, Factory.mineralCost, Factory.gasCost);
            }
        } 
                
        //if a barracks does not exist, checks if it is in the porcess of being built
        //if not, tries to build one
        else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0) {
            buildBarracks();
        }
    }


    private static void buildUnit(String unit, String builtFrom, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(unit);
        durations.add(buildTime);
        GameState.constructionsBeingBuilt.put(unit, durations);
        GameState.updateBuildings(builtFrom, "start");
        GameState.gas -= gasCost;
        GameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision("start building " + unit));
        decisionMade = true;
    }

    private static void buildBuilding(String building, int buildTime, double mineralCost, double gasCost) {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(building);
        durations.add(buildTime);
        GameState.constructionsBeingBuilt.put(building, durations);
        GameState.gas -= gasCost;
        GameState.minerals -= mineralCost;
        decisionsMade.add(formatDecision("start building " + building));
        decisionMade = true;
    }

    /**
     * Reassigns workers between gas, minerals, and free workers.
     *
     * @param initialState - the initial "job" of the worker
     * @param finalState - the final "job" of the worker
     */
    private static void reassignWorker (String initialState, String finalState){
        GameState.workers.put(initialState, GameState.workers.get(initialState) - 1);
        GameState.workers.put(finalState, GameState.workers.get(finalState) + 1);

        // reassigning workers not included in build order
        decisionsMade.add(formatDecision("reassign " + Worker.IDENT + " from " + initialState + " to " + finalState));
        decisionMade = true;
    }

    /**
     * Formats the output elements with empty spaces for readability.
     * 
     * https://stackoverflow.com/questions/13475388/generate-fixed-length-strings-filled-with-whitespaces
     */
    public static String formatDecision(String decision) {

        String output =  "\n" + String.format("%-5.5s", GameState.time)
                        + String.format("%-45.45s", decision) 
                        + String.format("%-12.12s", " gas: " + Math.round(GameState.gas))
                        + String.format("%-17.17s", " minerals: " + Math.round(GameState.minerals));

        // number of units when each action is performed
        for (Map.Entry<String, Integer> entry: GameState.units.entrySet()) {
            if (GameState.goal.get(entry.getKey()) != 0) {
                output += String.format("%-13.13s", entry.getKey() + "s: " + entry.getValue());
            }
        }

        return output;
    }
}