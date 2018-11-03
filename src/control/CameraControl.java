package control;

import environment.Cow;
import environment.Playground;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
public class CameraControl {

    private static final int MOVEMENTOFFSET = 10;
    private static boolean cameraDisable = false;

    /**
     * Moves the camera's layout position according to the given direction
     * @param direction The direction that the camera was told to move in
     */
    static void moveCamera(@NotNull String direction) {
        if (!cameraDisable) {
            switch (direction) {
                case "North":
                    Playground.playground.setLayoutY(Playground.playground.getLayoutY() + MOVEMENTOFFSET);
                    break;
                case "East":
                    Playground.playground.setLayoutX(Playground.playground.getLayoutX() - MOVEMENTOFFSET);
                    break;
                case "South":
                    Playground.playground.setLayoutY(Playground.playground.getLayoutY() - MOVEMENTOFFSET);
                    break;
                case "West":
                    Playground.playground.setLayoutX(Playground.playground.getLayoutX() + MOVEMENTOFFSET);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Attempts to move the playground to have the given coordinates be in the exact center of the screen.
     * @param xCoord The x coordinate to move to
     * @param yCoord The y coordinate to move to
     */
    private static void moveCamera(double xCoord, double yCoord) {
        Playground.playground.relocate(-xCoord + 400, -yCoord + 300);
    }

    /**
     * Zooms the camera in/out depending on the direction given by input.
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {
        double desiredScale = Playground.playground.getScaleX();

        if (!cameraDisable) {
            if (direction) {
                if (Playground.playground.getScaleX() < 4.8)
                    desiredScale += 0.2;
            }
            else {
                if (Playground.playground.getScaleX() > 0.4)
                    desiredScale -= 0.2;
            }
            Playground.playground.setScaleX(desiredScale);
            Playground.playground.setScaleY(desiredScale);
        }
    }

    /**
     * Sets the zoom scale back to the default value of one.
     */
    static void resetZoom() {
        Playground.playground.setScaleX(1);
        Playground.playground.setScaleY(1);
    }

    /**
     * Moves the camera viewport to a cow.
     * @param cowToMoveTo The cow to move the camera to
     */
    public static void moveCameraToCow(Cow cowToMoveTo) {
        moveCamera(Objects.requireNonNull(cowToMoveTo).getAnimatedX(), cowToMoveTo.getAnimatedY());
    }

    /**
     * Disables the 'camera' from moving
     */
    public static void disableCamera() {
        cameraDisable = true;
    }

    /**
     * Enables the 'camera' so that it can move again
     */
    public static void enableCamera() {
        cameraDisable = false;
    }
}