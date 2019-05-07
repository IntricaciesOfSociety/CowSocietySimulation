package infrastructure.terrain;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import metaControl.metaEnvironment.Regioning.BinRegion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

    private Random random = new Random();

    protected BinRegion region;

    int[][] placementArray = new int[4][4];

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
    Tile(double xCoord, double yCoord, Image sprite, BinRegion newRegion) {
        this.setImage(sprite);
        this.setLayoutX(xCoord);
        this.setLayoutY(yCoord);
        this.region = newRegion;
    }

    /**
     * Given a proposedConstruction, attempts to place the proposedConstruction on the current tile. Only works if there is adequate space.
     * @param proposedConstruction The proposedConstruction to be tried and placed
     * @param size The number of spaces that the proposed proposedConstruction takes up
     * @return If the construction is possible or not
     */
    public boolean tieToObject(Tile proposedConstruction, int size) {
        Point2D randPlacement = this.getIsRoom(size);
        if (randPlacement != null) {
            updatePlacementArray((int)randPlacement.getX(), (int)randPlacement.getY(), size);
            proposedConstruction.setLayoutX(this.getLayoutX() + (randPlacement.getX() * 100));
            proposedConstruction.setLayoutY(this.getLayoutY() + (randPlacement.getY() * 100));
            return true;
        }
        else {
            System.out.println("Could not tie " + proposedConstruction + " to object");
            return false;
        }
    }

    /**
     * @return If the selected tile is okay to build the proposed tile upon.
     */
    Point2D getIsRoom(int size) {
        boolean broken;
        ArrayList<Point2D> possiblePoints = new ArrayList<>();
        if (placementArray[0][0] != 2)
            for (int i = 0; i < placementArray.length - (size - 1); i++)
                for (int j = 0; j < placementArray.length - (size - 1); j++) {
                    broken = false;
                    innerLoop:
                    for (int k = 0; k < size; k++)
                        for (int h = 0; h < size; h++)
                            if (placementArray[i + k][j + h] == 1) {
                                broken = true;
                                break innerLoop;
                            }
                    if (!broken)
                        possiblePoints.add(new Point2D(i, j));
                }
        if (possiblePoints.size() == 0) {
            if (size == 1)
                placementArray[0][0] = 2;
            return null;
        }
        else
            return possiblePoints.get(random.nextInt(possiblePoints.size()));
    }

    private void updatePlacementArray(int x, int y, int size) {
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                placementArray[x + i][y + j] = 1;
    }

    /**TODO:Implement
     * The entrence coordinates for this tile
     * @return The coordinates for the entrence of the tile
     */
    public static Point2D getEntrance(@NotNull Tile tileToCheck) {
        return new Point2D(tileToCheck.getLayoutX() + tileToCheck.getRegion().getLayoutX(),tileToCheck.getLayoutY() + tileToCheck.getRegion().getLayoutY());
    }

    public BinRegion getRegion() {
        return this.region;
    }
}