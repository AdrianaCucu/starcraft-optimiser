package constructions;

import base.*;

public class Worker extends Unit {

    public static final String IDENT = "Worker";
    private BaseUnit assignedBaseUnit = null;

    public BaseUnit getAssignedBaseUnit() {
        return assignedBaseUnit;
    }

    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, CommandCenter.IDENT);
    }

    public void assignBaseUnit(BaseUnit baseUnit) {
        this.assignedBaseUnit = baseUnit;
    }

}
