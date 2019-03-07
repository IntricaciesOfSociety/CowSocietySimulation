package menus;

import buildings.Building;
import cowParts.Cow;
import metaEnvironment.Playground;
import org.jetbrains.annotations.*;

import java.util.ArrayList;

/**
 * Handles the opening, the closing, and the calling to creation of the menus
 */
public class MenuHandler {

    //Stores every open cow menu
    private static ArrayList<MenuCreation> openCowMenus = new ArrayList<>();
    private static ArrayList<MenuCreation> openInhabitantMenus = new ArrayList<>();

    public static boolean allCowMenusOpen = false;

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
     * Calls the creation of a inhabitants menu that shows what cows are in a building.
     * @param buildingToCreateMenuFrom The building whos inhabitants are going to be contained in the menu
     * @return The inhabitants menu that was created.
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static MenuCreation createInhabitantsMenu(Building buildingToCreateMenuFrom) {
        MenuCreation inhabitantsMenu = new MenuCreation(buildingToCreateMenuFrom);
        openInhabitantMenus.add(inhabitantsMenu);
        return inhabitantsMenu;
    }

    /**TODO: Implement
     * Creates a menu that only contains an error message. Caused by the player trying to do something within the sim
     * that doesn't make sense such as making a large building on a small space.
     */
    public static void createErrorMenu() {

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

        for (MenuCreation openMenu : openInhabitantMenus) {
            openMenu.updateInhabitantMenu();
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