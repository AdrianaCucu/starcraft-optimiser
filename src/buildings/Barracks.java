package buildings;

public class Barracks extends Building {

    /**
     * Constructor for Barracks.
     *
     * Mineral cost: 150
     * Gas cost: 0
     * Build Time: 65 seconds
     * Dependency: Supply Depot
     */
    public Barracks() {
        super(150, 0, 65, new SupplyDepot());
    }

}
