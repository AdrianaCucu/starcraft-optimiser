package constructions;


public class Hellion extends Unit{

    public static final String IDENT = "Hellion";

    /**
     * Hellion constructor.
     */
    public Hellion() {
        super(50, 0, 25, null, new Barracks());
    }
}
