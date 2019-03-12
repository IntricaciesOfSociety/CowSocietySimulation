package metaEnvironment;

import menus.MenuCreation;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import metaControl.main.Input;
import metaControl.main.SimState;
import metaControl.timeControl.Time;
import org.jetbrains.annotations.NotNull;
import userInterface.playgroundUI.StaticUI;

/**
 * Creates and handles the switching of the panes within the main window (the playground).
 */
public class Playground {

    public static Pane playground;

    //The pane that holds all of the cows (The simulation part of the simulation)
    private static Pane motion = new Pane();

    //The pane that holds the menu when a user clicks on a detailed view button
    private static Pane detailedView = new Pane();

    //The pane that holds the entire story view when a user clicks the story view button
    private static Pane storyView = new Pane();

    /**
     * Defaults the playground to the motion pane.
     */
    public static void init() {
        if (LoadConfiguration.getFullscreen())
            SimState.initFullScreen();

        int sideX = (LoadConfiguration.getHorizontalRegions() * LoadConfiguration.getBinRegionSize()) * 400;
        int sideY = (LoadConfiguration.isSquareRegionSet()) ? sideX :
                (LoadConfiguration.getVerticalRegions() * LoadConfiguration.getBinRegionSize()) * 400;
        playground = motion;
        playground.setPrefSize(sideX, sideY);
        motion.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        createBorders();

        Playground.playground.setEffect(Time.dayNightCycle);
    }

    /**
     * Creates the black border outline for the playground node. Is bound to the bounds of the playground, and is resized
     * accordingly. Automatically updates.
     */
    private static void createBorders() {
       playground.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    /**
     * Chnages the playground to be equal to the given string-corresponding pane.
     * @param grounds The string as the name of the pane to switch the playground to
     */
    public static void setPlayground(@NotNull String grounds) {
        switch (grounds) {
            case "DetailedView":
                SimState.root.getChildren().remove(playground);
                playground = detailedView;

                StaticUI.disableUI();

                SimState.addPlayground(playground);
                MenuCreation.createStatsVeiwMenu(Input.selectedCows);
                break;

            case "StoryView":
                SimState.root.getChildren().remove(playground);
                playground = storyView;

                StaticUI.disableUI();

                SimState.addPlayground(playground);
                MenuCreation.createStoryViewMenu(Input.selectedCows);
                break;

            case "Motion":
                SimState.root.getChildren().remove(playground);
                playground = motion;

                StaticUI.enableUI();
                SimState.addPlayground(playground);
                break;
        }
    }
}