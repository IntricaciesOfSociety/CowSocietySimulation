package metaControl.menus.userInterface.playgroundUI;

import javafx.scene.Group;

/**
 * Creates and handles the UI for the playground.
 */
public class PlaygroundUIHandler {

    //The root nodes for all UI elements
    //TODO: Change scope to getters
    public static Group staticUI;
    public static Group buildingUI;
    public static Group resourcesUI;

    /**
     * Creates the panes for the various UIs that have access to the root node. Also calls for creation of the staticUI.
     */
    public static void init() {
        staticUI = new Group();
        buildingUI = new Group();
        resourcesUI = new Group();

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