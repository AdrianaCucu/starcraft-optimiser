package constructions;

import java.util.*;

public class Refinery extends Building {

    public static final String IDENT = "Refinery";

    /**
     * Constructor for Refinery.
     *
     * Mineral cost: 75
     * Gas cost: 0
     * Build Time: 30 seconds
     * Dependency: none
     * Builds units: none
     */
    public Refinery() {
        super(75, 0, 30, null, Arrays.asList());
    }

}
