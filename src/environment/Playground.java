package environment;

import control.CameraControl;
import control.Input;
import control.SimState;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import menus.MenuHandler;
import menus.PlaygroundUI;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Creates and handles the switching of the panes within the main window (the playground).
 */
public class Playground {

    public static Pane playground;

    //The pane that holds all of the cows (The simulation part of the simulation)
    private static Pane motion = new Pane();

    //The pane that holds the menu when a user clicks on a detailed view button
    private static Pane detailedView = new Pane();

    /**
     * Defaults the playground to the motion pane.
     */
    public static void init() {
        playground = motion;
        motion.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Creates the black border outline for the playground node. Is bound to the bounds of the playground, and is resized
     * accordingly. Automatically updates.
     */
    public static void createBorders() {
       // playground.setBorder(new Border(new BorderStroke(Color.BLACK,
        // BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
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

                PlaygroundUI.disableUI();
                CameraControl.disableCamera();

                SimState.addPlayground(playground);
                MenuHandler.createMenu(Input.selectedCows);
                break;

            case "Motion":
                SimState.root.getChildren().remove(playground);
                playground = motion;

                PlaygroundUI.enableUI();
                SimState.addPlayground(playground);
                CameraControl.enableCamera();

                break;
        }
    }
}