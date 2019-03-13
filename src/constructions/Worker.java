package constructions;

public class Worker extends Unit {
    private boolean isAssigned;
    private Base assignedBase;



    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, new CommandCenter());
        this.isAssigned = false;
        this.assignedBase = null;
    }

    public void assignPatch() {
        isAssigned = true;
        assignedBase = new Patch();
    }

    public void assignGeyser() {
        isAssigned = true;
        assignedBase = new Geyser();
    }

}