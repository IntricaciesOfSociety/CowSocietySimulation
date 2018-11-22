package menus;

import cowParts.Cow;
import metaEnvironment.Playground;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.jetbrains.annotations.*;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Stores every open menu
    private static ArrayList<MenuCreation> openCowMenus = new ArrayList<>();

    public static BooleanProperty allCowMenusOpen = new SimpleBooleanProperty(false);

    /**
     * Calls for the creation of a menu based on the given selected cows.
     * @param selectedCow The cow that were selected to have their menu opened
     * @return The menu object that was created
     */
    @NotNull
    @Contract("null -> null")
    public static MenuCreation createPopupMenu(Cow selectedCow) {
        MenuCreation newMenu = new MenuCreation(selectedCow);
        openCowMenus.add(newMenu);
        return newMenu;
    }

    /**
     * Calls the creation of a menuView. Which one is decided by the sim's current state.
     * @param selectedCows The cows selected when the menu creation was called.
     */
    public static void createMenuView(ArrayList selectedCows) {
        new MenuCreation(selectedCows);
    }

    /**
     * Closes the given menu by removing it from its parent node
     * @param menu the menu to be closed
     */
    public static void closeMenu(@NotNull MenuCreation menu) {
        menu.stack.getChildren().clear();
        openCowMenus.remove(menu);
        Playground.playground.getChildren().remove(menu.stack);
    }

    /**
     * Iterates through the open menus and updates them all.
     */
    public static void updateOpenMenus() {
        for (MenuCreation openMenu : openCowMenus) {
            openMenu.updateCowMenu();
        }
    }

    /**
     * Takes the ids of the cows that have their menu's open and returns them as an arrayList string.
     * @return The list of cows whos id's are open
     */
    public static ArrayList<Cow> getCowsWithOpenMenus() {
        ArrayList<Cow> openMenuCows = new ArrayList<>();

        for (MenuCreation openMenu : openCowMenus) {
            openMenuCows.add(openMenu.getCowFromMenu());
        }

        return openMenuCows;
    }
}