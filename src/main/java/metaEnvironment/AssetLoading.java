package metaEnvironment;

import javafx.scene.image.Image;
import metaControl.LoadConfiguration;

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

    public static Image basicTile;

    public static Image basicSmallBuilding;
    public static Image basicLargeBuilding;

    public static Image basicRock;
    public static Image basicWatersource;
    public static Image basicTree;

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
            basicTile = new Image(new FileInputStream("src/main/resources/Terrain/"
                    + LoadConfiguration.getBasicTileName() + ".png"),0, 0, true, false);
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }
    }

    /**
     * Loads the base building assets.
     */
    private static void loadBuildings() {
        try {
            basicSmallBuilding = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicSmallDwelling() + ".png"),0, 0, true, false);
            basicLargeBuilding  = new Image(new FileInputStream("src/main/resources/Buildings/"
                    + LoadConfiguration.getBasicLargeDwelling() + ".png"),0, 0, true, false);
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
            basicRock = new Image(new FileInputStream("src/main/resources/Environment/Rock.png"),0, 0, true, false);
            basicWatersource = new Image(new FileInputStream("src/main/resources/Environment/WateringHole.png"),0, 0, true, false);
            basicTree = new Image(new FileInputStream("src/main/resources/Environment/Tree.png"),0, 0, true, false);
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
                    basicCows.add(new Image(new FileInputStream(spritePath.toString()),0, 0, true, false));
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
