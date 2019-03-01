package infrastructure;

import infrastructure.buildingTypes.GenericBuilding;
import javafx.scene.image.Image;
import terrain.Tile;
import java.lang.reflect.InvocationTargetException;

/**
 * Handles the creation of large dwelling infrastructure. Called only if building prerequisites have been fulfilled
 * (resources and technology).
 */
public class BuildingCreation {

    /**
     * Calls for the creation of a building given an image, name, and tile. Determines which building to build based off
     * of class name
     * @param buildingSprite The image to create a building from
     * @param name The name of the building
     * @param tileToBuildOn The tile that the building will be built on
     */
    public static GenericBuilding createBuilding(Image buildingSprite, String name, Tile tileToBuildOn, Class buildingType) {
        if (tileToBuildOn != null) {
            try {
                return (GenericBuilding) buildingType.getConstructor(Image.class, String.class, Tile.class).newInstance(
                        buildingSprite, name, tileToBuildOn
                );
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("Cannot construct " + name);
        return null;
    }
}
