import java.util.*;
import units.*;
import buildings.*;
import resources.*;

public class GameState {

    // do specific storing for each action the unit is performing
    /**
     * Stores the units and the total number for each type of unit at the current second.
     */
    private static HashMap<Unit, Integer> units = new HashMap<>();

    /**
     * Stores the buildings and total number for each type of building at the current second.
     */
    private static HashMap<Building, Integer> buildings = new HashMap<>();

    /**
     * The initial state of the game at 0 seconds.
     */
    public static void initialiseGame() {
        units.put(new Hellion(), 0);
        units.put(new Marine(), 0);
        units.put(new Medivac(), 0);
        units.put(new Viking(), 0);
        units.put(new Worker(), 6);

        buildings.put(new Barracks(), 0);
        buildings.put(new CommandCenter(), 1);
        buildings.put(new Factory(), 0);
        buildings.put(new Refinery(), 0);
        buildings.put(new Starport(), 0);
        buildings.put(new SupplyDepot(), 0);

        Minerals.updateTotal(50);
    }

    public static void updateState(Unit unit) {

    }

    public static void updateState(Building building) {

    }

}