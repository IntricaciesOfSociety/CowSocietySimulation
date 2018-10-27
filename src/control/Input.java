package control;

import environment.Cow;
import environment.Playground;
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

import java.util.Objects;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks
 */
public class Input {

    private static Rectangle dragBox = new Rectangle(0,0,0,0);

    //Directions relating to the direction that the dragBox is being created in.
    private static boolean xRight;
    private static boolean yUp;
    private static double startXDrag;
    private static double startYDrag;

    public static String selectedCow = "";

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
                case W:
                    CameraControl.moveCamera("North");
                    break;
                case A:
                    CameraControl.moveCamera("West");
                    break;
                case S:
                    CameraControl.moveCamera("South");
                    break;
                case D:
                    CameraControl.moveCamera("East");
                    break;

                //XC camera zoom controls. C to center the playground
                case Z:
                    CameraControl.zoomCamera(true);
                    break;
                case X:
                    CameraControl.zoomCamera(false);
                    break;
                case C:
                    Playground.playground.relocate(0,0);
                    CameraControl.resetZoom();
                    break;

                //Toggles all cow menus
                case N:
                    toggleAllCowMenus();
                    break;

                //Pause/UnPause simulation
                case P:
                    if (!SimState.getSimState().equals("Paused"))
                        SimState.setSimState("Paused");
                    else
                        SimState.setSimState("Playing");
            }
            PlaygroundUI.mouseEventUpdate();
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
            String objectString = mouseEvent.getTarget().toString();

            if (objectString.contains("id"))
                cowClicked(getParsedId(objectString));
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

        /*
         * Handles anytime the mouse is moved over a node within the playground. If an animal is found then the id
         * is parsed and used for the selectedCow variable.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            PlaygroundUI.mouseEventUpdate();

            String objectString = mouseEvent.getPickResult().toString();

            if (objectString.contains("id") && objectString.contains("ImageView"))
                selectedCow = getParsedId(objectString);
        });

        /*
         * Handles anytime that the mouse is taken off of a target within the playground, defined by MOUSE_ENTERED_TARGET,
         * only if a animal is not selected.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
            PlaygroundUI.mouseEventUpdate();

            if (MenuHandler.openMenus.isEmpty())
                selectedCow = "N/A";
        });

        /*
         * Handles any scrolling event within the playground and zooms in/out according to the direction of the scroll.
         */
        Playground.playground.addEventFilter(ScrollEvent.SCROLL, scrollEvent -> {
            CameraControl.zoomCamera(scrollEvent.getDeltaY() > 0);
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


    /**TODO: Switch implementation to action listeners
     * Checks to see what cows are within the bounds of the dragBox. Opens those cow's menus.
     */
    private static void checkDragBox() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            if (Cow.cowList.get(i).getX() > dragBox.getBoundsInParent().getMinX() && Cow.cowList.get(i).getX() < dragBox.getBoundsInParent().getMaxX())
                Cow.cowList.get(i).openMenu();
        }
    }

    /**TODO: Switch implementation to action listeners
     * Sets the clicked cow as clicked in the list of cows and opens that cow's menu
     * @param objectId the object's id
     */
    private static void cowClicked(String objectId) {
            Objects.requireNonNull(Cow.findCow(objectId)).setClicked();
    }

    /**TODO: Switch implementation to action listeners
     * Sets every cow in the cowList's menu to open or closed based on MenuHandler toggle.
     */
    private static void toggleAllCowMenus() {
        if (MenuHandler.allCowMenusOpen) {
            for (int i = 0; i < Cow.cowList.size(); i++) {
                Cow.cowList.get(i).closeMenu();
                MenuHandler.allCowMenusOpen = false;
            }
        }
        else {
            for (int i = 0; i < Cow.cowList.size(); i++) {
                Cow.cowList.get(i).openMenu();
                MenuHandler.allCowMenusOpen = true;
            }
        }
    }

    /**
     * Returns the id of a clicked object, parsed out of the string representation of the object
     * @param objectAsString the object as a string
     * @return the id from the object string
     */
    @NotNull
    public static String getParsedId(@NotNull String objectAsString) {
        //Gets the part of the string that contains the object id
        if (objectAsString.contains("id="))
            return objectAsString.substring(objectAsString.indexOf("id=") + 3, objectAsString.indexOf(','));
        else
            return objectAsString.substring(objectAsString.indexOf("Cow: ") + 5, objectAsString.lastIndexOf(':'));
    }
}
