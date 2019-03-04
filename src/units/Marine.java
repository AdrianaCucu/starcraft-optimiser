package units;

import buildings.*;

public class Marine extends Unit {
    /**
     * Marine constructor.
     */
    public Marine() {
        super(50, 0, 25, null, new Barracks());
    }

    /**
     * Updates game state when marine is being built.
     */
    public void buildMarine() {
        super.beingBuilt = true;
        Minerals.total -= super.mineralCost;
        Gas.total -= super.gasCost;
    }
}