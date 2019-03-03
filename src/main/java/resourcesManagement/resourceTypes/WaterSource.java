package resourcesManagement.resourceTypes;

import javafx.scene.image.Image;
import metaEnvironment.Playground;
import terrain.Tile;
import terrain.TileHandler;

/**
 * Creates and handles any waterSource resource.
 */
public class WaterSource extends GenericResource {

    /**
     * Calls for the creation of a woodSource
     * @param resourceSprite The sprite to create the waterSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    public WaterSource(Image resourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            constructSource(resourceSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn.tieToObject(this, TileHandler.getSize(sourceSprite))) {
            this.resourceHealth = TileHandler.getSize(sourceSprite) * 25;
            this.region = tileToBuildOn.getRegion();
            this.setImage(sourceSprite);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deplete(int depleteDelta) {
        resourceHealth -= depleteDelta;

        if (resourceHealth <= 0) {
            region.removeWaterSource(this);
            Playground.playground.getChildren().remove(this);
        }

    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isDestroyed() {
        return resourceHealth <= 0;
    }
}