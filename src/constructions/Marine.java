package constructions;


public class Marine extends Unit {

    public static final String IDENT = "Marine";

    /**
     * Marine constructor.
     */
    public Marine() {
        super(50, 0, 25, null, Barracks.IDENT);
    }
}