package resourcesManagement;

import javafx.scene.image.Image;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WaterSource;
import resourcesManagement.resourceTypes.WoodSource;
import terrain.Tile;

class ResourceCreation {

    static void createWoodSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            tileToBuildOn.getRegion().addWoodSource(new WoodSource(sourceSprite, tileToBuildOn));
        else
            System.out.println("Cannot place WoodSource");
    }

    static void createRockSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            tileToBuildOn.getRegion().addRockSource(new RockSource(sourceSprite, tileToBuildOn));
        else
            System.out.println("Cannot place RockSource");
    }

    static void createWaterSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            tileToBuildOn.getRegion().addWaterSource(new WaterSource(sourceSprite, tileToBuildOn));
        else
            System.out.println("Cannot place WaterSource");
    }
}
