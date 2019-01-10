package resourcesManagement;

import cowParts.CowHandler;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

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
        for (int i = 0; i < LoadConfiguration.getInitialWaterSources(); i++) {
            new WaterSource(AssetLoading.basicWatersource, Tile.getRandomNonBuiltUponTerrainTile(Tile.getSize(AssetLoading.basicWatersource)));
        }

        for (int j = 0; j < LoadConfiguration.getInitialRocks(); j++) {
            new RockSource(AssetLoading.basicRock, Tile.getRandomNonBuiltUponTerrainTile(Tile.getSize(AssetLoading.basicRock)));
        }

        for (int k = 0; k < LoadConfiguration.getInitialTrees(); k++) {
            new WoodSource(AssetLoading.basicTree, Tile.getRandomNonBuiltUponTerrainTile(Tile.getSize(AssetLoading.basicTree)));
        }

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
}
