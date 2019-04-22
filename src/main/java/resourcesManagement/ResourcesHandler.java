package resourcesManagement;

import cowParts.creation.Cow;
import cowParts.CowHandler;
import metaControl.metaEnvironment.LoadConfiguration;
import metaControl.metaEnvironment.AssetLoading;
import metaControl.metaEnvironment.Regioning.BinRegionHandler;
import metaControl.metaEnvironment.Regioning.regionContainers.Playground;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WaterSource;
import resourcesManagement.resourceTypes.WoodSource;
import infrastructure.terrain.Tile;
import infrastructure.terrain.TileHandler;

import java.util.ArrayList;

/**
 * Handles all the resource management for the city.
 */
public class ResourcesHandler {

    private static int rock = 0;
    private static int wood = 0;
    private static int clay = 0;
    private static int coal = 0;
    private static int copper = 0;
    private static int iron = 0;
    private static int power = 0;

    /**
     * Creates the initial resources for the sim.
     */
    public static void init() {
        for (int i = 0; i < LoadConfiguration.getInitialWaterSources(); i++)
            ResourceCreation.createWaterSource(
                    AssetLoading.basicWatersource,
                    TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.basicWatersource), PlaygroundHandler.getMotion(), AssetLoading.desertTileFull)
            );
        for (int j = 0; j < LoadConfiguration.getInitialSmallRocks(); j++)
            ResourceCreation.createRockSource(
                    AssetLoading.smallRock,
                    TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.smallRock), PlaygroundHandler.getMotion())
            );
        for (int k = 0; k < LoadConfiguration.getInitialSmallTrees(); k++)
            ResourceCreation.createWoodSource(
                    AssetLoading.smallTree,
                    TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.smallTree), PlaygroundHandler.getMotion(), AssetLoading.mountainTileFull, AssetLoading.desertTileFull)
            );
        for (int q = 0; q < LoadConfiguration.getInitialLargeTrees(); q++)
            ResourceCreation.createWoodSource(
                    AssetLoading.largeTree,
                    TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.largeTree), PlaygroundHandler.getMotion(), AssetLoading.mountainTileFull, AssetLoading.desertTileFull)
            );
        for (int h = 0; h < LoadConfiguration.getInitialLargeRocks(); h++)
            ResourceCreation.createRockSource(
                    AssetLoading.largeRock, TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.largeRock), PlaygroundHandler.getMotion())
            );

        for (int a = 0; a < LoadConfiguration.getInitialClay(); a++)
            ResourceCreation.createRockSource(
                    AssetLoading.clay, TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.clay), PlaygroundHandler.getMines())
            );
        for (int b = 0; b < LoadConfiguration.getInitialCopper(); b++)
            ResourceCreation.createRockSource(
                    AssetLoading.copper, TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.copper), PlaygroundHandler.getMines())
            );
        for (int c = 0; c < LoadConfiguration.getInitialIron(); c++)
            ResourceCreation.createRockSource(
                    AssetLoading.iron, TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.iron), PlaygroundHandler.getMines())
            );
    }

    /**
     * Changes the city resource based off the given resource.
     * @param resourceType The resource to modify
     * @param amountToModify The amount that the resource is modified by
     */
    public static void modifyResource(@NotNull String resourceType, int amountToModify) {
        switch (resourceType) {
            case "rock":
                rock += (rock + amountToModify >= 0) ? amountToModify : -rock; break;
            case "wood":
                wood += (wood + amountToModify >= 0) ? amountToModify : -wood; break;
            case "power":
                power += (power + amountToModify >= 0) ? amountToModify : -power; break;
        }
    }

    @Contract(pure = true)
    public static int getRockAmount() {
        return rock;
    }

    @Contract(pure = true)
    public static int getWoodAmount() {
        return wood;
    }

    @Contract(pure = true)
    public static int getPowerAmount() {
        return power;
    }

    public static int getClayAmount() { return clay; }

    public static int getCoalAmount() { return coal; }

    public static int getCopperAmount() { return copper; }

    public static int getIronAmount() { return iron; }

    /**
     * Consumes city resources to build towards a buliding requirement.
     * @param buildingRequirement The building requirement to change
     * @param resourceContribution The resource being contributed
     * @param repurposeAmount The amount of the resource being contributed
     */
    public static void repurposeResource(@NotNull ResourceRequirement buildingRequirement, String resourceContribution, int repurposeAmount) {
        modifyResource(resourceContribution, -repurposeAmount);
        buildingRequirement.modifyRequirement(resourceContribution, -repurposeAmount);
    }

    /**
     * @return Every city resource and its amount as a string
     */
    @NotNull
    @Contract(pure = true)
    public static String getResourcesAsString() {
        return "Rock:" + rock + " Wood:" + wood + " Power:" + power;
    }

    /**
     * Updates the city's power resource.
     */
    public static void updatePower() {
        power = CowHandler.liveCowList.size() * 10;
    }

    public static RockSource getClosestRockSource(Cow cowToCheck) {
        ArrayList<Tile> resourceList = new ArrayList<>();
        Playground resourcePool = cowToCheck.getRegionIn().getPlayground();

        for (int i = 0; i < (resourcePool.getMaxBinRegionId() - resourcePool.getMinBinRegionId()); i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(resourcePool.getMinBinRegionId() + i).getAllRockSources());

        return (RockSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }

    public static WoodSource getClosestWoodSource(Cow cowToCheck) {
        ArrayList<Tile> resourceList = new ArrayList<>();
        Playground resourcePool = cowToCheck.getRegionIn().getPlayground();

        for (int i = 0; i < (resourcePool.getMaxBinRegionId() - resourcePool.getMinBinRegionId()); i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(resourcePool.getMinBinRegionId() + i).getAllWoodSources());

        return (WoodSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }

    public static WaterSource getClosestWaterSource(Cow cowToCheck) {
        ArrayList<Tile> resourceList = new ArrayList<>();
        Playground resourcePool = cowToCheck.getRegionIn().getPlayground();

        for (int i = 0; i < (resourcePool.getMaxBinRegionId() - resourcePool.getMinBinRegionId()); i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(resourcePool.getMinBinRegionId() + i).getAllWaterSources());

        return (WaterSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }
}
