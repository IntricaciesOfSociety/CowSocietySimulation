package buildings;

import javafx.scene.image.Image;
import menus.MenuHandler;
import org.jetbrains.annotations.Contract;
import terrain.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Handles the management of buildings that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    static Image constructionSprite;

    static ArrayList<Building> constructedBuildings = new ArrayList<>();

    /**
     * Creates the necessary buildings based off the situation chosen for the sim.
     */
    public static void init() {
        loadConstructionSprite();
        new SmallDwelling(loadSprite("CowShack"), Tile.getRandomTerrainTile());
    }

    private static void loadConstructionSprite() {
        constructionSprite = loadSprite("UnderConstruction");
    }

    /**
     * Finds the image corresponding to the given string.
     * @param imageName The name of the image to have a building created from
     * @return The new building
     */
    public static Image loadSprite(String imageName) {
        Image buildingSprite = null;
        try {
            buildingSprite = new Image(new FileInputStream("src/main/resources/Buildings/" + imageName + ".png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            MenuHandler.createErrorMenu();
        }
        return buildingSprite;
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
            building.getBuildingAsBuildingTile().setOpacity(0.5);
        }
    }

    /**
     * Sets the opacity of all drawn buildings back to normal.
     */
    public static void dehighlightBuildings() {
        for (Building building : constructedBuildings) {
            building.getBuildingAsBuildingTile().setOpacity(1);
        }
    }

    /**TODO: Implement
     * Assigns the given cow to it's building.
     * @param cowIDToAssign The cow's ID to find the building assignment from
     * @return The building that is being assigned
     */
    @Contract(pure = true)
    public static Building getBuildingAssignment(String cowIDToAssign) {
        return constructedBuildings.get(0);
    }
}