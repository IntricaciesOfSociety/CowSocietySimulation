package resourcesManagement;

import cowParts.Cow;
import cowParts.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

public class WoodSource extends Tile implements Resource {

    private static ArrayList<WoodSource> woodSources = new ArrayList<>();

    WoodSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        constructSource(sourceSprite, tileToBuildOn);
    }

    @Override
    public void constructSource(Image sourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(sourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
            addWoodSource(this);
    }

    public static ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(0));
        ImageView closestRockSource = woodSources.get(0);

        for(int i = 0; i < woodSources.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, woodSources.get(i)) < smallestDistance)
                closestRockSource = woodSources.get(i);
        }
        return closestRockSource;
    }

    private static void addWoodSource(Resource resource) {
        woodSources.add((WoodSource) resource);
    }
}
