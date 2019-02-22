package userInterface;

import buildings.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import metaControl.SimState;
import org.jetbrains.annotations.Contract;
import terrain.Tile;

/**
 * Creates handles tiles within the simulation. A tile is a space of land that can hold either plain terrain, or
 * a building. Can construct buildings and change tiles from this menu.
 */
public class TileUI {

    private static Tile selectedTile;
    private static boolean opened = false;

    private static Rectangle background;

    private static Text tileText;
    private static Text coordsText;

    private static Button buildButton;
    private static Button buildButton2;

    /**
     * Creates the UI components for the TileUI. Layout is temp.
     */
    private static void init() {
        background = new Rectangle();
        tileText = new Text("TILE UI        " + selectedTile);
        coordsText = new Text("");
        buildButton = new Button("Build City Center");
        buildButton2 = new Button("Build Cow Shack");

        tileText.setFill(Color.RED);
        coordsText.setFill(Color.YELLOW);

        PlaygroundUI.buildingUI.getChildren().addAll(background, tileText, coordsText, buildButton, buildButton2);

        BuildingHandler.highlightBuildings();

        updateUIPlacements();
    }

    private static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(500);
        background.setHeight(50);
        background.relocate(360, 0);

        coordsText.relocate(435, 40);

        tileText.relocate(385, 25);

        buildButton.relocate(750, 0);
        buildButton2.relocate(750, 25);
    }

    /**
     * Sets the tile that is currently selected within the UI. Set from Input.java.
     * @param target The selected tile
     */
    public static void setSelectedTile(Tile target) {
        selectedTile = target;
    }

    /**
     * @return The current selectedTile
     */
    @Contract(pure = true)
    static ImageView getSelectedTile() {
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
        SimState.setSimState("Playing");
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
        tileText.setText("TILE UI        " + selectedTile);

        if (selectedTile != null)
            coordsText.setText("XCoord:" + selectedTile.getLayoutX() + " YCoord:" + selectedTile.getLayoutY());

        if (selectedTile instanceof Building)
            tileText.setText(selectedTile.getClass().getSimpleName() + " " + ((Building) selectedTile).getStreetAddress());
    }

}