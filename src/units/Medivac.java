package units;

import buildings.*;


public class Medivac extends Unit{

    public static final String IDENT = "Medivac";

    /**
     * Medivac constructor.
     */
    public Medivac() {
        super(100 ,100, 42, null, new Starport());
    }

}