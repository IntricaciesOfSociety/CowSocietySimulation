package menus;

import infrastructure.buildingTypes.GenericBuilding;
import javafx.scene.CacheHint;
import menus.menuImplementations.CowFitnessPopup;
import menus.menuImplementations.StatsViewMenu;
import menus.menuImplementations.StoryViewMenu;
import metaControl.main.SimState;
import cowParts.Cow;
import metaEnvironment.Playground;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

import static menus.MenuHandler.createInhabitantsMenu;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class MenuCreation {

    /**
     * Calls the creation of a new popup menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        new CowFitnessPopup(cow);
    }

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        if (SimState.getSimState().equals("DetailedView"))
            new StatsViewMenu(cowsPreviouslySelected);
        else if (SimState.getSimState().equals("StoryView"))
            new StoryViewMenu(cowsPreviouslySelected);
    }

    /**
     * Creates a new menu for the clicked on building showing the amount of inhabitants in the building.
     * @param buildingToCreateMenuFrom The building to create a menu for
     */
    MenuCreation(GenericBuilding buildingToCreateMenuFrom) {
        createInhabitantsMenu(buildingToCreateMenuFrom);
    }


}