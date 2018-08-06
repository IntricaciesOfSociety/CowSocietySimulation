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
    static ArrayList<StackPane> openMenus = new ArrayList<>();

    /**
     * Fetches the correct menu type based off of the object given
     * @param menuRaw The object to create a menu from
     * @return The new menu created
     */
    public static GenericMenu fetchMenu(Object menuRaw) {
        GenericMenu menu = null;

        switch(menuRaw.getClass().getSimpleName()) {
            case "Animal":
                menu = new GenericMenu( ((Animal) menuRaw).getId());
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
     * Loops through every open menu and closes the one matching the given id. Removes the menu's children, removes the
     * menu from the root node, then removes the menu from the open menu list
     * @param id
     */
    public static void closeMenu(String id) {
        for (int i = 0; i < openMenus.size(); i++) {
            if (openMenus.get(i).getId().equals(id)) {

                openMenus.get(i).getChildren().clear();
                SimState.root.getChildren().remove(openMenus.get(i));
                openMenus.remove(openMenus.get(i));

            }

        }
    }
}
