package infrastructure;

import cowParts.Cow;
import infrastructure.buildingTypes.CommercialBuilding;
import infrastructure.buildingTypes.GenericBuilding;
import infrastructure.buildingTypes.GovernmentalBuilding;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.BinRegionHandler;
import org.jetbrains.annotations.Contract;
import resourcesManagement.resourceTypes.RockSource;
import technology.CurrentEraTechnology;
import terrain.Tile;
import terrain.TileHandler;

import java.util.ArrayList;

/**
 * Handles the management of infrastructure that have been given context within the simulation. Does not handle the building's
 * menus.
 */
public class BuildingHandler {

    private static GenericBuilding defaultBuilding;


    /**
     * Creates the necessary infrastructure based off the situation chosen for the sim.
     */
    public static void init() {
        defaultBuilding = BuildingCreation.createResidentialBuilding(
                AssetLoading.basicLargeBuilding, LoadConfiguration.getBasicLargeDwelling(), TileHandler.getRandomNotFullTile(4)
        );

        BuildingCreation.createIndustrialBuilding(
                AssetLoading.basicMineBuilding, LoadConfiguration.getBasicMine(),
                TileHandler.getRandomNotFullTile(2, AssetLoading.desertTileFull, AssetLoading.flatTerrain)
        );

        for (int i = 0; i < LoadConfiguration.getGroceryStores(); i++) {
            BuildingCreation.createCommercialBuilding(
                    AssetLoading.basicGroceryStoreBuilding, LoadConfiguration.getBasicGroceryStore(), TileHandler.getRandomNotFullTile(4)
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

    private static boolean checkBuildingName(GenericBuilding building, String buildingName) {
        return building.getId().equals(buildingName);
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

    public static GenericBuilding getDefaultBuilding() {
        return defaultBuilding;
    }

    public static GenericBuilding getClosestGroceryStore(Cow cowToCheck) {
        ArrayList<Tile> buildingList = new ArrayList<>();

        for (int i = 0; i < BinRegionHandler.newestRegionId; i++)
            BinRegionHandler.binRegionMap.get(i).getAllCommercialBuildings().forEach(
                (building) -> { if (BuildingHandler.checkBuildingName(building, CurrentEraTechnology.getGroceryStoreName())) buildingList.add(building); }
            );

        return (CommercialBuilding) TileHandler.getClosestTile(cowToCheck, buildingList);
    }

    public static GenericBuilding getClosestVotingArea(Cow cowToCheck) {
        ArrayList<Tile> buildingList = new ArrayList<>();

        for (int i = 0; i < BinRegionHandler.newestRegionId; i++)
            buildingList.addAll(BinRegionHandler.binRegionMap.get(i).getAllGovernmentalBuildings());

        return (GovernmentalBuilding) TileHandler.getClosestTile(cowToCheck, buildingList);
    }
}