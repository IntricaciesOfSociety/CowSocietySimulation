package resourcesManagement;

import cowParts.Cow;
import cowParts.CowHandler;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.BinRegionHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WaterSource;
import resourcesManagement.resourceTypes.WoodSource;
import terrain.Tile;
import terrain.TileHandler;

import java.util.ArrayList;

/**
 * Handles all the resource management for the city.
 */
public class ResourcesHandler {

    private static int rock = 0;
    private static int wood = 0;
    private static int power = 0;

    /**
     * Creates the initial resources for the sim.
     */
    public static void init() {
        for (int i = 0; i < LoadConfiguration.getInitialWaterSources(); i++)
            ResourceCreation.createWaterSource(
                    AssetLoading.basicWatersource,
                    TileHandler.getRandomNotFullTile(TileHandler.getSize(AssetLoading.basicWatersource), AssetLoading.desertTileFull)
            );
        for (int j = 0; j < LoadConfiguration.getInitialSmallRocks(); j++)
            ResourceCreation.createRockSource(
                    AssetLoading.smallRock,
                    TileHandler.getRandomNotFullTile(TileHandler.getSize(AssetLoading.smallRock))
            );
        for (int k = 0; k < LoadConfiguration.getInitialSmallTrees(); k++)
            ResourceCreation.createWoodSource(
                    AssetLoading.smallTree,
                    TileHandler.getRandomNotFullTile(TileHandler.getSize(AssetLoading.smallTree), AssetLoading.mountainTileFull, AssetLoading.desertTileFull)
            );
        for (int q = 0; q < LoadConfiguration.getInitialLargeTrees(); q++)
            ResourceCreation.createWoodSource(
                    AssetLoading.largeTree,
                    TileHandler.getRandomNotFullTile(TileHandler.getSize(AssetLoading.largeTree), AssetLoading.mountainTileFull, AssetLoading.desertTileFull)
            );
        for (int h = 0; h < LoadConfiguration.getInitialLargeRocks(); h++)
            ResourceCreation.createRockSource(
                    AssetLoading.largeRock, TileHandler.getRandomNotFullTile(TileHandler.getSize(AssetLoading.largeRock))
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

        for (int i = 0; i < BinRegionHandler.newestRegionId; i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(i).getAllRockSources());

        return (RockSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }

    public static WoodSource getClosestWoodSource(Cow cowToCheck) {
        ArrayList<Tile> resourceList = new ArrayList<>();

        for (int i = 0; i < BinRegionHandler.newestRegionId; i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(i).getAllWoodSources());

        return (WoodSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }

    public static WaterSource getClosestWaterSource(Cow cowToCheck) {
        ArrayList<Tile> resourceList = new ArrayList<>();

        for (int i = 0; i < BinRegionHandler.newestRegionId; i++)
            resourceList.addAll(BinRegionHandler.binRegionMap.get(i).getAllWaterSources());

        return (WaterSource) TileHandler.getClosestTile(cowToCheck, resourceList);
    }
}
