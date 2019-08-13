package metaControl.menus;

import metaControl.menus.menuImplementations.GenericMenu;
import org.jetbrains.annotations.*;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the metaControl.menus
 */
public class MenuHandler {

    private static ArrayList<GenericMenu> menusToUpdate = new ArrayList<>();

    private static GenericMenu openViewMenu;

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
    }

    /**
     * Iterates through the open metaControl.menus and updates them all.
     */
    public static void updateOpenMenus() {
        for (GenericMenu openMenu : menusToUpdate) {
            openMenu.updateMenu();
        }
    }

    static void setOpenViewMenu(GenericMenu newOpenMenu) {
        openViewMenu = newOpenMenu;
    }

    public static GenericMenu getOpenViewMenu() {
        return openViewMenu;
    }
}