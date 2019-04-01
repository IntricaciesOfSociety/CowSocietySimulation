package metaEnvironment.Regioning.regionContainers;

import infrastructure.buildings.buildingTypes.GenericBuilding;
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
    private GenericBuilding defaultBuilding;

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

    public void setDefaultBuilding(GenericBuilding building) {
        defaultBuilding = building;
    }

    public GenericBuilding getDefaultBuilding() {
        return defaultBuilding;
    }
}