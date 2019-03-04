package buildings;

public class Factory extends Building {

    /**
     * Constructor for Factory.
     *
     * Mineral cost: 150
     * Gas cost: 100
     * Build Time: 60 seconds
     * Dependency: Barracks
     */
    public Factory() {
        super(150, 100, 60, new Barracks());
    }

}
