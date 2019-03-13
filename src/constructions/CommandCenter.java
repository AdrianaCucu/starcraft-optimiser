package constructions;

import java.util.*;

public class CommandCenter extends Building {

    public static final String IDENT = "CommandCentre";

    /**
     * Constructor for Command Center.
     *
     * Mineral cost: 400
     * Gas cost: 0
     * Build Time: 100 seconds
     * Dependency: none
     * Builds units: Worker
     */
    public CommandCenter() {
        super(400, 0, 100, null, Arrays.asList(Worker.IDENT));
    }

}
