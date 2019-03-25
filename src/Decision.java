import java.sql.Ref;
import java.util.*;
import java.lang.reflect.*;

import constructions.Construction;
import constructions.buildings.*;
import constructions.units.*;

public class Decision {

    // the decisions in order
    public static ArrayList<String> decisionsMade = new ArrayList<>();

    public static void makeDecision() {

        // updates the resources and checks if the goal has been met
        GameState.updateGameState();

        if (!Goal.goalAchieved) {

            Random random = new Random();

            // to choose a random element from the possible decisions
            int index = random.nextInt(15);

            updateState(index);

            // updates the game time
            GameState.time += 1;
        }
    }

    // makes a random decision
    // if decision is not possible, just updates the game state (minerals etc.)
    private static void updateState(int index) {

        switch (index) {

            // "reassign worker from free to gas"
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

            // "build hellion"
            case 4: {
                if (GameState.minerals >= 100 && GameState.freeBuildings.get(Factory.IDENT) > 0) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Hellion.IDENT);
                    durations.add(Hellion.buildTime);
                    GameState.constructionsBeingBuilt.put(Hellion.IDENT, durations);
                    GameState.updateBuildings(Hellion.builtFrom, "start");
                    GameState.gas -= Hellion.gasCost;
                    GameState.minerals -= Hellion.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Hellion.IDENT);
                }
                break;
            }

            // "build marine"
            case 5: {
                if (GameState.minerals >= 50 && GameState.freeBuildings.get(Barracks.IDENT) > 0) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Marine.IDENT);
                    durations.add(Marine.buildTime);
                    GameState.constructionsBeingBuilt.put(Marine.IDENT, durations);
                    GameState.updateBuildings(Marine.builtFrom, "start");
                    GameState.gas -= Marine.gasCost;
                    GameState.minerals -= Marine.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Marine.IDENT);
                }
                break;
            }

            // "build medivac"
            case 6: {
                if (GameState.minerals >= 100 && GameState.gas >= 100 && GameState.freeBuildings.get(Starport.IDENT) > 0) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Medivac.IDENT);
                    durations.add(Medivac.buildTime);
                    GameState.constructionsBeingBuilt.put(Medivac.IDENT, durations);
                    GameState.updateBuildings(Medivac.builtFrom, "start");
                    GameState.gas -= Medivac.gasCost;
                    GameState.minerals -= Medivac.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Medivac.IDENT);
                }
                break;
            }

            // "build viking"
            case 7: {
                if (GameState.minerals >= 150 && GameState.gas >= 75 && GameState.freeBuildings.get(Starport.IDENT) > 0) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Viking.IDENT);
                    durations.add(Viking.buildTime);
                    GameState.constructionsBeingBuilt.put(Viking.IDENT, durations);
                    GameState.updateBuildings(Viking.builtFrom, "start");
                    GameState.gas -= Viking.gasCost;
                    GameState.minerals -= Viking.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Viking.IDENT);
                }
                break;
            }

            // "build worker"
            case 8: {
                if (GameState.minerals >= 50 && GameState.freeBuildings.get(CommandCenter.IDENT) > 0
                    && (GameState.workers.get("gas") < GameState.freeBuildings.get(Refinery.IDENT) * 3 
                    || GameState.workers.get("minerals") < GameState.patches * 3)) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Worker.IDENT);
                    durations.add(Worker.buildTime);
                    GameState.constructionsBeingBuilt.put(Worker.IDENT, durations);
                    GameState.updateBuildings(Worker.builtFrom, "start");
                    GameState.gas -= Worker.gasCost;
                    GameState.minerals -= Worker.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Worker.IDENT);
                }
                break;
            }

            // "build barracks"
            case 9: {
                if (GameState.minerals >= 150 && (GameState.freeBuildings.get(SupplyDepot.IDENT) > 0 || GameState.busyBuildings.get(SupplyDepot.IDENT) > 0)) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Barracks.IDENT);
                    durations.add(Barracks.buildTime);
                    GameState.constructionsBeingBuilt.put(Barracks.IDENT, durations);
                    GameState.gas -= Barracks.gasCost;
                    GameState.minerals -= Barracks.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Barracks.IDENT);
                }
                break;
            }

            // "build command center"
            case 10: {
                if (GameState.minerals >= 400) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(CommandCenter.IDENT);
                    durations.add(CommandCenter.buildTime);
                    GameState.constructionsBeingBuilt.put(CommandCenter.IDENT, durations);
                    GameState.gas -= CommandCenter.gasCost;
                    GameState.minerals -= CommandCenter.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + CommandCenter.IDENT);
                }
                break;
            }

            // "build factory"
            case 11: {
                if (GameState.minerals >= 150 && GameState.gas >= 100 
                    && (GameState.freeBuildings.get(Barracks.IDENT) > 0 || GameState.busyBuildings.get(Barracks.IDENT) > 0)) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Factory.IDENT);
                    durations.add(Factory.buildTime);
                    GameState.constructionsBeingBuilt.put(Factory.IDENT, durations);
                    GameState.gas -= Factory.gasCost;
                    GameState.minerals -= Factory.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Factory.IDENT);
                }
                break;
            }

            // "build refinery"
            case 12: {
                if (GameState.minerals >= 75 
                    && GameState.freeBuildings.get(Refinery.IDENT) + GameState.constructionsBeingBuilt.get(Refinery.IDENT).size() < GameState.geysers) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Refinery.IDENT);
                    durations.add(Refinery.buildTime);
                    GameState.constructionsBeingBuilt.put(Refinery.IDENT, durations);
                    GameState.gas -= Refinery.gasCost;
                    GameState.minerals -= Refinery.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Refinery.IDENT);
                }
                break;
            }

            // "build starport"
            case 13: {
                if (GameState.minerals >= 150 && GameState.gas >= 100 
                    && (GameState.freeBuildings.get(Factory.IDENT) > 0 || GameState.busyBuildings.get(Factory.IDENT) > 0)) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(Starport.IDENT);
                    durations.add(Starport.buildTime);
                    GameState.constructionsBeingBuilt.put(Starport.IDENT, durations);
                    GameState.gas -= Starport.gasCost;
                    GameState.minerals -= Starport.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + Starport.IDENT);
                }
                break;
            }

            // "build supply depot"
            case 14: {
                if (GameState.minerals >= 100) {
                    ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get(SupplyDepot.IDENT);
                    durations.add(SupplyDepot.buildTime);
                    GameState.constructionsBeingBuilt.put(SupplyDepot.IDENT, durations);
                    GameState.gas -= SupplyDepot.gasCost;
                    GameState.minerals -= SupplyDepot.mineralCost;
                    decisionsMade.add(GameState.time + " start building " + SupplyDepot.IDENT);
                }
                break;
            }
        }
    }

    /**
     * Reassigns workers between gas, minerals, and free workers.
     *
     * @param initialState - the initial "job" of the worker
     * @param finalState - the final "job" of the worker
     */
    private static void reassignWorker(String initialState, String finalState) {
        Integer initialNumber = GameState.workers.get(initialState) - 1;
        GameState.workers.put(initialState, initialNumber);
        Integer finalNumber = GameState.workers.get(finalState) + 1;
        GameState.workers.put(finalState, finalNumber);
        decisionsMade.add(GameState.time + " reassign " + Worker.IDENT + " from " + initialState + " to " + finalState);
    }
}
