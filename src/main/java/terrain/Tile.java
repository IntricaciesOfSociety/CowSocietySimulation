package terrain;

import buildings.Building;
import buildings.BuildingHandler;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.WaterSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

    private static Random random = new Random();

    private static final int ROWTILES = (int) Playground.playground.getPrefWidth() / 400;
    private static final int COLTILES = (int) Playground.playground.getPrefHeight() / 400;
    private static ArrayList<Tile> tileList = new ArrayList<>();

    /**
     * Tile creation
     * @param xCoord The x coordinate where the tile will be created
     * @param yCoord The y coordinate where the tile will be created
     * @param sprite The sprite that the tile will be created with
     */
    private Tile(double xCoord, double yCoord, Image sprite) {
        this.setImage(sprite);
        this.setLayoutX(xCoord);
        this.setLayoutY(yCoord);
        Playground.playground.getChildren().add(this);
    }

    /**TEMP: When no tile is selected and no cows are present, creates the building at a random tile.
     * Given a building, attempts to place the building on the current tile. Only works if there is adequate space.
     * @param building The building to be tried and placed
     * @param tileToBuildOn The tile that the building will be built on
     * @param numberOfSpaces The number of spaces that the proposed building takes up
     */
    @Nullable
    public static String tieToBuilding(Building building, ImageView tileToBuildOn, int numberOfSpaces) {
        if (getIsRoom(tileToBuildOn)) {
            building.setLayoutX(tileToBuildOn.getLayoutX());
            building.setLayoutY(tileToBuildOn.getLayoutY());

            BuildingHandler.constructedBuildings.add(building);
            Playground.playground.getChildren().add(building);

            return tileToBuildOn.toString();
        }
        else
            BuildingHandler.constructedBuildings.remove(building);
            MenuHandler.createErrorMenu();
            return null;
    }

    /**
     * Ties the given water source to a tile
     * @param waterSource The waterSource to tie to the given tile
     * @param tileToBuildOn The tile that the waterSource will be built on
     */
    public static void tieToWaterSource(@NotNull WaterSource waterSource, @NotNull Tile tileToBuildOn) {
        waterSource.setLayoutX(tileToBuildOn.getLayoutX());
        waterSource.setLayoutY(tileToBuildOn.getLayoutY());
    }

    /**
     * Creates a random point where a tile is, then calls the search for the tile at that point.
     * @return The random tile found
     */
    @Contract(" -> new")
    public static Tile getRandomTile() {
        Point2D randomTileCoords = new Point2D(random.nextInt(ROWTILES) * 400, random.nextInt(COLTILES) * 400);
        return tileAt(randomTileCoords);
    }

    /**TODO: Implement
     * Finds the tile at the given point
     * @param randomTileCoords The point to find the coordinate at
     * @return The tile that was at the given point
     */
    @Nullable
    @Contract(pure = true)
    private static Tile tileAt(Point2D randomTileCoords) {
        for (Tile tile : tileList) {
            if(tile.getLayoutX() == randomTileCoords.getX() && tile.getLayoutY() == randomTileCoords.getY())
                return tile;
        }
        return null;
    }

    /**
     * Creates the perfect amount of tiles based on the size of the playground.
     */
    public static void createTiles() {
        for (int i = 0; i < COLTILES; i++) {
            for (int j = 0; j < ROWTILES; j++) {
                try {
                    tileList.add(new Tile(400 * j, i * 400, new Image(
                            new FileInputStream("src/main/resources/Environment/FlatTerrain.png"),
                            0, 0, true, false)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**TODO: Implement
     * Returns if there is space to build the proposed tile upon.
     * @return If the selected tile is okay to build the proposed tile upon.
     */
    @Contract(pure = true)
    private static boolean getIsRoom(ImageView tileToCheck) {
        for (int i = 0; i < BuildingHandler.constructedBuildings.size(); i++) {
            if (BuildingHandler.constructedBuildings.get(i).getLayoutX() == tileToCheck.getLayoutX() &&
                    BuildingHandler.constructedBuildings.get(i).getLayoutY() == tileToCheck.getLayoutY() )
                return false;
        }
        return true;
    }
}
