package units;

import buildings.*;
import resources.*;

public class Unit {

    protected double mineralCost;
    protected double gasCost;

    /**
     * Build time in seconds
     */
    protected int buildTime;

    //no dependency for core program
    protected Building dependentOn;
    protected Building builtFrom;

    /**
     * Unit constructor.
     *
     * @param mineralCost - the cost in minerals
     * @param gasCost - the cost in gas
     * @param buildTime - the build time
     * @param dependentOn - the type of building the unit is dependent on
     * @param builtFrom - the type of building that builds the unit
     */
    public Unit(double mineralCost, double gasCost, int buildTime, Building dependentOn, Building builtFrom) {
        this.mineralCost = mineralCost;
        this.gasCost = gasCost;
        this.buildTime = buildTime;
        this.dependentOn = dependentOn;
        this.builtFrom = builtFrom;
    }

    // need to check that we have enough resources before
    public void buildUnit() {
        Minerals.total -= mineralCost;
        Gas.total -= gasCost;
    }
}