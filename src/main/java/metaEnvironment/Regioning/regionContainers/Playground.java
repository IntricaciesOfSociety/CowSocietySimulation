package metaEnvironment.Regioning.regionContainers;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.*;

/**
 * Creates and handles the switching of the panes within the main window (the playground).
 */
public class Playground extends Pane {

    private int regionId;
    private int minBinRegionId;
    private int maxBinRegionId;
    private ColorAdjust brightnessAdjust = new ColorAdjust();

    Playground(int id) {
        this.regionId = id;
        this.setEffect(brightnessAdjust);
    }

    public void setBinRegionIds(int min, int max) {
        minBinRegionId = min;
        maxBinRegionId = max;
    }

    public void setBrightness(double newBrightness) {
        brightnessAdjust.setBrightness(newBrightness);
    }

    public int getMinBinRegionId() {
        return minBinRegionId;
    }

    public int getMaxBinRegionId() {
        return maxBinRegionId;
    }

    public int getRegionId() {
        return regionId;
    }
}