package units;

import buildings.*;

public class Marine extends Unit {
    /**
     * Marine constructor.
     */
    public Marine() {
        super(50, 0, 25, null, new Barracks());
    }
}