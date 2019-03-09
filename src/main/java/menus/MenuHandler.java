package menus;

import org.jetbrains.annotations.*;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    private static ArrayList<GenericMenu> menusToUpdate = new ArrayList<>();

    private static GenericMenu staticStatsMenu;
    private static GenericMenu staticStoryMenu;

    static void addMenuToUpdateCycle(GenericMenu menuToUpdate) {
        menusToUpdate.add(menuToUpdate);
    }

    public static void updateMenuOnce(GenericMenu menuToUpdate) {
        menuToUpdate.updateMenu();
    }

    /**
     * Closes the given menu by removing it from its parent node
     * @param menu the menu to be closed
     */
    public static void closeMenu(@NotNull GenericMenu menu) {
        menu.closeMenu();
        staticStatsMenu = null;
        staticStoryMenu = null;
    }

    /**
     * Iterates through the open menus and updates them all.
     */
    public static void updateOpenMenus() {
        for (GenericMenu openMenu : menusToUpdate) {
            openMenu.updateMenu();
        }
    }

    static void setCurrentStoryMenu(GenericMenu storyMenu) {
        staticStoryMenu = storyMenu;
    }

    static void setCurrentStatsMenu(GenericMenu statsMenu) {
        staticStatsMenu = statsMenu;
    }

    public static GenericMenu getCurrentStoryMenu() {
        return staticStoryMenu;
    }

    public static GenericMenu getCurrentStatsMenu() {
        return staticStatsMenu;
    }
}