package infrastructure;

import infrastructure.buildingTypes.GenericBuilding;
import infrastructure.buildingTypes.IndustrialBuilding;
import infrastructure.buildingTypes.ResidentialBuilding;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.BinRegionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.util.LinkedList;

/**
 * Handles the management of infrastructure that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    private static GenericBuilding defaultBuilding;

    private static LinkedList<GenericBuilding> buildQueue = new LinkedList<>();

    /**
     * Creates the necessary infrastructure based off the situation chosen for the sim.
     */
    public static void init() {
        defaultBuilding = BuildingCreation.createBuilding(
                AssetLoading.basicLargeBuilding, LoadConfiguration.getBasicLargeDwelling(),
                Tile.getRandomNotFullTile(4), ResidentialBuilding.class
        );

        BuildingCreation.createBuilding(
                AssetLoading.basicMineBuilding, LoadConfiguration.getBasicMine(),
                Tile.getRandomNotFullTile(2, AssetLoading.desertTileFull, AssetLoading.flatTerrain), IndustrialBuilding.class
        );

        for (int i = 0; i < LoadConfiguration.getGroceryStores(); i++) {
            BuildingCreation.createBuilding(
                    AssetLoading.basicGroceryStoreBuilding, LoadConfiguration.getBasicGroceryStore(),
                    Tile.getRandomNotFullTile(4), ResidentialBuilding.class
            );
        }
    }

    /**
     * Sets the opacity of all active region infrastructure to low.
     */
    public static void highlightBuildings() {
        BinRegionHandler.getActiveRegions().forEach(
                (activeBin) -> activeBin.getAllBuildings().forEach(
                        (building) -> building.setOpacity(0.5)
                )
        );
    }

    /**
     * Sets the opacity of all active region infrastructure back to normal.
     */
    public static void dehighlightBuildings() {
        BinRegionHandler.getActiveRegions().forEach(
                (activeBin) -> activeBin.getAllBuildings().forEach(
                        (building) -> building.setOpacity(1)
                )
        );
    }

    /**TODO: Implement
     * Assigns the given cow to it's building.
     * @param cowID The cow's ID to find the building assignment from
     * @return The building that is being assigned
     */
    @Contract(pure = true)
    public static GenericBuilding getBuildingAssignment(String cowID) {
        return defaultBuilding;
    }

    /**
     * Finds and returns the next building that needs to be constructed. The last element in the list is the newest building
     * and the first element is the oldest building.
     * @return The oldest not constructed building
     */
    @Nullable
    public static GenericBuilding nextToConstruct() {
        return buildQueue.getFirst();
    }
}