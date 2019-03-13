package base;

import java.util.ArrayList;

public class Base {
    private ArrayList<MineralPatch> patches;
    private ArrayList<Geyser> geysers;

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


}
