package units;

import buildings.*;

public class Unit {
    protected double mineralCost;
    protected double gasCost;
    /**
     * Build time in seconds
     */
    protected int buildTime;
    protected Unit dependentOn;
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
     * @param mineralCost - mineral cost
     * @param gasCost - gas cost
     * @param buildTime - build time;
     * @param dependentOn - unit its dependent on
     * @param builtFrom - building its built from
     */
    public Unit(double mineralCost, double gasCost, int buildTime, Unit dependentOn, Building builtFrom) {
        this.mineralCost = mineralCost;
        this.gasCost = gasCost;
        this.buildTime = buildTime;
        this.dependentOn = dependentOn;
        this.builtFrom = builtFrom;
        this.beingBuilt = false;
    }
}