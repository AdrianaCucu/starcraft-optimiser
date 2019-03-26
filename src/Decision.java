import constructions.buildings.*;
import constructions.units.*;

import java.util.*;

public class Decision {

    public static final String BUILD = "build";

    // the decisions in order (for printing)
    public static ArrayList<String> decisionsMade = new ArrayList<>();

    public static void makeDecision() {

        // updates the resources and checks if the goal has been met
        GameState.updateGameState();

        if (!Goal.goalAchieved) {

            Random random = new Random();

            // to choose a random element from the possible decisions
            // 4 possible units for the goal
            int index = random.nextInt(4);

            updateState(index);

            // updates the game time
            GameState.time += 1;
        }
    }

    public static void updateState(int index) {

        switch (index) {

            // "build marine"
            case 0: {

                if (GameState.goal.get(Marine.IDENT) > GameState.units.get(Marine.IDENT)) {

                    //checks if a free barracks exists to build a marine
                    if (GameState.freeBuildings.get(Barracks.IDENT) > 0) {
                        if (GameState.minerals >= Marine.mineralCost && GameState.gas >= Marine.gasCost) {
                            buildMarine();
                            break;
                        }
                    }

                    //if a free barracks does not exist, checks if it is in the process of being built or busy
                    //if not, tries to build a barracks
                    else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0 
                        || GameState.busyBuildings.get(Barracks.IDENT) == 0) {

                        //if a supply depot exists, can build a barracks
                        if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
                            if (GameState.minerals >= Barracks.mineralCost && GameState.gas >= Barracks.gasCost) {
                                buildBarracks();
                                break;
                            }
                        }
                        
                        //if a supply depot does not exist, checks if it is in the porcess of being built
                        //if not, tries to build one
                        else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
                            if (GameState.minerals >= SupplyDepot.mineralCost && GameState.gas >= SupplyDepot.gasCost) {
                                buildSupplyDepot();
                                break;
                            }
                        }
                    }
                }
            }

            // "build hellion"
            case 1: {

                if (GameState.goal.get(Hellion.IDENT) > GameState.units.get(Hellion.IDENT)) {

                    //checks if a free factory exists to build a hellion
                    if (GameState.freeBuildings.get(Factory.IDENT) > 0) {
                        if (GameState.minerals >= Hellion.mineralCost && GameState.gas >= Hellion.gasCost) {
                            buildHellion();
                            break; // inside the if statement, so that if it can build a hellion, it checks to see if it can build sth else
                        }
                    } 
                        
                    //if a free factory does not exist, checks if it is in the process of being built or busy
                    //if not, tries to build a factory
                    else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0 
                        || GameState.busyBuildings.get(Factory.IDENT) == 0) {

                        //if a barracks exists, can build a factory
                        if (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0) {
                            if (GameState.minerals >= Factory.mineralCost && GameState.gas >= Factory.gasCost) {
                                buildFactory();
                                break;
                            }
                        } 
                                
                        //if a barracks does not exist, checks if it is in the porcess of being built
                        //if not, tries to build one
                        else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0) {

                            //if a supply depot exists, can build a barracks
                            if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
                                if (GameState.minerals >= Barracks.mineralCost && GameState.gas >= Barracks.gasCost) {
                                    buildBarracks();
                                    break;
                                }
                            }

                            //if a supply depot does not exist, checks if it is in the porcess of being built
                            //if not, tries to build one
                            else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
                                if (GameState.minerals >= SupplyDepot.mineralCost && GameState.gas >= SupplyDepot.gasCost) {
                                    buildSupplyDepot();
                                    break;
                                }
                            }
                        }
                    }
                }
            }

            // "build medivac"
            case 2: {

                if (GameState.goal.get(Medivac.IDENT) > GameState.units.get(Medivac.IDENT)) {

                    //checks if a free starport exists to build a medivac
                    if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
                        if (GameState.minerals >= Medivac.mineralCost && GameState.gas >= Medivac.gasCost) {
                            buildMedivac();
                            break;
                        }
                    } 

                    //if a free starport does not exist, checks if it is in the process of being built or busy
                    //if not, tries to build a starport
                    else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
                        || GameState.busyBuildings.get(Starport.IDENT) == 0) {

                        //if a factory exists, can build a starport
                        if (GameState.freeBuildings.get(Factory.IDENT) > 0 || GameState.busyBuildings.get(Factory.IDENT) > 0) {
                            if (GameState.minerals >= Starport.mineralCost && GameState.gas >= Starport.gasCost) {
                                buildStarport();
                                break;
                            }
                        }
                        
                        //if a factory does not exist, checks if it is in the process of being built
                        //if not, tries to build a factory
                        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0) {

                            //if a barracks exists, can build a factory
                            if (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0) {
                                if (GameState.minerals >= Factory.mineralCost && GameState.gas >= Factory.gasCost) {
                                    buildFactory();
                                    break;
                                }
                            } 
                            
                            //if a barracks does not exist, checks if it is in the porcess of being built
                            //if not, tries to build one
                            else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0) {

                                //if a supply depot exists, can build a barracks
                                if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
                                    if (GameState.minerals >= Barracks.mineralCost && GameState.gas >= Barracks.gasCost) {
                                        buildBarracks();
                                        break;
                                    }
                                }

                                //if a supply depot does not exist, checks if it is in the porcess of being built
                                //if not, tries to build one
                                else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
                                    if (GameState.minerals >= SupplyDepot.mineralCost && GameState.gas >= SupplyDepot.gasCost) {
                                        buildSupplyDepot();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // "build viking"
            case 3: {

                if (GameState.goal.get(Viking.IDENT) > GameState.units.get(Viking.IDENT)) {

                    //checks if a free starport exists to build a viking
                    if (GameState.freeBuildings.get(Starport.IDENT) > 0) {
                        if (GameState.minerals >= Viking.mineralCost && GameState.gas >= Viking.gasCost) {
                            buildViking();
                            break;
                        }
                    } 

                    //if a free starport does not exist, checks if it is in the process of being built or busy
                    //if not, tries to build a starport
                    else if (GameState.constructionsBeingBuilt.get(Starport.IDENT).size() == 0 
                        || GameState.busyBuildings.get(Starport.IDENT) == 0) {

                        //if a factory exists, can build a starport
                        if (GameState.freeBuildings.get(Factory.IDENT) > 0 || GameState.busyBuildings.get(Factory.IDENT) > 0) {
                            if (GameState.minerals >= Starport.mineralCost && GameState.gas >= Starport.gasCost) {
                                buildStarport();
                                break;
                            }
                        }
                        
                        //if a factory does not exist, checks if it is in the process of being built
                        //if not, tries to build a factory
                        else if (GameState.constructionsBeingBuilt.get(Factory.IDENT).size() == 0) {

                            //if a barracks exists, can build a factory
                            if (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0) {
                                if (GameState.minerals >= Factory.mineralCost && GameState.gas >= Factory.gasCost) {
                                    buildFactory();
                                    break;
                                }
                            } 
                            
                            //if a barracks does not exist, checks if it is in the porcess of being built
                            //if not, tries to build one
                            else if (GameState.constructionsBeingBuilt.get(Barracks.IDENT).size() == 0) {

                                //if a supply depot exists, can build a barracks
                                if (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0) {
                                    if (GameState.minerals >= Barracks.mineralCost && GameState.gas >= Barracks.gasCost) {
                                        buildBarracks();
                                        break;
                                    }
                                }

                                //if a supply depot does not exist, checks if it is in the porcess of being built
                                //if not, tries to build one
                                else if (GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT).size() == 0) {
                                    if (GameState.minerals >= SupplyDepot.mineralCost && GameState.gas >= SupplyDepot.gasCost) {
                                        buildSupplyDepot();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // build workers, command centers, refineries, reassign workers
            default: {

                Random random = new Random();
                int i = random.nextInt(7);

                switch (i) {
                    
                    case 0: {
                        if (GameState.workers.get(Worker.FREE) > 0 && GameState.workers.get(Worker.GAS) < GameState.freeBuildings.get(Refinery.IDENT) * 3) {
                            reassignWorker(Worker.FREE, Worker.GAS);
                        }
                        break;
                    }

                    // "reassign worker from free to minerals"
                    case 1: {
                        if (GameState.workers.get(Worker.FREE) > 0 && GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {
                            reassignWorker(Worker.FREE, Worker.MINERALS);
                        }
                        break;
                    }

                    // "reassign worker from gas to minerals"
                    case 2: {
                        if (GameState.workers.get(Worker.GAS) > 0 && GameState.workers.get(Worker.MINERALS) < GameState.patches * 3) {
                            reassignWorker(Worker.GAS, Worker.MINERALS);
                        }
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

                    // "build worker"
                    case 4: {

                        //checks if a free command center exists to build a viking
                        if (GameState.freeBuildings.get(CommandCenter.IDENT) > 0) {
                            if (GameState.minerals >= Worker.mineralCost && GameState.gas >= Worker.gasCost) {
                                buildWorker();
                                break;
                            }
                        } 
                        break;
                    }

                    // "build refinery"
                    case 5: {

                        //max number of refineries is number of geysers
                        if (GameState.minerals >= Refinery.mineralCost && GameState.gas >= Refinery.gasCost
                            && GameState.freeBuildings.get(Refinery.IDENT)
                            + GameState.busyBuildings.get(Refinery.IDENT)
                            + GameState.constructionsBeingBuilt.get(Refinery.IDENT).size() < GameState.geysers) {
                            buildRefinery();
                            break;
                        }
                    }

                     // "build command center"
                     case 6: {

                        //max 3 command centers for now
                        if (GameState.minerals >= CommandCenter.mineralCost && GameState.gas >= CommandCenter.gasCost
                            && GameState.freeBuildings.get(CommandCenter.IDENT)
                                + GameState.busyBuildings.get(CommandCenter.IDENT)
                                + GameState.constructionsBeingBuilt.get(CommandCenter.IDENT).size() < 3) {
                            buildCommandCenter();
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void buildMarine() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Marine.IDENT);
        durations.add(Marine.buildTime);
        GameState.constructionsBeingBuilt.put(Marine.IDENT, durations);
        GameState.updateBuildings(Marine.builtFrom, "start");
        GameState.gas -= Marine.gasCost;
        GameState.minerals -= Marine.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Marine.IDENT);
    }

    private static void buildHellion() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Hellion.IDENT);
        durations.add(Hellion.buildTime);
        GameState.constructionsBeingBuilt.put(Hellion.IDENT, durations);
        GameState.updateBuildings(Hellion.builtFrom, "start");
        GameState.gas -= Hellion.gasCost;
        GameState.minerals -= Hellion.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Hellion.IDENT);
    }

    private static void buildMedivac() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Medivac.IDENT);
        durations.add(Medivac.buildTime);
        GameState.constructionsBeingBuilt.put(Medivac.IDENT, durations);
        GameState.updateBuildings(Medivac.builtFrom, "start");
        GameState.gas -= Medivac.gasCost;
        GameState.minerals -= Medivac.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Medivac.IDENT);
    }

    private static void buildViking() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Viking.IDENT);
        durations.add(Viking.buildTime);
        GameState.constructionsBeingBuilt.put(Viking.IDENT, durations);
        GameState.updateBuildings(Viking.builtFrom, "start");
        GameState.gas -= Viking.gasCost;
        GameState.minerals -= Viking.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Viking.IDENT);
    }

    private static void buildWorker() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Worker.IDENT);
        durations.add(Worker.buildTime);
        GameState.constructionsBeingBuilt.put(Worker.IDENT, durations);
        GameState.updateBuildings(Worker.builtFrom, "start");
        GameState.gas -= Worker.gasCost;
        GameState.minerals -= Worker.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Worker.IDENT);
    }

    private static void buildBarracks() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Barracks.IDENT);
        durations.add(Barracks.buildTime);
        GameState.constructionsBeingBuilt.put(Barracks.IDENT, durations);
        GameState.gas -= Barracks.gasCost;
        GameState.minerals -= Barracks.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Barracks.IDENT);
    }

    private static void buildCommandCenter() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(CommandCenter.IDENT);
        durations.add(CommandCenter.buildTime);
        GameState.constructionsBeingBuilt.put(CommandCenter.IDENT, durations);
        GameState.gas -= CommandCenter.gasCost;
        GameState.minerals -= CommandCenter.mineralCost;
        decisionsMade.add(GameState.time + " start building " + CommandCenter.IDENT);
    }

    private static void buildStarport() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Starport.IDENT);
        durations.add(Starport.buildTime);
        GameState.constructionsBeingBuilt.put(Starport.IDENT, durations);
        GameState.gas -= Starport.gasCost;
        GameState.minerals -= Starport.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Starport.IDENT);
    }

    private static void buildSupplyDepot() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT);
        durations.add(SupplyDepot.buildTime);
        GameState.constructionsBeingBuilt.put(SupplyDepot.IDENT, durations);
        GameState.gas -= SupplyDepot.gasCost;
        GameState.minerals -= SupplyDepot.mineralCost;
        decisionsMade.add(GameState.time + " start building " + SupplyDepot.IDENT);
    }

    private static void buildRefinery() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Refinery.IDENT);
        durations.add(Refinery.buildTime);
        GameState.constructionsBeingBuilt.put(Refinery.IDENT, durations);
        GameState.gas -= Refinery.gasCost;
        GameState.minerals -= Refinery.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Refinery.IDENT);
    }

    private static void buildFactory() {
        ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Factory.IDENT);
        durations.add(Factory.buildTime);
        GameState.constructionsBeingBuilt.put(Factory.IDENT, durations);
        GameState.gas -= Factory.gasCost;
        GameState.minerals -= Factory.mineralCost;
        decisionsMade.add(GameState.time + " start building " + Factory.IDENT);
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
        decisionsMade.add(GameState.time + " reassign " + Worker.IDENT + " from " + initialState + " to " + finalState);
    }
}
