import java.sql.Ref;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import constructions.buildings.*;
import constructions.units.*;

public class Decision {

    // the decisions in order
    public static ArrayList<String> decisionsMade = new ArrayList<>();


    public static void makeDecision() {

        // updates the game time and resources
        GameState.updateGameState();

        if (!Goal.goalAchieved) {

            Random random = new Random();

            List<String> decisions = possibleDecisions();

            // chooses a random element from the possible decisions list
            int index = random.nextInt(decisions.size());

            updateState(decisions, index);
        }
    }

    public static List<String> possibleDecisions() {

        List<String> possibleDecisions = new ArrayList<>();

        // !!! aici e probabil ceva gresit cu precedenta !!!
        for (int i = Goal.precedence; i > 0; i--) {

            // do nothing
            possibleDecisions.add("skip");

            // reassign workers
            if (GameState.workers.get("free") > 0 && GameState.workers.get("minerals") < 24) {
                possibleDecisions.add("reassign worker from free to minerals");
            }

            if (GameState.workers.get("free") > 0 && GameState.workers.get("gas") < 6) {
                possibleDecisions.add("reassign worker from free to gas");
            }

            if (GameState.workers.get("gas") > 0 && GameState.workers.get("minerals") < 24) {
                possibleDecisions.add("reassign worker from gas to minerals");
            }

            if (GameState.workers.get("minerals") > 0 && GameState.workers.get("gas") < 6) {
                possibleDecisions.add("reassign worker from minerals to gas");
            }

            // build stuff
            if (GameState.minerals >= 50) {
                if (GameState.freeBuildings.get("command center") > 0 && Worker.precedence == i) {
                    possibleDecisions.add("build worker");
                }
                if (GameState.freeBuildings.get("barracks") > 0 && Marine.precedence == i) {
                    possibleDecisions.add("build marine");
                }

                if (GameState.minerals >= 75 && Refinery.precedence == i) {
                    possibleDecisions.add("build refinery");
                }

                if (GameState.minerals >= 100) {
                    if (SupplyDepot.precedence == i) {
                        possibleDecisions.add("build supply depot");
                    }
                    if (GameState.freeBuildings.get("factory") > 0 && Hellion.precedence == i) {
                        possibleDecisions.add("build hellion");
                    }
                    if (GameState.gas >= 100 && GameState.freeBuildings.get("starport") > 0 && Medivac.precedence == i) {
                        possibleDecisions.add("build medivac");
                    }
                }

                if (GameState.minerals >= 150) {
                    if ((GameState.freeBuildings.get("supply depot") > 0 || GameState.busyBuildings.get("supply depot") > 0)
                        && Barracks.precedence == i) {
                        possibleDecisions.add("build barracks");
                    }
                    if (GameState.gas >= 75) {
                        if (GameState.freeBuildings.get("starport") > 0 && Viking.precedence == i) {
                            possibleDecisions.add("build viking");
                        }
                        if (GameState.gas >= 100) {
                            if ((GameState.freeBuildings.get("barracks") > 0 || GameState.busyBuildings.get("barracks") > 0)
                                && Factory.precedence == i) {
                                possibleDecisions.add("build factory");
                            }
                            if ((GameState.freeBuildings.get("factory") > 0 || GameState.busyBuildings.get("factory") > 0)
                                && Starport.precedence == i) {
                                possibleDecisions.add("build starport");
                            }
                        }
                    }
                }
            }
        }

        return possibleDecisions;
    }

    private static void updateState(List<String> decisions, int index) {

        if (!decisions.get(index).equals("skip")) {
            int minutes = GameState.time / 60;
            int seconds = GameState.time % 60;
            if (seconds < 10) {
                decisionsMade.add(decisions.get(index) + " " + minutes + ":0" + seconds);
            }
            else {
                decisionsMade.add(decisions.get(index) + " " + minutes + ":" + seconds);
            }
        }

        switch (decisions.get(index)) {

            case "reassign worker from free to gas": {
                Integer freeNumber = GameState.workers.get("free") - 1;
                GameState.workers.put("free", freeNumber);
                Integer gasNumber = GameState.workers.get("gas") + 1;
                GameState.workers.put("gas", gasNumber);
                break;
            }
            case "reassign worker from free to minerals": {
                Integer freeNumber = GameState.workers.get("free") - 1;
                GameState.workers.put("free", freeNumber);
                Integer mineralsNumber = GameState.workers.get("minerals") + 1;
                GameState.workers.put("minerals", mineralsNumber);
                break;
            }
            case "reassign worker from gas to free": {
                Integer gasNumber = GameState.workers.get("gas") - 1;
                GameState.workers.put("gas", gasNumber);
                Integer freeNumber = GameState.workers.get("free") + 1;
                GameState.workers.put("free", freeNumber);
                break;
            }
            case "reassign worker from gas to minerals": {
                Integer gasNumber = GameState.workers.get("gas") - 1;
                GameState.workers.put("gas", gasNumber);
                Integer mineralsNumber = GameState.workers.get("minerals") + 1;
                GameState.workers.put("minerals", mineralsNumber);
                break;
            }
            case "reassign worker from minerals to free": {
                Integer mineralsNumber = GameState.workers.get("minerals") - 1;
                GameState.workers.put("minerals", mineralsNumber);
                Integer freeNumber = GameState.workers.get("free") + 1;
                GameState.workers.put("free", freeNumber);
                break;
            }
            case "reassign worker from minerals to gas": {
                Integer mineralsNumber = GameState.workers.get("minerals") - 1;
                GameState.workers.put("minerals", mineralsNumber);
                Integer gasNumber = GameState.workers.get("gas") + 1;
                GameState.workers.put("gas", gasNumber);
                break;
            }
            case "build hellion": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("hellion");
                durations.add(Hellion.buildTime);
                GameState.constructionsBeingBuilt.put("hellion", durations);
                GameState.updateBuildings(Hellion.builtFrom, "start");
                GameState.gas -= Hellion.gasCost;
                GameState.minerals -= Hellion.mineralCost;
                break;
            }
            case "build marine": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("marine");
                durations.add(Marine.buildTime);
                GameState.constructionsBeingBuilt.put("marine", durations);
                GameState.updateBuildings(Marine.builtFrom, "start");
                GameState.gas -= Marine.gasCost;
                GameState.minerals -= Marine.mineralCost;
                break;
            }
            case "build medivac": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("medivac");
                durations.add(Medivac.buildTime);
                GameState.constructionsBeingBuilt.put("medivac", durations);
                GameState.updateBuildings(Medivac.builtFrom, "start");
                GameState.gas -= Medivac.gasCost;
                GameState.minerals -= Medivac.mineralCost;
                break;
            }
            case "build viking": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("viking");
                durations.add(Viking.buildTime);
                GameState.constructionsBeingBuilt.put("viking", durations);
                GameState.updateBuildings(Viking.builtFrom, "start");
                GameState.gas -= Viking.gasCost;
                GameState.minerals -= Viking.mineralCost;
                break;
            }
            case "build worker": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("worker");
                durations.add(Worker.buildTime);
                GameState.constructionsBeingBuilt.put("worker", durations);
                GameState.updateBuildings(Worker.builtFrom, "start");
                GameState.gas -= Worker.gasCost;
                GameState.minerals -= Worker.mineralCost;
                break;
            }
            case "build barracks": {
                Integer barracksNumber = GameState.freeBuildings.get("barracks") + 1;
                GameState.workers.put("barracks", barracksNumber);
                GameState.gas -= Barracks.gasCost;
                GameState.minerals -= Barracks.mineralCost;
                break;
            }
            case "build command center": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("command center");
                durations.add(CommandCenter.buildTime);
                GameState.constructionsBeingBuilt.put("command center", durations);
                GameState.gas -= CommandCenter.gasCost;
                GameState.minerals -= CommandCenter.mineralCost;
                break;
            }
            case "build factory": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("factory");
                durations.add(Factory.buildTime);
                GameState.constructionsBeingBuilt.put("factory", durations);
                GameState.gas -= Factory.gasCost;
                GameState.minerals -= Factory.mineralCost;
                break;
            }
            case "build refinery": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("refinery");
                durations.add(Refinery.buildTime);
                GameState.constructionsBeingBuilt.put("refinery", durations);
                GameState.gas -= Refinery.gasCost;
                GameState.minerals -= Refinery.mineralCost;
                break;
            }
            case "build starport": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("starport");
                durations.add(Starport.buildTime);
                GameState.constructionsBeingBuilt.put("starport", durations);
                GameState.gas -= Starport.gasCost;
                GameState.minerals -= Starport.mineralCost;
                break;
            }
            case "build supply depot": {
                ArrayList<Integer> durations = GameState.constructionsBeingBuilt.get("supply depot");
                durations.add(SupplyDepot.buildTime);
                GameState.constructionsBeingBuilt.put("supply depot", durations);
                GameState.gas -= SupplyDepot.gasCost;
                GameState.minerals -= SupplyDepot.mineralCost;
                break;
            }
        }
    }
}
