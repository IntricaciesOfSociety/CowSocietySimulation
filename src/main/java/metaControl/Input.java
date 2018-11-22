package metaControl;

import cowParts.Cow;
import resourcesManagement.Food;
import metaEnvironment.Playground;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menus.MenuHandler;
import menus.PlaygroundUI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks.
 */
public class Input {

    private static Rectangle dragBox = new Rectangle(0,0,0,0);

    //Directions relating to the direction that the dragBox is being created in.
    private static boolean xRight;
    private static boolean yUp;
    private static double startXDrag;
    private static double startYDrag;

    //The current selected cows
    public static ArrayList<Cow> selectedCows = MenuHandler.getCowsWithOpenMenus();

    /**
     * Sets the listener for any key press or mouse event. Will update the corresponding object.
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    static void enableInput(@NotNull Scene scene) {

        initDragBox();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            KeyCode keyPressed = key.getCode();
            switch (keyPressed) {
                //WASD camera controls
                case W: CameraControl.moveCamera("North"); break;
                case A: CameraControl.moveCamera("West"); break;
                case S: CameraControl.moveCamera("South"); break;
                case D: CameraControl.moveCamera("East"); break;

                //XC camera zoom controls. C to center the playground
                case Z: CameraControl.zoomCamera(true); break;
                case X: CameraControl.zoomCamera(false); break;
                case C:
                    CameraControl.moveCamera(Food.getX(), Food.getY());
                    CameraControl.resetZoom();
                    break;

                //Toggles all cow menus
                case N:
                    if(SimState.getSimState().equals("Playing"))
                        toggleAllCowMenus();
                    break;

                //Pause/UnPause simulation
                case P:
                    if (!SimState.getSimState().equals("Paused"))
                        SimState.setSimState("Paused");
                    else
                        SimState.setSimState("Playing");
            }
        });

        /*
         * Saves the mouse drag point's coords anytime that the mouse is pressed based on the mouse's current coords
         * within the scene.
         */
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            startXDrag = mouseEvent.getX() - Playground.playground.getLayoutX();
            startYDrag = mouseEvent.getY() - Playground.playground.getLayoutY();
        });

        /*
         * Handles any scrolling event within the playground and zooms in/out according to the direction of the scroll.
         */
        Playground.playground.addEventFilter(ScrollEvent.SCROLL, scrollEvent -> CameraControl.zoomCamera(scrollEvent.getDeltaY() > 0));

        /*
         * Calls the check to see what cow nodes were within the dragBox, then sets the box coords and size to be out of
         * the way; all on the mouse released event in the playground.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            checkDragBox();
            dragBox.setWidth(0);
            dragBox.setHeight(0);
        });

        /*
         * Handles any click event that happens within the playground (a complete mouse pressed then a mouse released
         * event) by checking the object that was clicked, then calling the corresponding method.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof Cow) {
                ((Cow) mouseEvent.getTarget()).switchMenuState();
                PlaygroundUI.cowClickEvent();
            }
        });

        /*
         * Handles whenever the the mouse is dragged. Moves the dragBox within the playground depending on the start
         * coordinates of the drag and the current mouse coordinates relative to the playground.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            xRight = mouseEvent.getX() > startXDrag;
            yUp = mouseEvent.getY() < startYDrag;

            //Sets the x y width and height of the drag box based on what direction the mouse is moving.
            dragBox.setX((xRight) ? startXDrag : mouseEvent.getX());
            dragBox.setWidth((xRight) ? (mouseEvent.getX() - startXDrag) : (startXDrag - mouseEvent.getX()));
            dragBox.setY((yUp) ? mouseEvent.getY() : startYDrag);
            dragBox.setHeight((yUp) ? (startYDrag - mouseEvent.getY()) : (mouseEvent.getY() - startYDrag));
        });
    }

    /**
     * Initializes the dragBox rectangle and adds it into the playground node.
     */
    private static void initDragBox() {
        dragBox.setFill(Color.BLACK);
        dragBox.setOpacity(0.5);
        Playground.playground.getChildren().add(dragBox);
    }


    /**TODO: Switch implementation to collision. Yikes
     * Checks to see what cows are within the bounds of the dragBox. Opens those cow's menus.
     */
    private static void checkDragBox() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            if (Cow.cowList.get(i).getAnimatedX() > dragBox.getBoundsInParent().getMinX() && Cow.cowList.get(i).getAnimatedX() < dragBox.getBoundsInParent().getMaxX()
                && Cow.cowList.get(i).getAnimatedY() > dragBox.getBoundsInParent().getMinY() && Cow.cowList.get(i).getAnimatedY() < dragBox.getBoundsInParent().getMaxY()) {
                Cow.cowList.get(i).openMenu();
                PlaygroundUI.cowClickEvent();
            }

        }
    }

    /**
     * Sets all cow menus to open or closed based off of the value of allCowMenusOpen.
     */
    private static void toggleAllCowMenus() {
        if (MenuHandler.allCowMenusOpen.getValue())
                MenuHandler.allCowMenusOpen.set(false);
        else
                MenuHandler.allCowMenusOpen.set(true);

        PlaygroundUI.cowClickEvent();
    }

    /**
     * Refreshes the selected cow variable equal to the cows from the open menus list in menu handler.
     */
    public static void updateSelectedCows() {
        selectedCows = MenuHandler.getCowsWithOpenMenus();
    }
}