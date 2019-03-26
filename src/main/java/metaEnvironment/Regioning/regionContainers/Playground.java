package metaEnvironment.Regioning.regionContainers;

import javafx.scene.layout.*;

/**
 * Creates and handles the switching of the panes within the main window (the playground).
 */
public class Playground extends Pane {

    private int regionId;
    private int minBinRegionId;
    private int maxBinRegionId;

    Playground(int id) {
        this.regionId = id;
    }

    public void setBinRegionIds(int min, int max) {
        minBinRegionId = min;
        maxBinRegionId = max;
    }

    int getMinBinRegionId() {
        return minBinRegionId;
    }

    int getMaxBinRegionId() {
        return maxBinRegionId;
    }

    public int getRegionId() {
        return regionId;
    }
}