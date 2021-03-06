package resourcesManagement.resourceTypes;

import javafx.scene.image.Image;
import metaControl.metaEnvironment.AssetLoading;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import infrastructure.terrain.Tile;
import infrastructure.terrain.TileHandler;

import java.util.Random;

/**
 * Creates and handles any woodSource resource.
 */
public class WoodSource extends GenericResource {

    /**
     * Calls for the creation of a woodSource
     * @param sourceSprite The spirte to create the woodSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    public WoodSource(Image sourceSprite, Tile tileToBuildOn) {
        if (tileToBuildOn != null)
            constructSource(sourceSprite, tileToBuildOn);
    }

    public static void repopulate() {
        int popIncrease = new Random().nextInt(100);
        for (int i = 0; i < popIncrease; i++) {
            new WoodSource(AssetLoading.smallTree, TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.smallTree), PlaygroundHandler.playground, AssetLoading.mountainTileFull, AssetLoading.desertTileFull));
        }
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
            region.removeWoodSource(this);
            this.region.getPlayground().getChildren().remove(this);
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
