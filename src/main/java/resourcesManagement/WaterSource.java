package resourcesManagement;

import cowParts.Cow;
import cowParts.Movement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

public class WaterSource extends Tile implements Resource {

    private static ArrayList<WaterSource> wateringHoles = new ArrayList<>();

    WaterSource(Image resourceSprite, Tile tileToBuildOn) {
        constructSource(resourceSprite, tileToBuildOn);
    }

    @Override
    public void constructSource(Image waterSourceSprite, @NotNull Tile tileToBuildOn) {
        this.setImage(waterSourceSprite);

        if (tileToBuildOn.tieToObject(this, 1))
            addWaterSource(this);
    }

    public static ImageView getClosestResource(Cow cowToCheck) {
        double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(0));
        ImageView closestRockSource = wateringHoles.get(0);

        for(int i = 0; i < wateringHoles.size(); i++) {
            if (Movement.findDistanceBetweenCowAndObject(cowToCheck, wateringHoles.get(i)) < smallestDistance)
                closestRockSource = wateringHoles.get(i);
        }
        return closestRockSource;
    }

    private static void addWaterSource(Resource waterHoleResource) {
        wateringHoles.add((WaterSource) waterHoleResource);
    }

    /**
     * @return Returns the default watering hole
     */
    @Contract(pure = true)
    public static WaterSource getWateringHole() {
        return wateringHoles.get(0);
    }
}