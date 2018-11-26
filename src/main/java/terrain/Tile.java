package terrain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuHandler;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import userInterface.TileUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Handles the creation and management of tiles within the sim. A tile is a section of land.
 */
public class Tile extends ImageView {

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
        this.setScaleX(20);
        this.setScaleY(40);
        Playground.playground.getChildren().add(this);
    }

    /**
     * Given a building, attempts to place the building on the current tile. Only works if there is adequate space.
     * @param building The building to be tried and placed
     * @param numberOfSpaces The number of spaces that the proposed building takes up
     */
    public static void tieToBuilding(ImageView building, int numberOfSpaces) {
        if (getIsRoom(numberOfSpaces)) {
            building.setLayoutX(TileUI.getSelectedTile().getLayoutX());
            building.setLayoutY(TileUI.getSelectedTile().getLayoutX());
            Playground.playground.getChildren().add(building);
        }
        else
            MenuHandler.createErrorMenu();
    }

    /**TODO: Implement
     * Creates the perfect amount of tiles based on the size of the playground.
     */
    public static void createTiles() {
        try {
            new Tile(500, 500, new Image(new FileInputStream("src/main/resources/Buildings/EmptyTile.png"),0, 0, true, false));
            new Tile(1000, 1000, new Image(new FileInputStream("src/main/resources/Buildings/EmptyTile.png"),0, 0, true, false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**TODO: Implement
     * Returns if there is space to build the proposed tile upon.
     * @return If the selected tile is okay to build the proposed tile upon.
     */
    @Contract(pure = true)
    private static boolean getIsRoom(int proposedNumberOfSpaces) {
        return true;
    }
}
