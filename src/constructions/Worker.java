package constructions;

import base.*;

public class Worker extends Unit {

    public static final String IDENT = "Worker";

    private Base assignedBase = null;

    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, new CommandCenter());
    }

    public void assignPatch(MineralPatch patch) {
        assignedBase = patch;
    }

    public void assignGeyser(Geyser geyser) {
        assignedBase = geyser;
    }

}
