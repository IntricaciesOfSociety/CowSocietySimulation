package userInterface;

import buildings.BuildingHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;

/**
 * Creates handles tiles within the simulation. A tile is a space of land that can hold either plain terrain, or
 * a building. Can construct buildings and change tiles from this menu.
 */
public class TileUI {

    private static ImageView selectedTile;
    private static boolean opened = false;

    private static Text tileText;

    /**
     * Creates the UI components for the TileUI.
     */
    private static void init() {
        Rectangle background = new Rectangle(225, 0, 500, 50);
        tileText = new Text(250, 25, "TILE UI        " + selectedTile);
        Button buildButton = new Button("Build");

        tileText.setFill(Color.RED);
        buildButton.relocate(625, 25);

        PlaygroundUI.buildingUI.getChildren().addAll(background, tileText, buildButton);

        BuildingHandler.highlightBuildings();

        buildButton.setOnAction(event -> BuildingHandler.createBuilding("CityCenter"));
    }

    /**
     * Sets the tile that is currently selected within the UI. Set from Input.java.
     * @param target The selected tile
     */
    public static void setSelectedTile(ImageView target) {
        selectedTile = target;
    }

    /**
     * @return The current selectedTile
     */
    @Contract(pure = true)
    public static ImageView getSelectedTile() {
        return selectedTile;
    }

    /**
     * Sets the tileUI to open and calls for the creation of the UI.
     */
    static void create() {
        opened = true;
        init();
    }

    /**
     * Sets the tileUI to closed and calls for the destruction of the UI.
     */
    static void close() {
        opened = false;
        BuildingHandler.dehighlightBuildings();
        PlaygroundUI.buildingUI.getChildren().clear();
    }

    /**
     * @return If the tileUI is opened or not
     */
    @Contract(pure = true)
    static boolean isOpened() {
        return opened;
    }

    /**
     * Updates the various elements of the tileUI that need to be updated such as the tileText.
     */
    public static void updateUI() {
        tileText.setText("BUILDING UI        " + selectedTile);
    }

}