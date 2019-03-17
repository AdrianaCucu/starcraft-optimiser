package base;

import java.util.ArrayList;

public class Base {
    private ArrayList<MineralPatch> patches;
    private ArrayList<Geyser> geysers;

    public ArrayList<MineralPatch> getPatches() {
        return patches;
    }

    public Base() {
        patches = new ArrayList<>();
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());
        patches.add(new MineralPatch());

        geysers = new ArrayList<>();
        geysers.add(new Geyser());
        geysers.add(new Geyser());
    }

    /**
     * Checks for best spot to assign worker.
     * @return
     */
    public BaseUnit checkBasicUnit() {
        return patches.get(1);
    }


}
