package constructions;

import resources.*;

import java.util.*;

public class Building extends Construction {

    protected double mineralCost;
    protected double gasCost;

    /**
     * Build time in seconds.
     */
    protected int buildTime;

    /**
     * The building cannot be built unless the dependency is satisfied.
     */
    protected String dependentOn;

    /**
     * Stores the types of units each building can build.
     * Different for each type of building.
     * Can be empty.
     */
    protected List<String> units;

    /**
     * Used to check if the building is constructing a worker at the current time.
     */
    protected boolean buildingUnit = false;

    /**
     * Building constructor.
     *
     * @param mineralCost - the cost in minerals
     * @param gasCost - the cost in gas
     * @param buildTime - the build time
     * @param dependentOn - the type of building the current building is dependent on
     * @param units - the type of units the building can build
     */
    public Building(double mineralCost, double gasCost, int buildTime, String dependentOn, List<String> units) {
        this.mineralCost = mineralCost;
        this.gasCost = gasCost;
        this.buildTime = buildTime;
        this.dependentOn = dependentOn;
        this.units = units;
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
     * Can be null, because not all constructions have dependencies.
     *
     * @return the building that represents the dependency or null
     */
    public String getDependency() {
        return dependentOn;
    }

    // need to check that dependencies are met before building
    // and that we have enough resources
    public void buildBuilding() {
        Minerals.total -= mineralCost;
        Gas.total -= gasCost;
    }

    // need to check that it is possible to build it beforehand:
    // resources, that the building is not already busy,
    // that the building can build this type of unit
    /**
     * When the building starts building a unit.
     *
     * @param unit the unit being built
     */
    public void startBuilding(Unit unit) {
        buildingUnit = true;
    }

    // only for constructions that are busy
    /**
     * When the building finishes building a unit.
     *
     * @param unit the unit that was built
     */
    public void stopBuilding(Unit unit) {
        buildingUnit = false;
    }
}
