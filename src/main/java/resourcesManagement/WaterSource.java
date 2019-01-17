package resourcesManagement;

import cowParts.Cow;
import cowParts.cowMovement.DecideActions;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Creates and handles any waterSource resource.
 */
public class WaterSource extends Resource {

    private static ArrayList<WaterSource> wateringHoles = new ArrayList<>();
    private int resourceHealth = 1000;

    /**
     * Calls for the creation of a woodSource
     * @param resourceSprite The sprite to create the waterSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    WaterSource(Image resourceSprite, Tile tileToBuildOn) {
        constructSource(resourceSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructSource(Image waterSourceSprite, Tile tileToBuildOn) {
        this.setImage(waterSourceSprite);

        if (tileToBuildOn != null) {
            if (tileToBuildOn.tieToObject(this, Tile.getSize(waterSourceSprite)))
                addWaterSource(this);
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isDestroyed() {
        return resourceHealth <= 0;
    }

    /**
     * Finds the closest resource to the given cow
     * @param cowToCheck The cow to find the closest resource from
     * @return The closest resource to the given cow
     */
    @Nullable
    public static ImageView getClosestResource(Cow cowToCheck) {
        if (wateringHoles.size() != 0) {
            double smallestDistance = DecideActions.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(0));
            ImageView closestWaterSource = wateringHoles.get(0);

            for(int i = 0; i < wateringHoles.size(); i++) {
                if (DecideActions.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(i)) < smallestDistance) {
                    closestWaterSource = wateringHoles.get(i);
                    smallestDistance = DecideActions.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(i));
                }
            }

            return closestWaterSource;
        }
        else
            return null;
    }

    /**
     * Adds the waterSource to the waterSource list.
     * @param waterHoleResource The resource to add
     */
    private static void addWaterSource(Resource waterHoleResource) {
        wateringHoles.add((WaterSource) waterHoleResource);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deplete(int depleteDelta) {
        resourceHealth -= depleteDelta;

        if (resourceHealth <= 0) {
            wateringHoles.remove(this);
            Playground.playground.getChildren().remove(this);
        }
    }
}