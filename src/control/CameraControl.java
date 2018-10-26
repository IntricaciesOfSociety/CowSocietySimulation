package control;

import environment.Playground;
import org.jetbrains.annotations.NotNull;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
class CameraControl {

    private static final int MOVEMENTOFFSET = 10;

    /**
     * Moves the camera's layout position according to the given direction
     * @param direction The direction that the camera was told to move in
     */
    static void moveCamera(@NotNull String direction) {

        switch (direction) {
            case "North":
                Playground.playground.setLayoutY(Playground.playground.getLayoutY() + MOVEMENTOFFSET); break;
            case "East":
                Playground.playground.setLayoutX(Playground.playground.getLayoutX() - MOVEMENTOFFSET); break;
            case "South":
                Playground.playground.setLayoutY(Playground.playground.getLayoutY() - MOVEMENTOFFSET); break;
            case "West":
                Playground.playground.setLayoutX(Playground.playground.getLayoutX() + MOVEMENTOFFSET); break;
            default:
                break;
        }
    }

    /**
     * Zooms the camera in/out depending on the direction given by input.
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {
        double desiredScale = Playground.playground.getScaleX();

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

    public static void resetZoom() {
        Playground.playground.setScaleX(1);
        Playground.playground.setScaleY(1);
    }
}
