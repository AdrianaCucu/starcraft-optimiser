package units;

import buildings.*;

public class Worker extends Unit {

    /**
     * Worker constructor.
     */
    public Worker() {
        super(50, 0, 17, null, new CommandCenter());
    }
}