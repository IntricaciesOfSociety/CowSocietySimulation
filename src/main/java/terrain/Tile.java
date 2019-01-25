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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

    private static final int ROWTILES = (int) Playground.playground.getPrefWidth() / 400;
    private static final int COLTILES = (int) Playground.playground.getPrefHeight() / 400;

    private boolean isTerrain = true;
    private String tileType;

    //The list that contains every tile
    private static ArrayList<Tile> tileList = new ArrayList<>();

    private Point2D entrance;
    private ArrayList<Tile> children = new ArrayList<>();
    private ArrayList<ArrayList<Boolean>> orientation = new ArrayList<>();

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
        for (int i = 0; i < 4; i++) {
            orientation.add(new ArrayList<>());
            for (int j = 0; j < 4; j++) {
                orientation.get(i).add(false);
            }
        }
        Playground.playground.getChildren().add(this);
    }

    @Contract(pure = true)
    public static int getSize(Image newSprite) {
        return (int) newSprite.getWidth() / 100;
    }

    /**
     * Given a proposedConstruction, attempts to place the proposedConstruction on the current tile. Only works if there is adequate space.
     * @param proposedConstruction The proposedConstruction to be tried and placed
     * @param size The number of spaces that the proposed proposedConstruction takes up
     * @return If the construction is possible or not
     */
    public boolean tieToObject(Tile proposedConstruction, int size) {
        if (this.getIsRoom(size)) {

            Point2D randCoords = getRandomTileOrientation(this, size);
            proposedConstruction.setLayoutX(randCoords.getX());
            proposedConstruction.setLayoutY(randCoords.getY());

            proposedConstruction.isTerrain = false;
            this.children.add(proposedConstruction);
            tileList.add(proposedConstruction);

            Playground.playground.getChildren().add(proposedConstruction);
            return true;
        }
        else {
            MenuHandler.createErrorMenu();
            return false;
        }
    }

    @Contract("_, _ -> new")
    private Point2D getRandomTileOrientation(Tile tile, int size) {
        if (size == 4) {
            placeTile(0, 0, 4);
            return new Point2D(tile.getLayoutX(), tile.getLayoutY());
        }

        int possibleSpaces = 0;
        int spacesToCheck = -size + 5;

        for (int i = 0; i < spacesToCheck; i++) {
            for (int j = 0; j < spacesToCheck; j++) {
                if (!checkForOverlap(i, j, size))
                    possibleSpaces ++;
            }
        }

        int randOrientation = new Random().nextInt(possibleSpaces);
        int counter = -1;

        for (int i = 0; i < spacesToCheck; i++) {
            for (int j = 0; j < spacesToCheck; j++) {
                if (!checkForOverlap(i, j, size)) {
                    counter ++;
                    if (counter == randOrientation) {
                        placeTile(i, j, size);
                        return new Point2D(tile.getLayoutX() + (i * 100), tile.getLayoutY() + (j * 100));
                    }
                }
            }
        }
        return null;
    }

    private void placeTile(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!orientation.get(row + i).get(col + j))
                    orientation.get(row + i).set(col + j, true);
            }
        }
    }

    /**
     * Creates a random point where a tile is, then calls the search for the tile at that point. If that tile is not
     * terrain then recurse and try again.
     * @return The random tile found
     */
    @Nullable
    public static Tile getRandomNotFullTile(int size, Image ... tilesToExclude) {
        ArrayList<Tile> notFullTiles = new ArrayList<>();

        System.out.println(tilesToExclude);
        if (tilesToExclude.length != 0) {
            List<Image> exclusions = Arrays.asList(tilesToExclude);

            for (int j = 0; j < tileList.size(); j++) {
                if (!exclusions.contains(tileList.get(j).getImage()) && tileList.get(j).isTerrain && tileList.get(j).getIsRoom(size))
                    notFullTiles.add(tileList.get(j));
            }
            if (notFullTiles.size() != 0) {
                System.out.println(notFullTiles);
                return notFullTiles.get(new Random().nextInt(notFullTiles.size()));
            }

            else {
                MenuHandler.createErrorMenu();
                return null;
            }
        }
        else {
            for (int i = 0; i < tileList.size(); i++) {
                if (tileList.get(i).isTerrain && tileList.get(i).getIsRoom(size))
                    notFullTiles.add(tileList.get(i));
            }
        }

        if (notFullTiles.size() != 0)
            return notFullTiles.get(new Random().nextInt(notFullTiles.size()));
        else {
            MenuHandler.createErrorMenu();
            return null;
        }
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

    /**
     * @return If the selected tile is okay to build the proposed tile upon.
     */
    @Contract(pure = true)
    private boolean getIsRoom(int size) {
        int spacesToCheck = -size + 5;
        for (int i = 0; i < spacesToCheck; i++) {
            for (int j = 0; j < spacesToCheck; j++) {
                if (!checkForOverlap(i, j, size))
                    return true;
            }
        }
        return false;
    }

    private boolean checkForOverlap(int row, int col, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (orientation.get(row + i).get(col + j))
                    return true;
            }
        }
        return false;
    }

    /**
     * The entrence coordinates for this tile
     * @return The coordinates for the entrence of the tile
     */
    public static Point2D getEntrance(@NotNull Tile tileToCheck) {
        if (tileToCheck.entrance != null)
            return tileToCheck.entrance;
        else
            return new Point2D(tileToCheck.getLayoutX(), tileToCheck.getLayoutY());
    }
}