package resourcesManagement;

import cowParts.cowMovement.DecideActions;
import cowParts.Cow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metaEnvironment.Playground;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Creates and handles any woodSource resource.
 */
public class WoodSource extends Resource {

    private static ArrayList<WoodSource> woodSources = new ArrayList<>();

    /**
     * Calls for the creation of a woodSource
     * @param sourceSprite The spirte to create the woodSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    WoodSource(Image sourceSprite, Tile tileToBuildOn) {
        resourceHealth = Tile.getSize(sourceSprite) * 25;

        if (tileToBuildOn != null)
            constructSource(sourceSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(sourceSprite);

        if (tileToBuildOn.tieToObject(this, Tile.getSize(sourceSprite)))
            addWoodSource(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isDestroyed() {
        return resourceHealth <= 0;
    }

    /**
     * Returns the closest woodSource to the given cow
     * @param cowToCheck The cow to check for closest woodSource to
     * @return The closest woodSource
     */
    @Nullable
    public static ImageView getClosestResource(Cow cowToCheck) {
        if (woodSources.size() != 0) {
            double smallestDistance = DecideActions.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(0));
            ImageView closestRockSource = woodSources.get(0);

            for(int i = 0; i < woodSources.size(); i++) {
                if (DecideActions.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(i)) < smallestDistance) {
                    closestRockSource = woodSources.get(i);
                    smallestDistance = DecideActions.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(i));
                }
            }
            return closestRockSource;
        }
        else {
            return null;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void deplete(int depleteDelta) {
        resourceHealth -= depleteDelta;

        if (resourceHealth <= 0) {
            woodSources.remove(this);
            Playground.playground.getChildren().remove(this);
        }
    }

    /**
     * Adds the woodSource to the woodResource list.
     * @param resource The resource to add
     */
    private static void addWoodSource(Resource resource) {
        woodSources.add((WoodSource) resource);
    }
}
