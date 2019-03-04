package buildings;

public class Building {

    protected double mineralCost;
    protected double gasCost;

    /**
     * Build time in seconds.
     */
    protected int buildTime;

    /**
     * The building cannot be built unless the dependency is satisfied.
     */
    protected Building dependentOn;

    public Building(double mineralCost, double gasCost, int buildTime, Building dependentOn) {
        this.mineralCost = mineralCost;
        this.gasCost = gasCost;
        this.buildTime = buildTime;
        this.dependentOn = dependentOn;
    }

    public double getMineralCost() {
        return mineralCost;
    }

    public double getGasCost() {
        return gasCost;
    }

    public int getBuildTime() {
        return buildTime;
    }

    /**
     * Can be null, because not all buildings have dependencies.
     *
     * @return the building that represents the dependency or null
     */
    public Building getDependency() {
        return dependentOn;
    }
}
