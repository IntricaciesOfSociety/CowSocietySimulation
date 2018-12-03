package resourcesManagement;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import terrain.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * TODO: Implement proper water object
 * TEST ONLY, TEMPORARY.
 * Creates a rectangle that cows move to when their hunger is 0.
 */
public class WaterSource extends ImageView{

    private static ArrayList<WaterSource> wateringHoles = new ArrayList<>();
    /**
     * Creates and moves the food object.
     */
    public void createWateringHole() {
        try {
            this.setImage(new Image(new FileInputStream("src/main/resources/Environment/WateringHole.png")));
        }
        catch (FileNotFoundException error) {
            MenuHandler.createErrorMenu();
        }

        this.setX(0);
        this.setY(0);
        Tile.tieToWaterSource(this, Tile.getRandomTile());

        wateringHoles.add(this);
        Playground.playground.getChildren().add(this);
    }

    @Contract(pure = true)
    public static WaterSource getWateringHole() {
        return wateringHoles.get(0);
    }
}