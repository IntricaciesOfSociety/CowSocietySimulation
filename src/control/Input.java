package control;

import environment.Cow;
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

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks
 */
public class Input {

    private static double startXDrag = 0;
    private static double startYDrag = 0;

    private static Rectangle dragBox = new Rectangle(0,0,0,0);

    public static String objectMouseIsOn = "";

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

                //XC camera zoom controls
                case X:
                    CameraControl.zoomCamera(true);
                    break;
                case C:
                    CameraControl.zoomCamera(false);
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
         * Handles any click event (a complete mouse pressed then a mouse released event) by checking the object that
         * was clicked, then calling the corresponding method.
         */
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            String objectString = mouseEvent.getTarget().toString();

            if (objectString.contains("id"))
                cowClicked(getParsedId(objectString));
        });

        /*
         * Saves the mouse drag point's coords anytime that the mouse is pressed based on the mouse's current coords.
         */
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            startXDrag = mouseEvent.getX();
            startYDrag = mouseEvent.getY();
        });

        /*
         * Calls the check to see what cow nodes were within the dragBox, then sets the box coords and size to be out of
         * the way; all on the mouse released event.
         */
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            checkDragBox();
            dragBox.resizeRelocate(0,0,0,0);
        });

        /*
         * Handles whenever the the mouse is dragged. Moves the dragBox
         * depending on the start coordinates of the drag and the current mouse coordinates.
         */
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            dragBox.setX(startXDrag);
            dragBox.setY(startYDrag);
            dragBox.setWidth(mouseEvent.getX() - dragBox.getX());
            dragBox.setHeight(mouseEvent.getY() - dragBox.getY());
        });

        /*
         * Handles anytime the mouse is moved over a node within the playground. If an animal is found then the id
         * is parsed and used for the objectMouseIsOn variable.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            PlaygroundUI.mouseEventUpdate();

            String objectString = mouseEvent.getPickResult().toString();

            if (objectString.contains("id") && objectString.contains("ImageView"))
                objectMouseIsOn = getParsedId(objectString);
        });

        /*
         * Handles anytime that the mouse is taken off of a target within the playground defined by MOUSE_ENTERED_TARGET,
         * only if a animal is not selected.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
            PlaygroundUI.mouseEventUpdate();

            if (MenuHandler.openMenus.isEmpty())
                objectMouseIsOn = "N/A";
        });

        /*
         * Handles any scrolling event and zooms in/out accordingly
         */
        scene.addEventFilter(ScrollEvent.SCROLL, scrollEvent ->
                CameraControl.zoomCamera(scrollEvent.getDeltaY() > 0));
    }

    /**
     * Creates the dragBox rectangle and adds it into the playground node.
     */
    private static void initDragBox() {
        dragBox.setFill(Color.BLACK);
        dragBox.setOpacity(0.5);
        SimState.playground.getChildren().add(dragBox);
    }


    /**TODO: Switch implementation to action listeners
     * Checks to see what cows are within the bounds of the dragBox. Opens those cow's menus.
     */
    private static void checkDragBox() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            if (Cow.cowList.get(i).getX() > dragBox.getBoundsInParent().getMinX() && Cow.cowList.get(i).getX() < dragBox.getBoundsInParent().getMaxX()) {
                Cow.cowList.get(i).openMenu();
            }
        }
    }

    /**TODO: Switch implementation to action listeners
     * Sets the clicked cow as clicked in the list of cows and opens that cow's menu
     * @param objectId the object's id
     */
    private static void cowClicked(String objectId) {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            if (Cow.cowList.get(i).getId().equals(objectId)) {
                Cow.cowList.get(i).setClicked();
            }
        }
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
        return objectAsString.substring(objectAsString.indexOf("id=") + 3, objectAsString.indexOf(','));
    }
}
