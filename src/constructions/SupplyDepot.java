package constructions;

import java.util.*;

public class SupplyDepot extends Building {

    /**
     * Constructor for Supply Depot.
     *
     * Mineral cost: 100
     * Gas cost: 0
     * Build Time: 30 seconds
     * Dependency: none
     * Builds units: none
     */
    public SupplyDepot() {
        super(100, 0, 30, null, Arrays.asList());
    }

}
