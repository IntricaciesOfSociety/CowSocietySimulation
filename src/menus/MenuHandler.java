package menus;

import control.SimState;
import enviornment.Animal;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Keeps track of every open menu
    public static ArrayList<StackPane> openMenus = new ArrayList<>();

    /**
     * Fetches the correct menu type based off of the object given
     * @param menuRaw The object to create a menu from
     * @return The new menu created
     */
    public static GenericMenu fetchMenu(Object menuRaw) {
        GenericMenu menu = null;

        switch(menuRaw.getClass().getSimpleName()) {
            case "Animal":
                menu = new GenericMenu( ((Animal) menuRaw));
                break;
            case "Button":
                menu = new GenericMenu( ((Button) menuRaw).getId());
                break;
        }

        return menu;
    }

    /**
     * TODO: change the creation from generic menu to here
     * @param menu The menu object to be drawn
     */
    public static void drawMenu(GenericMenu menu) {
        //menu.buttons;
    }

    /**
     * Closes the given menu
     * @param menu the menu to be closed
     */
    public static void closeMenu(GenericMenu menu) {
        menu.stack.getChildren().clear();
        openMenus.remove(menu.stack);
        SimState.playground.getChildren().remove(menu.stack);
    }
}
