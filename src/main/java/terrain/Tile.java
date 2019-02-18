package terrain;

import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

    private static final int ROWTILES = (int) Playground.playground.getPrefWidth() / 400;
    private static final int COLTILES = (int) Playground.playground.getPrefHeight() / 400;

    private boolean isDefaultTerrain = true;

    //The list that contains every tile
    private static ArrayList<Tile> tileList = new ArrayList<>();

    private Point2D entrance;
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

        if (sprite.equals(AssetLoading.defaultTile))
            isDefaultTerrain = true;
        for (int i = 0; i < 4; i++) {
            orientation.add(new ArrayList<>());
            for (int j = 0; j < 4; j++) {
                orientation.get(i).add(false);
            }
        }
        this.setCacheHint(CacheHint.SPEED);
        Playground.playground.getChildren().add(this);
    }

    @Contract(pure = true)
    public static int getSize(@NotNull Image newSprite) {
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

            proposedConstruction.isDefaultTerrain = false;

            Playground.playground.getChildren().add(proposedConstruction);
            return true;
        }
        else {
            MenuHandler.createErrorMenu();
            return false;
        }
    }

    @Nullable
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
     * Adds tiles from the tileList that still have room, excluding any tile types given if any.
     * @return The random tile that is not an exclusion and that has room
     */
    @Nullable
    public static Tile getRandomNotFullTile(int size, @NotNull Image ... tilesToExclude) {
        ArrayList<Tile> notFullTiles = new ArrayList<>();

        if (tilesToExclude.length != 0) {
            List<Image> exclusions = Arrays.asList(tilesToExclude);
            for (Tile aTileList : tileList) {
                if (!exclusions.contains(aTileList.getImage()) && aTileList.isDefaultTerrain && aTileList.getIsRoom(size))
                    notFullTiles.add(aTileList);
            }
        }
        else {
            for (Tile aTileList : tileList) {
                if (aTileList.isDefaultTerrain && aTileList.getIsRoom(size))
                    notFullTiles.add(aTileList);
            }
        }

        if (notFullTiles.size() != 0)
            return notFullTiles.get(new Random().nextInt(notFullTiles.size()));
        else
            return null;
    }

    /**
     * Creates the perfect amount of tiles based on the size of the playground. Ground not the default tile has a chance
     * to appear, usually based of the size of the sim.
     */
    public static void createTiles() {
        int mountainBiomesAmount = LoadConfiguration.getMountainBiomes();
        int desertBiomesAmount = LoadConfiguration.getDesertBiomes();

        ArrayList<Point2D> biomeStarts = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < COLTILES; i++) {
            for (int j = 0; j < ROWTILES; j++) {
                tileList.add(new Tile(400 * j, i * 400, AssetLoading.defaultTile));
            }
        }

        for (int q = 0; q < mountainBiomesAmount + desertBiomesAmount; q++) {
            Point2D randStart = new Point2D(new Random().nextInt(ROWTILES), new Random().nextInt(COLTILES));

            while (biomeStarts.contains(randStart)) {
                randStart = new Point2D(new Random().nextInt(ROWTILES), new Random().nextInt(COLTILES));
            }

            biomeStarts.add(randStart);
        }

        for (int t = 0; t < mountainBiomesAmount; t++) {
            int randStartCoord = random.nextInt(biomeStarts.size());
            createBiome(biomeStarts.get(randStartCoord), AssetLoading.mountainTileFull);
            biomeStarts.remove(randStartCoord);
        }

        for (int u = 0; u < desertBiomesAmount; u++) {
            int randStartCoord = random.nextInt(biomeStarts.size());
            createBiome(biomeStarts.get(randStartCoord), AssetLoading.desertTileFull);
            biomeStarts.remove(randStartCoord);
        }
    }

    private static void createBiome(@NotNull Point2D startingPoint, Image biomeToCreate) {
        int biomeSize = new Random().nextInt(11) + 5;

        int xLimit = (int) ((biomeSize <= (ROWTILES - startingPoint.getX()) - 1) ? biomeSize : ROWTILES - startingPoint.getX());
        int yLimit = (int) ((biomeSize <= (COLTILES - startingPoint.getY()) - 1) ? biomeSize : COLTILES - startingPoint.getY());

        for (int i = 0; i < yLimit; i++) {
            for (int j = 0; j < xLimit; j++) {
                int tileIndex = (int) (startingPoint.getX() + j + ((startingPoint.getY() + i) * COLTILES));
                if (tileList.get(tileIndex).isDefaultTerrain) {
                    Playground.playground.getChildren().remove(tileList.get(tileIndex));
                    tileList.set(
                            tileIndex,
                            new Tile(400 * (startingPoint.getX() + j), 400 * (startingPoint.getY() + i), biomeToCreate)
                    );
                }
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

    @Contract(pure = true)
    public static boolean getIsRoom(int size, @NotNull Tile tileToCheck) {
        return tileToCheck.getIsRoom(size);
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