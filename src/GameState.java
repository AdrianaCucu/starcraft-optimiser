import java.util.*;
import units.*;
import buildings.*;
import resources.*;

public class GameState {
    private GameState gs;
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
        this.time = 0;
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


    /**
     * Initialises game and creates first starting game state.
     * @return
     */
    public GameState initialiseGame() {
        GameState newGs;
        gs = new GameState();

        gs.updateState(new CommandCenter());

        gs.updateState(new Worker());
        gs.updateState(new Worker());
        gs.updateState(new Worker());
        gs.updateState(new Worker());
        gs.updateState(new Worker());
        gs.updateState(new Worker());

        Minerals.total = 50;
        Gas.total = 0;

        Timer.setSecondsElapsed(0);

        newGs = gs;

        return newGs;

    }


    /**
     * Updates state of game accordingly.
     * @param unit
     */
    public void updateState(Unit unit) {
        String unitType = unit.IDENT;

        switch (unitType) {
            case Hellion.IDENT: {
                hellions.add(unit);
                break;
            }
            case Marine.IDENT: {
                marines.add(unit);
                break;
            }
            case Medivac.IDENT: {
                medivacs.add(unit);
                break;
            }
            case Viking.IDENT: {
                vikings.add(unit);
                break;
            }
            case Worker.IDENT: {
                unassignedWorkers.add(unit);
                break;
            }
            default: {

            }

        }

    }

    public void updateState(Building building) {
        String buildingType = building.IDENT;

        switch (buildingType) {
            case Barracks.IDENT: {
                barracks.add(building);
                break;
            }
            case CommandCenter.IDENT: {
                commandCenters.add(building);
                break;
            }
            case Factory.IDENT: {
                factories.add(building);
                break;
            }
            case Refinery.IDENT: {
                refineries.add(building);
                break;
            }
            case Starport.IDENT: {
                starports.add(building);
                break;
            }
            default: {

            }

        }
    }


}