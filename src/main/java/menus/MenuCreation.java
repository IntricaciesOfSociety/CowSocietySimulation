package menus;

import buildings.Building;
import cowParts.CowHandler;
import javafx.scene.CacheHint;
import metaControl.SimState;
import cowParts.Cow;
import cowParts.cowThoughts.Social;
import metaEnvironment.Playground;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import metaEnvironment.logging.EventLogger;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class MenuCreation {

    Pane stack;

    private Cow clickedCow;

    //Statistics texts
    private VBox cognitiveAggregates = new VBox();
    private Text numberOfInhabitants;
    private Tile clickedBuilding;

    /**
     * Calls the creation of a new popup menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        createPopupMenu(cow);
    }

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        if (SimState.getSimState().equals("DetailedView"))
            StatsViewMenu.createDetailedViewMenu(cowsPreviouslySelected);
        else if (SimState.getSimState().equals("StoryView"))
            StoryViewMenu.createStoryViewMenu(cowsPreviouslySelected);
    }

    /**
     * Creates a new menu for the clicked on building showing the amount of inhabitants in the building.
     * @param buildingToCreateMenuFrom The building to create a menu for
     */
    MenuCreation(Building buildingToCreateMenuFrom) {
        createInhabitantsMenu(buildingToCreateMenuFrom);
    }

    /**
     * Creates the menu for the given building that shows the number of inhabitants in that building.
     * @param buildingToCreateMenuFrom The building to create an inhabitants menu for
     */
    private void createInhabitantsMenu(@NotNull Tile buildingToCreateMenuFrom) {
        Rectangle background = new Rectangle(50, 50, Color.BLACK);
        numberOfInhabitants = new Text(Integer.toString(((Building) buildingToCreateMenuFrom).getCurrentInhabitants().size()));
        stack = new Pane();

        numberOfInhabitants.setFill(Color.RED);
        numberOfInhabitants.relocate(10, 30);
        numberOfInhabitants.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
        clickedBuilding = buildingToCreateMenuFrom;

        stack.relocate(clickedBuilding.getLayoutX() + 175, clickedBuilding.getLayoutY() + 175);

        stack.getChildren().addAll(background, numberOfInhabitants);
        Playground.playground.getChildren().add(stack);
    }


    /**
     * Creates the UI elements for the popup menu.
     * @param cow The cow who's menu is being created.
     */
    private void createPopupMenu(@NotNull Cow cow) {
        clickedCow = cow;

        Text overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic;
        Rectangle background = new Rectangle(0,0, 110, 150);

        cognitiveAggregates.setSpacing(8);
        cognitiveAggregates.setLayoutX(5);
        cognitiveAggregates.setLayoutY(20);

        overallEmotion = new Text(5, 30, "Emotion: " + cow.self.getEmotionAggregate());
        overallFinance = new Text(5, 30, "Finance: " + cow.self.getFinanceAggregate());
        overallSocial = new Text(5, 30, "Social: " + cow.self.getSocialAggregate());
        overallPhysical = new Text(5, 30, "Physical: " + cow.self.getPhysicalAggregate());
        overallMental = new Text(5, 30, "Mental: " + cow.self.getMentalAggregate());
        overallAcademic = new Text(5, 30, "Academic: " + cow.self.getAcademicAggregate());

        Text idText = new Text(5,15, cow.getId());
        stack = new Pane();

        overallEmotion.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallFinance.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallSocial.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallPhysical.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallMental.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        overallAcademic.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        overallEmotion.setFill(Color.RED);
        overallFinance.setFill(Color.RED);
        overallSocial.setFill(Color.RED);
        overallPhysical.setFill(Color.RED);
        overallMental.setFill(Color.RED);
        overallAcademic.setFill(Color.RED);

        background.setFill(Color.BLACK);
        idText.setFill(Color.WHITE);

        stack.setCacheHint(CacheHint.SPEED);
        stack.setCache(true);

        cognitiveAggregates.getChildren().addAll(overallEmotion, overallFinance, overallSocial, overallPhysical, overallMental, overallAcademic);
        stack.getChildren().addAll(background, idText, cognitiveAggregates);

        Playground.playground.getChildren().add(stack);

        updateCowMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    void updateCowMenu() {
        stack.relocate((clickedCow.getTranslateX() + 55), (clickedCow.getTranslateY() + 40));
    }

    /**
     * Updates the text containing the amount of inhabitants in the given building's inhabitants menu.
     */
    void updateInhabitantMenu() {
        numberOfInhabitants.setText(Integer.toString(((Building) clickedBuilding).getCurrentInhabitants().size()));
    }

    /**
     * @return The id of the cow who this menu belongs to
     */
    Cow getCowFromMenu() {
        return clickedCow;
    }


}