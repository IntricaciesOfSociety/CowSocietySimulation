package metaControl.menus;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import metaControl.menus.menuImplementations.*;
import cowParts.creation.Cow;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal metaControl.menus.
 */
public class MenuCreation {

    /**
     * Calls the creation of a new popup menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    public static GenericMenu createCowPopup(@NotNull Cow cow) {
        GenericMenu newPopup = new CowFitnessPopup(cow);
        MenuHandler.addMenuToUpdateCycle(newPopup);
        return newPopup;
    }

    public static GenericMenu createEstablishmentsMenu() {
        return new EstablishmentsMenu();
    }

    /**
     * Creates a new story view menu from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected
     */
    public static GenericMenu createStoryViewMenu(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        GenericMenu newStoryView = new StoryViewMenu(cowsPreviouslySelected);
        MenuHandler.setCurrentStoryMenu(newStoryView);
        return newStoryView;
    }

    /**
     * Creates a new stats view menu from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected
     */
    public static GenericMenu createStatsViewMenu(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        GenericMenu newStatsView = new StatsViewMenu(cowsPreviouslySelected);
        MenuHandler.setCurrentStatsMenu(newStatsView);
        return newStatsView;
    }

    /**
     * Creates a new menu for the clicked on building showing the amount of inhabitants in the building.
     * @param buildingToCreateMenuFrom The building to create a menu for
     */
    public static GenericMenu createInhabitantsMenu(GenericBuilding buildingToCreateMenuFrom) {
        GenericMenu newInhabitantsMenu = new InhabitantsPopup(buildingToCreateMenuFrom);
        MenuHandler.addMenuToUpdateCycle(newInhabitantsMenu);
        return newInhabitantsMenu;
    }

    public static GenericMenu createTechTreeView() {
        return new TechTreeMenu();
    }
}