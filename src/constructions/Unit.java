package constructions;

import resources.*;

public class Unit extends Construction {

    protected double mineralCost;
    protected double gasCost;

    /**
     * Build time in seconds
     */
    protected int buildTime;

    //no dependency for core program
    protected Building dependentOn;
    protected Building builtFrom;

    protected boolean beingBuilt;

    /**
     * getter for beingBuilt.
     * @return - beingBuilt
     */
    public boolean isBeingBuilt() {
        return beingBuilt;
    }

    /**
     * setter for beingBuilt;
     * @param beingBuilt - boolean value
     */
    public void setBeingBuilt(boolean beingBuilt) {
        this.beingBuilt = beingBuilt;
    }

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
        this.beingBuilt = false;
    }

    // need to check that we have enough resources before
    public void buildUnit() {
        this.beingBuilt = true;
        Minerals.total -= mineralCost;
        Gas.total -= gasCost;
    }
}