package buildings;

import javafx.scene.image.Image;
import menus.MenuHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Handles the management of buildings that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    static Image smallUnderConstructionSprite;
    static Image largeUnderConstructionSprite;

    static ArrayList<Building> buildingsList = new ArrayList<>();

    /**
     * Creates the necessary buildings based off the situation chosen for the sim.
     */
    public static void init() {
        loadSmallUnderConstructionSprite();
        loadLargeUnderConstructionSprite();
        new Hotel(loadSprite("CowHotel"), Tile.getRandomTerrainTile());
    }

    private static void loadLargeUnderConstructionSprite() {
        largeUnderConstructionSprite = loadSprite("LargeUnderConstruction");
    }

    private static void loadSmallUnderConstructionSprite() {
        smallUnderConstructionSprite = loadSprite("SmallUnderConstruction");
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
        for (Building building : buildingsList) {
            building.getBuildingAsBuildingTile().setOpacity(0.5);
        }
    }

    /**
     * Sets the opacity of all drawn buildings back to normal.
     */
    public static void dehighlightBuildings() {
        for (Building building : buildingsList) {
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
        return buildingsList.get(0);
    }

    @Nullable
    public static Building nextHouseToConstruct() {
        for (int i = 0; i < buildingsList.size(); i++) {
            if (!buildingsList.get(i).isConstructed())
                return buildingsList.get(i);
        }
        return null;
    }

    @Contract(pure = true)
    public static Building getDefaultBuilding() {
        return buildingsList.get(0);
    }
}