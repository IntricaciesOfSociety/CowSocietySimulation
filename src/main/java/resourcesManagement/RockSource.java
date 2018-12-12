package resourcesManagement;

import cowParts.Cow;
import cowMovement.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metaEnvironment.Playground;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Creates and handles any rockSource resource.
 */
public class RockSource extends Tile implements Resource {

    //How much the resource can be mined.
    private int resourceHealth = 100;

    private static ArrayList<RockSource> rockSources = new ArrayList<>();

    /**
     * Calls for the creation of a rockSource
     * @param sourceSprite The sprite to create the rockSource from
     * @param tileToBuildOn The tile to build the source upon
     */
    RockSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        constructSource(sourceSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(sourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
           addRockSource(this);
    }

    @Override
    public void deplete(int depleteDelta) {
        resourceHealth -= depleteDelta;

        if (resourceHealth <= 0) {
            rockSources.remove(this);
            Playground.playground.getChildren().remove(this);
        }
    }

    /**
     * Finds the closest resource to the given cow
     * @param cowToCheck The cow to find the closest resource from
     * @return The closest resource to the given cow
     */
    public static ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, rockSources.get(0));
        ImageView closestRockSource = rockSources.get(0);

        for(int i = 0; i < rockSources.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, rockSources.get(i)) < smallestDistance)
                closestRockSource = rockSources.get(i);
        }
        return closestRockSource;
    }

    /**
     * Adds the rockSource to the rockSource list.
     * @param resource The resource to add
     */
    private static void addRockSource(Resource resource) {
        rockSources.add((RockSource) resource);
    }
}
