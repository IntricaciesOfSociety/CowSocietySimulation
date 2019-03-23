package metaEnvironment;

import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles the loading of all assets.
 */
public class AssetLoading {

    public static Image defaultWorldTile;
    public static Image defaultMineTile;

    public static Image flatTerrain;
    public static Image mountainTileFull;
    public static Image mountainTileHalf;
    public static Image desertTileFull;

    public static Image smallUnderConstructionSprite;
    public static Image largeUnderConstructionSprite;

    public static Image basicSmallBuilding;
    public static Image basicLargeBuilding;
    public static Image basicGroceryStoreBuilding;
    public static Image basicMineBuilding;

    public static Image smallRock;
    public static Image smallTree;

    public static Image largeRock;
    public static Image largeTree;

    public static Image basicWatersource;


    public static ArrayList<Image> basicCows = new ArrayList<>();


    /**
     * Calls the loading for all base assets.
     */
    public static void loadBaseAssets() {
        loadTiles();
        loadBuildings();
        loadResources();
        loadCows();
    }

    /**
     * Loads the base tile assets.
     */
    private static void loadTiles() {
        try {
            flatTerrain = new Image(new FileInputStream("src/main/resources/Terrain/"
                    + "FlatTerrain.png"),0, 0, true, false);
            mountainTileFull = new Image(new FileInputStream("src/main/resources/Terrain/"
                    + "RockTerrainFull.png"),0, 0, true, false);
            mountainTileHalf = new Image(new FileInputStream("src/main/resources/Terrain/"
                    + "RockTerrainHalf.png"),0, 0, true, false);
            desertTileFull = new Image(new FileInputStream("src/main/resources/Terrain/"
                    + "DesertTileFull.png"),0, 0, true, false);
            loadDefaultTiles();
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    private static void loadDefaultTiles() {
        defaultWorldTile = flatTerrain;
        defaultMineTile = mountainTileFull;
    }

    /**
     * Loads the base building assets.
     */
    private static void loadBuildings() {
        try {
            largeUnderConstructionSprite = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + "LargeUnderConstruction.png"),0, 0, true, false);
            smallUnderConstructionSprite = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + "SmallUnderConstruction.png"),0, 0, true, false);
            basicSmallBuilding = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicSmallDwelling() + ".png"),0, 0, true, false);
            basicLargeBuilding  = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicLargeDwelling() + ".png"),0, 0, true, false);
            basicGroceryStoreBuilding = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicGroceryStore() + ".png"),0, 0, true, false);
            basicMineBuilding = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicMine() + ".png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    /**
     * Loads the basic resource assets.
     */
    private static void loadResources() {
        try {
            smallRock = new Image(new FileInputStream("src/main/resources/Environment/SmallRock.png"),0, 0, true, false);
            basicWatersource = new Image(new FileInputStream("src/main/resources/Environment/WateringHole.png"),0, 0, true, false);
            smallTree = new Image(new FileInputStream("src/main/resources/Environment/SmallTree.png"),0, 0, true, false);
            largeRock = new Image(new FileInputStream("src/main/resources/Environment/LargeRock.png"),0, 0, true, false);
            largeTree = new Image(new FileInputStream("src/main/resources/Environment/LargeTree.png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    /**
     * Loads all random cows.
     */
    private static void loadCows() {
        try {
            Files.walk(Paths.get("src/main/resources/Cows/Random")).filter(Files::isRegularFile).forEach(spritePath -> {
                try {
                    basicCows.add(new Image(new FileInputStream(spritePath.toString()),37.5, 16.5, true, false));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the loading of a UI sprite.
     * @param imageName The name of the UI sprite
     * @return The image of the UI sprite whose name was given
     */
    @Nullable
    @Contract("_ -> new")
    public static Image loadUISprite(String imageName) {
        try {
            return new Image(new FileInputStream("src/main/resources/UI/" + imageName + ".png"), 0, 0, true, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Handles the loading of a cow sprite within the Cows/Roles folder based off the given string
     * @param imageName The name of the sprite file as a string, without the file extension
     * @return The loaded image of the sprite
     */
    @Nullable
    public static Image loadCowRole(String imageName) {
        try {
            return new Image(new FileInputStream("src/main/resources/Cows/Roles/" + imageName + ".png"), 37.5, 16.5, true, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
