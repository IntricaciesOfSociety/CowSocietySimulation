package control;

import enviornment.Animal;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import menus.MenuHandler;
import menus.PlaygroundUI;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks
 */
public class Input {

    public static String objectMouseIsOn = "";

    /**
     * Sets the listener for any key press or mouse event. Will update the corresponding object.
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    public static void enableInput(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            CameraControl.forceUpdateCamera();
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

                //Pause/UnPause simulation
                case P:
                    if (!SimState.getSimState().equals("Paused"))
                        SimState.setSimState("Paused");
                    else
                        SimState.setSimState("Playing");
            }
            PlaygroundUI.update();
        });

        /*
         * Handles any click event by checking the object that was clicked, then calling the corresponding method
         */
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            //Necessary to update menus when paused
            CameraControl.forceUpdateCamera();

            String objectString = mouseEvent.getTarget().toString();

            if (objectString.contains("id"))
                parseId(objectString, true);
            else
                return;
        });

        /*
         * Handles anytime the mouse is moved over a node within the playground. If an animal is found then the id
         * is parsed and used for the objectMouseIsOn variable.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            String objectString = mouseEvent.getPickResult().toString();

            if (objectString.contains("id") && objectString.contains("ImageView"))
                parseId(objectString, false);
            else
                return;
        });

        /*
         * Handles anytime that the mouse is taken off of a target within the playground defined by MOUSE_ENTERED_TARGET,
         * only if a animal is not selected.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
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
     * Parses the id of the clicked object and calls the corresponding isClicked logic.
     * @param objectAsString the object represented by a string
     * @param clicked if the click flags are to be set
     */
    private static void parseId(String objectAsString, boolean clicked) {
        //Gets the part of the string that contains the object id
        String objectId = objectAsString.substring(objectAsString.indexOf("id=") + 3, objectAsString.indexOf(','));

        if (clicked) {

            for (int i = 0; i < Animal.animalList.size(); i++) {
                if (Animal.animalList.get(i).getId().equals(objectId)) {
                    Animal.animalList.get(i).isClicked();
                }
            }
        }
        else {
            objectMouseIsOn = objectId;
        }

    }
}
