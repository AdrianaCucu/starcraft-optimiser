package units;

import buildings.*;

public class Medivac extends Unit{
    /**
     * Medivac constructor.
     */
    public Medivac() {
        super(100 ,100, 42, null, new Starport());
    }

    /**
     * Updates game state when worker is being built.
     */
    public void buildMedivac() {
        super.beingBuilt = true;
        Minerals.total -= super.mineralCost;
        Gas.total -= super.gasCost;
    }
}