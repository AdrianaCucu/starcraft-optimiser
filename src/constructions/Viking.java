package constructions;

public class Viking extends Unit {

    public static final String IDENT = "Viking";

    /**
     * Viking constructor.
     */
    public Viking() {
        super(150, 75, 42, null, new Starport());
    }


}