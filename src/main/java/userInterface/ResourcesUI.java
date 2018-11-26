package userInterface;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;

/**
 * Creates and manages the resourceUI. Contains all the UI elements necessary to view the various needs and uses of
 * building resources throughout the city.
 */
class ResourcesUI {
    
    private static boolean opened = false;

    /**
     * Creates the core components of the resourcesUI.
     */
    private static void init() {
        Rectangle background = new Rectangle(225, 550, 500, 50);
        Text resourcesText = new Text(250, 575, "RESOURCES UI");

        resourcesText.setFill(Color.RED);

        PlaygroundUI.resourcesUI.getChildren().addAll(background, resourcesText);
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
    static boolean isOpened() {
        return opened;
    }
}
