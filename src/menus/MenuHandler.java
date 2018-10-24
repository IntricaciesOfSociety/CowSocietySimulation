package menus;

import control.SimState;
import environment.Cow;
import environment.Playground;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Stores every open menu
    public static ArrayList<StackPane> openMenus = new ArrayList<>();

    public static boolean allCowMenusOpen = false;

    /**
     * Calls for the creation of a menu based on the given object. Object can be a: Cow
     * @param objectToCreateMenuFrom The object that the new menu is to be created from
     * @return The menu object that was created
     */
    @Nullable
    public static GenericMenu createMenu(@NotNull Object objectToCreateMenuFrom) {

        if (objectToCreateMenuFrom.getClass().getSimpleName().equals("Cow"))
            return new GenericMenu((Cow) objectToCreateMenuFrom);
        else
            return null;
    }

    /**
     * Closes the given menu by removing it from its parent node
     * @param menu the menu to be closed
     */
    public static void closeMenu(@NotNull GenericMenu menu) {
        menu.stack.getChildren().clear();
        openMenus.remove(menu.stack);
        Playground.playground.getChildren().remove(menu.stack);
    }
}
