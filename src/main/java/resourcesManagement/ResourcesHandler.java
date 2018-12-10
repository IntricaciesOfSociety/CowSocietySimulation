package resourcesManagement;

import cowParts.Cow;
import javafx.scene.image.Image;
import menus.MenuHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ResourcesHandler {

    private static int rock = 0;
    private static int wood = 0;
    private static int power = 0;

    public static void init() {
        new WaterSource(loadSprite("WateringHole"), Tile.getRandomTerrainTile());

        for (int i = 0; i < 5; i++) {
            new RockSource(loadSprite("Rock"), Tile.getRandomTerrainTile());
            new WoodSource(loadSprite("Tree"), Tile.getRandomTerrainTile());
        }
    }

    /**
     * Finds the image corresponding to the given string.
     * @param imageName The name of the image to have a resource created from
     * @return The new resource
     */
    private static Image loadSprite(String imageName) {
        Image resourceSprite = null;
        try {
            resourceSprite = new Image(new FileInputStream("src/main/resources/Environment/" + imageName + ".png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            MenuHandler.createErrorMenu();
        }
        return resourceSprite;
    }

    public static void modifyResource(@NotNull String resourceType, int amountToModify) {
        switch (resourceType) {
            case ("rock"):
                rock += amountToModify; break;
            case ("wood"):
                wood += amountToModify; break;
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

    public static void repurposeResource(@NotNull ResourceRequirement buildingRequirement, String resourceContribution, int repurposeAmount) {
        modifyResource(resourceContribution, -repurposeAmount);
        buildingRequirement.modifyRequirement(resourceContribution, -repurposeAmount);
    }

    @NotNull
    @Contract(pure = true)
    public static String getResourcesAsString() {
        return "Rock:" + rock + " Wood:" + wood + " Power:" + power;
    }

    public static void updatePower() {
        power = Cow.cowList.size() * 10;
    }
}
