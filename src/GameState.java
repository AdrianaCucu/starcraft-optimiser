import java.util.*;
import units.*;
import buildings.*;
import base.*;

public class GameState {

    private static int time;


    private HashMap<Building, Integer> buildings = new HashMap<>();

    // units without workers
    private HashMap<Unit, Integer> units = new HashMap<>();

    private HashMap<Base, Integer> workers = new HashMap<>();


    // !!! WORK ON THIS !!!
    public GameState(HashMap<Unit, Integer> units, HashMap<Base, Integer> buildings, HashMap<Base, Integer> workers) {

    }

    // initial state of the game in terms of buildings, units, and time
    private void initialiseGame() {

        // 0 seconds when initialising game
        time = Timer.getSecondsElapsed();

        buildings.put(new Barracks(), 0);
        buildings.put(new CommandCenter(), 0);
        buildings.put(new Factory(), 0);
        buildings.put(new Refinery(), 0);
        buildings.put(new Starport(), 0);
        buildings.put(new SupplyDepot(), 0);

        units = new HashMap<>();
        units.put(new Hellion(), 0);
        units.put(new Marine(), 0);
        units.put(new Medivac(), 0);
        units.put(new Viking(), 0);

        workers = new HashMap<>();
        workers.put(null, 6);
    }

    public static GameState getCurrentState() {
        return new GameState();
    }

    public void updateState(Unit unit) {

    }

    public void updateState(Building building) {

    }

}
