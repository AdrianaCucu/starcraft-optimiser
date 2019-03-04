package buildings;

public class Refinery extends Building {

    /**
     * Constructor for Refinery.
     *
     * Mineral cost: 75
     * Gas cost: 0
     * Build Time: 30 seconds
     * Dependency: none
     */
    public Refinery() {
        super(75, 0, 30, null);
    }

}