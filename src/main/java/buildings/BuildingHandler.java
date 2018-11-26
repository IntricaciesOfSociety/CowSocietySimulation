package buildings;

import javafx.scene.image.Image;
import menus.MenuHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Handles the management of buildings that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    private static ArrayList<Building> constructedBuildings = new ArrayList<>();

    /**
     * Finds the image corresponding to the given string.
     * @param imageName The name of the image to have a building created from.
     */
    public static void createBuilding(String imageName) {
        Image buildingSprite = null;
        try {
            buildingSprite = new Image(new FileInputStream("src/main/resources/Buildings/" + imageName + ".png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            MenuHandler.createErrorMenu();
        }
        constructedBuildings.add(new Building(buildingSprite));
    }

    /**TODO: Implement
     * Demolishes the current building
     */
    public static void removeBuilding() {
    }

    /**
     * Sets the opacity of all drawn buildings to low.
     */
    public static void highlightBuildings() {
        for (Building building : constructedBuildings) {
            building.setOpacity(0.5);
        }
    }

    /**
     * Sets the opacity of all drawn buildings back to normal.
     */
    public static void dehighlightBuildings() {
        for (Building building : constructedBuildings) {
            building.setOpacity(1);
        }
    }
}
