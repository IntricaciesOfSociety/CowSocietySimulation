package userInterface;

import buildings.Building;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import metaControl.SimState;
import org.jetbrains.annotations.Contract;
import resourcesManagement.Resource;
import resourcesManagement.ResourcesHandler;

/**
 * Creates and manages the resourceUI. Contains all the UI elements necessary to view the various needs and uses of
 * building resources throughout the city.
 */
public class ResourcesUI {
    
    private static boolean opened = false;

    private static Rectangle background;

    private static Text resourcesText;

    /**
     * Creates the core components of the resourcesUI.
     */
    private static void init() {
        background = new Rectangle();
        resourcesText = new Text("RESOURCES UI        " + ResourcesHandler.getResourcesAsString());

        resourcesText.setFill(Color.RED);

        PlaygroundUI.resourcesUI.getChildren().addAll(background, resourcesText);

        updateUIPlacements();
    }

    private static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(500);
        background.setHeight(50);
        background.relocate(355, 725);

        resourcesText.relocate(385, 750);
    }

    /**
     * Sets the UI to open and calls for the creation of the UI.
     */
    static void create() {
        opened = true;
        init();
    }

    /**
     * Sets the UI to closed and calls for the destruction of the UI.
     */
    static void close() {
        opened = false;
        PlaygroundUI.resourcesUI.getChildren().clear();
    }

    /**
     * @return If the resourcesUI is opened or not.
     */
    @Contract(pure = true)
    public static boolean isOpened() {
        return opened;
    }

    /**
     * Updates the various elements of the resourcesUI that need to be updated such as the resourcesText.
     */
    public static void updateUI() {
        if (TileUI.getSelectedTile() instanceof Building)
            resourcesText.setText("RESOURCES UI        " + ((Building) TileUI.getSelectedTile()).getResourceRequirement().toString());
        else if (TileUI.getSelectedTile() instanceof Resource)
            resourcesText.setText("RESOURCES UI        " + ((Resource) TileUI.getSelectedTile()).toString());
        else
            resourcesText.setText("RESOURCES UI        " + ResourcesHandler.getResourcesAsString());
    }
}
