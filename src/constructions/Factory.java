package constructions;

import java.util.Arrays;

public class Factory extends Building {

    /**
     * Constructor for Factory.
     *
     * Mineral cost: 150
     * Gas cost: 100
     * Build Time: 60 seconds
     * Dependency: Barracks
     * Builds units: Hellion
     */
    public Factory() {
        super(150, 100, 60, new Barracks(), Arrays.asList(new Hellion()));
    }

}
