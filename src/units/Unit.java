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
    }
}