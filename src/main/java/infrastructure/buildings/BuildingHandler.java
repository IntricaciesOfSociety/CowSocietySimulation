package infrastructure.buildings;

import cowParts.Cow;
import infrastructure.buildings.buildingTypes.CommercialBuilding;
import infrastructure.buildings.buildingTypes.GenericBuilding;
import infrastructure.buildings.buildingTypes.GovernmentalBuilding;
import infrastructure.buildings.buildingTypes.IndustrialBuilding;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.BinRegionHandler;
import metaEnvironment.Regioning.regionContainers.Playground;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import technology.CurrentTechnology;
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
                AssetLoading.basicLargeBuilding, LoadConfiguration.getBasicLargeDwelling(), TileHandler.getRandRegionTile(4, PlaygroundHandler.getMotion())
        );

        for (int j = 0; j < 100; j++)
            BuildingCreation.createIndustrialBuilding(
                    AssetLoading.basicMineBuilding, LoadConfiguration.getBasicMine(),
                    TileHandler.getRandRegionTile(2, PlaygroundHandler.getMotion(), AssetLoading.desertTileFull, AssetLoading.flatTerrain)
            );

        //TODO: Remove size from creation methods
        for (int k = 0; k < LoadConfiguration.getGroceryStores(); k++)
            BuildingCreation.createCommercialBuilding(
                    AssetLoading.basicGroceryStoreBuilding, LoadConfiguration.getBasicGroceryStore(), TileHandler.getRandRegionTile(4, PlaygroundHandler.getMotion())
            );

        IndustrialBuilding mineExit = (IndustrialBuilding) BuildingCreation.createIndustrialBuilding(
                AssetLoading.mineExit, "MineExit",
                TileHandler.getRandRegionTile(2, PlaygroundHandler.getMines(), AssetLoading.desertTileFull, AssetLoading.flatTerrain)
        );
        if (mineExit != null) {
            mineExit.finishConstruction();
            mineExit.disableMenu();
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
     * Adds the given cow to the given building and updates that cow's animation accordingly.
     * @param cowToMove The cow to be added into the building
     * @param buildingToMoveInto The building to add the cow into
     */
    public static void enterBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToMoveInto) {
        cowToMove.hide();
        cowToMove.setBuildingIn((GenericBuilding) buildingToMoveInto);

        ((GenericBuilding) buildingToMoveInto).addInhabitant(cowToMove);
    }

    /**
     * Removes the cow from the building that it is in.
     * @param cowToMove The cow to remove from the building
     * @param buildingToExitFrom The building to remove the given cow from
     */
    public static void exitBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToExitFrom) {
        ((GenericBuilding)buildingToExitFrom).removeInhabitant(cowToMove);
        cowToMove.setBuildingIn(null);

        cowToMove.setTranslateX(buildingToExitFrom.getLayoutX() + buildingToExitFrom.getRegion().getLayoutX() + buildingToExitFrom.getImage().getWidth() / 2);
        cowToMove.setTranslateY(buildingToExitFrom.getLayoutY() + buildingToExitFrom.getRegion().getLayoutY() + buildingToExitFrom.getImage().getHeight() + 75);

        cowToMove.show();
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
        Playground resourcePool = cowToCheck.getRegionIn().getPlayground();

        for (int i = 0; i < (resourcePool.getMaxBinRegionId() - resourcePool.getMinBinRegionId()); i++)
            BinRegionHandler.binRegionMap.get(resourcePool.getMinBinRegionId() + i).getAllCommercialBuildings().forEach(
                (building) -> { if (BuildingHandler.checkBuildingName(building, CurrentTechnology.getGroceryStoreName())) buildingList.add(building); }
            );

        return (CommercialBuilding) TileHandler.getClosestTile(cowToCheck, buildingList);
    }

    public static GenericBuilding getClosestVotingArea(Cow cowToCheck) {
        ArrayList<Tile> buildingList = new ArrayList<>();
        Playground resourcePool = cowToCheck.getRegionIn().getPlayground();

        for (int i = 0; i < (resourcePool.getMaxBinRegionId() - resourcePool.getMinBinRegionId()); i++)
            buildingList.addAll(BinRegionHandler.binRegionMap.get(resourcePool.getMinBinRegionId() + i).getAllGovernmentalBuildings());

        return (GovernmentalBuilding) TileHandler.getClosestTile(cowToCheck, buildingList);
    }
}