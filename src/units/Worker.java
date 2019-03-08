package units;

import buildings.*;

public class Worker extends Unit {
    Base assignedBase;



    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, new CommandCenter());
        this.assignedBase = null;
    }

    public void assignPatch() {

    }

    public void assignGeyser() {

    }

}