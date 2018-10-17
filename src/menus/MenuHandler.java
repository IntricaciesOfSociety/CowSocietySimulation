package menus;

import control.SimState;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Stores every open menu
    public static ArrayList<StackPane> openMenus = new ArrayList<>();

    /**
     * Closes the given menu by removing it from its parent node
     * @param menu the menu to be closed
     */
    public static void closeMenu(@NotNull GenericMenu menu) {
        menu.stack.getChildren().clear();
        openMenus.remove(menu.stack);
        SimState.playground.getChildren().remove(menu.stack);
    }
}
