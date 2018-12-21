package terrain;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaEnvironment.AssetLoading;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

    private boolean isTerrain = true;
    private boolean isBulitUpon = false;

    private static Random random = new Random();

    private static final int ROWTILES = (int) Playground.playground.getPrefWidth() / 400;
    private static final int COLTILES = (int) Playground.playground.getPrefHeight() / 400;

    //The list that contains every tile
    private static ArrayList<Tile> tileList = new ArrayList<>();

    /**
     * Default constructor so classes that extend tile can have their own constructor.
     */
    protected Tile() {
    }

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

    /**
     * Given a proposedConstruction, attempts to place the proposedConstruction on the current tile. Only works if there is adequate space.
     * @param proposedConstruction The proposedConstruction to be tried and placed
     * @param numberOfSpaces The number of spaces that the proposed proposedConstruction takes up
     * @return If the construction is possible or not
     */
    public boolean tieToObject(Tile proposedConstruction, int numberOfSpaces) {
        if (getIsRoom(this)) {
            proposedConstruction.setLayoutX(this.getLayoutX());
            proposedConstruction.setLayoutY(this.getLayoutY());

            this.isBulitUpon = true;
            proposedConstruction.isTerrain = false;

            tileList.add(proposedConstruction);

            Playground.playground.getChildren().add(proposedConstruction);
            return true;
        }
        else {
            MenuHandler.createErrorMenu();
            return false;
        }
    }

    /** TODO: Implement
     * @return If the tile can me assigned to a terrain
     */
    public boolean tieToTerrain() {
        return false;
    }

    /**
     * Creates a random point where a tile is, then calls the search for the tile at that point. If that tile is not
     * terrain then recurse and try again.
     * @return The random tile found
     */
    @Contract(" -> new")
    public static Tile getRandomTerrainTile() {
        Point2D randomTileCoords = new Point2D(random.nextInt(ROWTILES) * 400, random.nextInt(COLTILES) * 400);
        Tile randomTile = tileAt(randomTileCoords);

        if (!randomTile.isBulitUpon)
            return randomTile;
        else
            return getRandomTerrainTile();
    }

    /**
     * Finds the tile at the given point
     * @param tileCoords The point to find the coordinate at
     * @return The tile that was at the given point
     */
    @Nullable
    @Contract(pure = true)
    private static Tile tileAt(Point2D tileCoords) {
        for (Tile tile : tileList) {
            if(tile.getLayoutX() == tileCoords.getX() && tile.getLayoutY() == tileCoords.getY())
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
                tileList.add(new Tile(400 * j, i * 400, AssetLoading.basicTile));
            }
        }
    }

    /**TODO: Implement partial terrain
     * @return If the selected tile is okay to build the proposed tile upon.
     */
    @Contract(pure = true)
    private static boolean getIsRoom(@NotNull Tile tileToCheck) {
        return tileToCheck.isTerrain && !tileToCheck.isBulitUpon;
    }
}