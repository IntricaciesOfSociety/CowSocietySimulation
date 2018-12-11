package resourcesManagement;

import cowParts.Cow;
import cowMovement.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Creates and handles any woodSource resource.
 */
public class WoodSource extends Tile implements Resource {

    private static ArrayList<WoodSource> woodSources = new ArrayList<>();

    /**
     * Calls for the creation of a woodSource
     * @param sourceSprite The spirte to create the woodSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    WoodSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        constructSource(sourceSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(sourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
            addWoodSource(this);
    }

    /**
     * Returns the closest woodSource to the given cow
     * @param cowToCheck The cow to check for closest woodSource to
     * @return The closest woodSource
     */
    public static ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(0));
        ImageView closestRockSource = woodSources.get(0);

        for(int i = 0; i < woodSources.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(i)) < smallestDistance)
                closestRockSource = woodSources.get(i);
        }
        return closestRockSource;
    }

    /**
     * Adds the woodSource to the woodResource list.
     * @param resource The resource to add
     */
    private static void addWoodSource(Resource resource) {
        woodSources.add((WoodSource) resource);
    }
}
