package buildings;

public class Starport extends Building {

    /**
     * Constructor for Starport.
     *
     * Mineral cost: 150
     * Gas cost: 100
     * Build Time: 50 seconds
     * Dependency: Factory
     */
    public Starport() {
        super(150, 100, 50, new Factory());
    }

}
