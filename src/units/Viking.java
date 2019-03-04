package units;

import buildings.*;

public class Viking extends Unit {
    /**
     * Viking constructor.
     */
    public Viking() {
        super(150, 75, 42, null, new Starport());
    }

    /**
     * Updates game state when worker is being built.
     */
    public void buildViking() {
        super.beingBuilt = true;
        Minerals.total -= super.mineralCost;
        Gas.total -= super.gasCost;
    }
}