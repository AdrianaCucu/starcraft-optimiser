package units;

import buildings.*;

public class Hellion extends Unit{

    /**
     * Hellion constructor.
     */
    public Hellion() {
        super(50, 0, 25, null, new Barracks());
    }

    /**
     * Updates game state when hellion is being built.
     */
    public void buildHellion() {
        super.beingBuilt = true;
        Minerals.total -= super.mineralCost;
        Gas.total -= super.gasCost;
    }
}