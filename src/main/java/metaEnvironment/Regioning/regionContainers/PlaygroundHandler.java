package metaEnvironment.Regioning.regionContainers;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import menus.MenuCreation;
import metaControl.main.Input;
import metaControl.main.SimState;
import metaEnvironment.LoadConfiguration;
import org.jetbrains.annotations.NotNull;
import userInterface.playgroundUI.StaticUI;

public class PlaygroundHandler {

    //MUTATED depending on what the user is looking at
    public static Playground playground;

    //The pane that holds all of the cows (The simulation part of the simulation)
    private static Playground motion = new Playground(0);

    //The pane that has the miens
    private static Playground mines = new Playground(1);



    //The pane that holds the menu when a user clicks on a detailed view button
    private static Playground detailedView = new Playground(2);

    //The pane that holds the entire story view when a user clicks the story view button
    private static Playground storyView = new Playground(3);

    //The pane that holds the establishmentView
    private static Playground establishmentView = new Playground(4);

    /**
     * Defaults the playground to the motion pane.
     */
    public static void init() {
        if (LoadConfiguration.getFullscreen())
            SimState.initFullScreen();

        int sideX = (LoadConfiguration.getWorldHRegions() * LoadConfiguration.getBinRegionSize()) * 400;
        int sideY = (LoadConfiguration.isWorldSquare()) ? sideX :
                (LoadConfiguration.getWorldVRegions() * LoadConfiguration.getBinRegionSize()) * 400;
        playground = motion;
        playground.setPrefSize(sideX, sideY);
        createBorders();
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
        boolean removedOld;

        if (playground.getRegionId() == 0 || playground.getRegionId() == 1)
            playground.resetDragBox();
        
        switch (grounds) {
            case "DetailedView":
                removedOld = SimState.root.getChildren().remove(playground);

                playground = detailedView;

                StaticUI.disableUI();

                MenuCreation.createStatsViewMenu(Input.selectedCows);
                if (removedOld)
                    SimState.addPlayground(playground);

                break;

            case "StoryView":
                removedOld = SimState.root.getChildren().remove(playground);

                playground = storyView;

                StaticUI.disableUI();

                MenuCreation.createStoryViewMenu(Input.selectedCows);
                if (removedOld)
                    SimState.addPlayground(playground);

                break;

            case "Motion":
                removedOld = SimState.root.getChildren().remove(playground);

                playground = motion;

                StaticUI.enableUI();
                if (removedOld)
                    SimState.addPlayground(playground);

                break;

            case "Mines":
                removedOld = SimState.root.getChildren().remove(playground);

                playground = mines;

                StaticUI.enableUI();

                if (removedOld)
                    SimState.addPlayground(playground);

                break;

            case "EstablishmentView":
                removedOld = SimState.root.getChildren().remove(playground);

                playground = establishmentView;

                StaticUI.disableUI();

                MenuCreation.createEstablishmentsMenu();
                if (removedOld)
                    SimState.addPlayground(playground);

                break;
        }
    }

    public static int getMaxBinId() {
        return playground.getMaxBinRegionId();
    }

    public static int getMinBinId() {
        return playground.getMinBinRegionId();
    }

    public static int getMaxBinId(Playground playgroundToCheck) {
        return playgroundToCheck.getMaxBinRegionId();
    }

    public static int getMinBinId(Playground playgroundToCheck) {
        return playgroundToCheck.getMinBinRegionId();
    }

    public static Playground getMines() {
        return mines;
    }

    public static Playground getMotion() {
        return motion;
    }
}
