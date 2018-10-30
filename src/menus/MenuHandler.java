package menus;

import environment.Cow;
import environment.Playground;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Stores every open menu
    private static ArrayList<MenuCreation> openCowMenus = new ArrayList<>();

    public static boolean allCowMenusOpen = false;

    /**
     * Calls for the creation of a menu based on the given object. Object can be a: Cow
     * @param objectToCreateMenuFrom The object that the new menu is to be created from
     * @return The menu object that was created
     */
    @Nullable
    public static MenuCreation createMenu(@NotNull Object objectToCreateMenuFrom) {
        if (objectToCreateMenuFrom.getClass().getSimpleName().equals("ArrayList")) {
            ArrayList objectCreateMenuFrom = (ArrayList) objectToCreateMenuFrom;
            MenuCreation newMenu = new MenuCreation(objectCreateMenuFrom);
        }
        if (objectToCreateMenuFrom.getClass().getSimpleName().equals("Cow")) {
            MenuCreation newMenu = new MenuCreation((Cow) objectToCreateMenuFrom);
            openCowMenus.add(newMenu);
            return newMenu;
        }

        else
            return null;
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
            openMenu.updateMenu();
        }
    }

    /**
     * Takes the ids of the cows that have their menu's open and returns them as an arrayList string.
     * @return The list of cows whos id's are open
     */
    public static ArrayList<String> getCowsWithOpenMenus() {
        ArrayList<String> openMenuCows = new ArrayList<>();

        for (MenuCreation openMenu : openCowMenus) {
            openMenuCows.add(openMenu.getCowIdFromMenu());
        }

        return openMenuCows;
    }
}