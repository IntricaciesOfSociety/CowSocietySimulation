package buildings;

import cowMovement.Movement;
import cowParts.Cow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
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

    //A list of every building that is built or being constructed.
    static ArrayList<Building> buildingsList = new ArrayList<>();

    /**
     * Creates the necessary buildings based off the situation chosen for the sim.
     */
    public static void init() {
        loadSmallUnderConstructionSprite();
        loadLargeUnderConstructionSprite();

        new LargeDwelling(AssetLoading.basicLargeBuilding, LoadConfiguration.getBasicLargeDwelling(), Tile.getRandomNonBuiltUponTerrainTile());
    }

    /**
     * Loads the sprite for the large under construction sprite into the corresponding field
     */
    private static void loadLargeUnderConstructionSprite() {
        largeUnderConstructionSprite = loadSprite("LargeUnderConstruction");
    }

    /**
     * Loads the sprite for the small under construction sprite into the corresponding field
     */
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

    /**
     * Finds and returns the next building that needs to be constructed. The last element in the list is the newest building
     * and the first element is the oldest building.
     * @return The newest not constructed building
     */
    @Nullable
    public static Building nextHouseToConstruct() {
        for (int i = 0; i < buildingsList.size(); i++) {
            if (!buildingsList.get(i).isConstructed())
                return buildingsList.get(i);
        }
        return null;
    }

    /**
     * @return The oldest building (The first building constructed).
     */
    @Contract(pure = true)
    public static Building getDefaultBuilding() {
        return buildingsList.get(0);
    }

    @Nullable
    public static ImageView getClosestVotingArea(Cow cowToCheck) {
        ArrayList<Building> possibleVotingBuildings = new ArrayList<>();
        for (int i = 0; i < buildingsList.size(); i++) {
            if (buildingsList.get(i).isConstructed() && buildingsList.get(i).isVotingPlace())
                possibleVotingBuildings.add(buildingsList.get(i));
        }

        if (possibleVotingBuildings.size() > 0) {
            double smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, possibleVotingBuildings.get(0));;
            ImageView closestVotingBuilding = null;
            for (int j = 0; j < possibleVotingBuildings.size(); j++) {
                closestVotingBuilding = possibleVotingBuildings.get(0);

                if (Movement.findDistanceBetweenCowAndObject(cowToCheck, possibleVotingBuildings.get(j)) < smallestDistance) {
                    smallestDistance = Movement.findDistanceBetweenCowAndObject(cowToCheck, possibleVotingBuildings.get(j));
                    closestVotingBuilding = possibleVotingBuildings.get(j);
                }
            }
            return closestVotingBuilding;
        }
        else {
            return null;
        }
    }
}