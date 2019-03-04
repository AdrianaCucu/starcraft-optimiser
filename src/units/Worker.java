package units;

import buildings.*;

public class Worker extends Unit {

    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, new CommandCenter());
    }

    /**
     * Updates game state when worker is being built.
     */
    public void buildWorker() {
        super.beingBuilt = true;
        Minerals.total -= super.mineralCost;
        Gas.total -= super.gasCost;
    }
}