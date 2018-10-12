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
    static void enableInput(Scene scene) {
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

            String objectString = mouseEvent.getTarget().toString();

            if (objectString.contains("id"))
                setAnimalClicked(getParsedId(objectString));
        });

        /*
         * Handles anytime the mouse is moved over a node within the playground. If an animal is found then the id
         * is parsed and used for the objectMouseIsOn variable.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, mouseEvent -> {
            PlaygroundUI.update();

            String objectString = mouseEvent.getPickResult().toString();

            if (objectString.contains("id") && objectString.contains("ImageView"))
                objectMouseIsOn = getParsedId(objectString);
        });

        /*
         * Handles anytime that the mouse is taken off of a target within the playground defined by MOUSE_ENTERED_TARGET,
         * only if a animal is not selected.
         */
        SimState.playground.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, mouseEvent -> {
            PlaygroundUI.update();

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
     * Sets the clicked animal as clicked in the list of animals
     * @param objectId the object's id
     */
    private static void setAnimalClicked(String objectId) {
        for (int i = 0; i < Animal.animalList.size(); i++) {

            if (Animal.animalList.get(i).getId().equals(objectId))
                Animal.animalList.get(i).isClicked();
        }
    }

    /**
     * Returns the id of a clicked object, parsed out of the string representation of the object
     * @param objectAsString the object as a string
     * @return the id from the object string
     */
    public static String getParsedId(String objectAsString) {
        //Gets the part of the string that contains the object id
        return objectAsString.substring(objectAsString.indexOf("id=") + 3, objectAsString.indexOf(','));
    }
}
