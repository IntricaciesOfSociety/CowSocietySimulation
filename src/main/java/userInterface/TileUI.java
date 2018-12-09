package userInterface;

import buildings.Building;
import buildings.BuildingHandler;
import buildings.CityCenter;
import buildings.SmallDwelling;
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

    private static Text tileText;

    /**
     * Creates the UI components for the TileUI. Layout is temp.
     */
    private static void init() {
        Rectangle background = new Rectangle(225, 0, 500, 50);
        tileText = new Text(250, 25, "TILE UI        " + selectedTile);
        Button buildButton = new Button("Build City Center");
        Button buildButton2 = new Button("Build Cow Shack");

        tileText.setFill(Color.RED);
        buildButton.relocate(615, 0);
        buildButton2.relocate(615, 25);

        PlaygroundUI.buildingUI.getChildren().addAll(background, tileText, buildButton, buildButton2);

        BuildingHandler.highlightBuildings();

        buildButton.setOnAction(event -> new CityCenter(BuildingHandler.loadSprite("CityCenter"), selectedTile));
        buildButton2.setOnAction(event -> new SmallDwelling(BuildingHandler.loadSprite("CowShack"), selectedTile));
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

        if (selectedTile instanceof Building) {
            tileText.setText(selectedTile.getClass().getSimpleName() + " " + ((Building) selectedTile).getResourceRequirement().toString());
        }
    }

}