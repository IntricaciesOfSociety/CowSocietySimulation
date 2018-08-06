package control;

import enviornment.Animal;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks
 */
class Input {

    /**
     * Sets the listener for any key press or mouse click. Will update the corresponding object
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    public static void enableInput(Scene scene) {
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
            }
        });

        /**
         * Handles any click event by checking the object that was clicked, then calling the corresponding method
         */
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String objectString = mouseEvent.getTarget().toString();

                if (objectString.contains("id"))
                    parseId(objectString);
                else
                    return;
            }
        });
    }

    /**
     * Parses the id of the object and calls the corresponding logic
     * @param objectAsString the object represented by a string
     */
    private static void parseId(String objectAsString) {
        String objectId = objectAsString.substring(objectAsString.indexOf("id=") + 3, objectAsString.indexOf(','));

        for (int i = 0; i < Animal.animalList.size(); i++) {
            if (Animal.animalList.get(i).getId().equals(objectId)) {
                Animal.animalList.get(i).isClicked();
            }
        }
    }
}
