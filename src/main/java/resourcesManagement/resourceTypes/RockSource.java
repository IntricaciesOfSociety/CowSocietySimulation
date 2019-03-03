package resourcesManagement.resourceTypes;

import javafx.scene.image.Image;
import metaEnvironment.Playground;
import terrain.Tile;
import terrain.TileHandler;

/**
 * Creates and handles any rockSource resource.
 */
public class RockSource extends GenericResource {

    /**
     * Calls for the creation of a rockSource
     * @param sourceSprite The sprite to create the rockSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    public RockSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            constructSource(sourceSprite, tileToBuildOn);
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
            region.removeRockSource(this);
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
