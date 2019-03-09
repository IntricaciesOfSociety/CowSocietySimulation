package buildings;

import cowParts.Cow;
import javafx.scene.image.ImageView;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles the management of buildings that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    //All buildings
    static ArrayList<Building> buildingsList = new ArrayList<>();

    //Lists of specialized buildings
    static ArrayList<Building> votingPlaces = new ArrayList<>();
    static ArrayList<Building> groceryStores = new ArrayList<>();

    /**
     * Creates the necessary buildings based off the situation chosen for the sim.
     */
    public static void init() {
        new LargeBuilding(AssetLoading.basicLargeBuilding, LoadConfiguration.getBasicLargeDwelling(), Tile.getRandomNotFullTile(4));

        new SmallBuilding(AssetLoading.basicMineBuilding, LoadConfiguration.getBasicMine(), Tile.getRandomNotFullTile(2, AssetLoading.desertTileFull, AssetLoading.flatTerrain));

        for (int i = 0; i < LoadConfiguration.getGroceryStores(); i++) {
            new LargeBuilding(AssetLoading.basicGroceryStoreBuilding, LoadConfiguration.getBasicGroceryStore(), Tile.getRandomNotFullTile(4));
        }
    }

    /**TODO: Implement
     * Demolishes the current building
     */
    public static void destroyBuilding() {
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

    public static ImageView getClosestGroceryStore(Cow cowToCheck) {
        return Building.getClosestBuilding(cowToCheck, groceryStores);
    }

    public static ImageView getClosestVotingArea(Cow cowToCheck) {
        return Building.getClosestBuilding(cowToCheck, votingPlaces);
    }
}