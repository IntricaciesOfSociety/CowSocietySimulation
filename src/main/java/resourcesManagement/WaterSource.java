package resourcesManagement;

import cowParts.Cow;
import cowMovement.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Creates and handles any waterSource resource.
 */
public class WaterSource extends Resource {

    private static ArrayList<WaterSource> wateringHoles = new ArrayList<>();
    private int resourceHealth = 10000;

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
    public void constructSource(Image waterSourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(waterSourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
            addWaterSource(this);
    }

    /**
     * Finds the closest resource to the given cow
     * @param cowToCheck The cow to find the closest resource from
     * @return The closest resource to the given cow
     */
    public ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(0));
        ImageView closestRockSource = wateringHoles.get(0);

        for(int i = 0; i < wateringHoles.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(i)) < smallestDistance)
                closestRockSource = wateringHoles.get(i);
        }
        return closestRockSource;
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

    /**
     * @return Returns the default watering hole
     */
    @Contract(pure = true)
    public static WaterSource getWateringHole() {
        return wateringHoles.get(0);
    }
}