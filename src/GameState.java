import java.util.*;
import units.*;
import buildings.*;
import resources.*;

public class GameState {
    private int time;

    private List<Barracks> barracks;
    private List<CommandCenter> commandCenters;
    private List<Factory> factories;
    private List<Refinery> refineries;
    private List<Starport> starports;
    private List<SupplyDepot> supplyDepots;

    private List<Hellion> hellions;
    private List<Marine> marines;
    private List<Medivac> medivacs;
    private List<Viking> vikings;
    private List<Worker> unassignedWorkers;
    private List<Worker> geyserWorkers;
    private List<Worker> patchWorkers;





    public GameState() {

    }


    public void initialiseGame() {
        this.barracks = new ArrayList<>();
        this.commandCenters = new ArrayList<>();
        this.factories = new ArrayList<>();
        this.refineries = new ArrayList<>();
        this.starports = new ArrayList<>();
        this.supplyDepots = new ArrayList<>();

        this.hellions  = new ArrayList<>();
        this.marines = new ArrayList<>();
        this.medivacs = new ArrayList<>();
        this.vikings = new ArrayList<>();
        this.unassignedWorkers = new ArrayList<>();
        this.geyserWorkers = new ArrayList<>();
        this.patchWorkers = new ArrayList<>();
    }

    public static void updateState(Unit unit) {

    }

    public static void updateState(Building building) {

    }

}