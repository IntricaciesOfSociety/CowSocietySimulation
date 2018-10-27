package control;

import environment.Cow;
import environment.Playground;
import menus.PlaygroundUI;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
public class CameraControl {

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

    /**
     * Sets the zoom scale back to the default value of one.
     */
    static void resetZoom() {
        Playground.playground.setScaleX(1);
        Playground.playground.setScaleY(1);
    }

    /**
     * Sets the selected how to the given id, then calls the moving of the camera to the given cow's position
     * @param parsedCowId The id of the cow whose position is to be moved to
     */
    public static void moveCameraToCow(String parsedCowId) {
        Input.selectedCow = parsedCowId;
        PlaygroundUI.mouseEventUpdate();
        Cow idMatchingCow = Cow.findCow(parsedCowId);
        moveCamera(Objects.requireNonNull(idMatchingCow).getX(), idMatchingCow.getY());
    }
}
