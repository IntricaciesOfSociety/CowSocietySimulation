package userInterface;

import javafx.scene.layout.AnchorPane;

/**
 * Creates and handles the UI for the playground.
 */
public class PlaygroundUI {

    //The root nodes for all UI elements
    //TODO: Change scope to getters
    public static AnchorPane staticUI;
    public static AnchorPane buildingUI;
    public static AnchorPane resourcesUI;

    /**
     * Creates the panes for the various UIs that have access to the root node.
     */
    public static void init() {
        staticUI = new AnchorPane();
        buildingUI = new AnchorPane();
        resourcesUI = new AnchorPane();
    }

    /**
     * Handles the call for creation of the staticUI.
     */
    public static void createStaticUI() {
        StaticUI.init();
    }

    /**
     * Handles the call for creation of the tileUI.
     */
    static void toggleTileUI() {
        if ((TileUI.isOpened()))
            TileUI.close();
        else
            TileUI.create();
    }

    /**
     * Handles the call for creation of the resourcesUI.
     */
    static void toggleResourcesUI() {
        if ((ResourcesUI.isOpened()))
            ResourcesUI.close();
        else
            ResourcesUI.create();
    }
}