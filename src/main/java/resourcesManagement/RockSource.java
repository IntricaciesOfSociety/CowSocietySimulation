package resourcesManagement;

import cowParts.Cow;
import cowParts.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

public class RockSource extends Tile implements Resource {

    private static ArrayList<RockSource> rockSources = new ArrayList<>();

    RockSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        constructSource(sourceSprite, tileToBuildOn);
    }

    @Override
    public void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(sourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
           addRockSource(this);
    }

    public static ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, rockSources.get(0));
        ImageView closestRockSource = rockSources.get(0);

        for(int i = 0; i < rockSources.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, rockSources.get(i)) < smallestDistance)
                closestRockSource = rockSources.get(i);
        }
        return closestRockSource;
    }

    private static void addRockSource(Resource resource) {
        rockSources.add((RockSource) resource);
    }
}
