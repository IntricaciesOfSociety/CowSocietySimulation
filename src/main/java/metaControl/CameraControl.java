package metaControl;

import cowParts.Cow;
import javafx.geometry.Bounds;
import metaEnvironment.Playground;
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
                    if (Playground.playground.getLayoutY() + MOVEMENTOFFSET <= 0)
                        Playground.playground.setLayoutY(Playground.playground.getLayoutY() + MOVEMENTOFFSET);
                    break;
                case "East":
                    if (Playground.playground.getLayoutX() - MOVEMENTOFFSET >= -Playground.playground.getBoundsInParent().getMaxX())
                        Playground.playground.setLayoutX(Playground.playground.getLayoutX() - MOVEMENTOFFSET);
                    break;
                case "South":
                    if (Playground.playground.getLayoutY() - MOVEMENTOFFSET >= -Playground.playground.getBoundsInParent().getMaxY())
                        Playground.playground.setLayoutY(Playground.playground.getLayoutY() - MOVEMENTOFFSET);
                    break;
                case "West":
                    if ((Playground.playground.getLayoutX()) + MOVEMENTOFFSET < 160)
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
    static void moveCamera(double xCoord, double yCoord) {
        Playground.playground.setTranslateX(-xCoord);
        Playground.playground.setTranslateY(-yCoord);
    }

    /**TODO: Fix zooming
     * Zooms the camera in/out depending on the direction given by input.
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {

        double delta = 1.2;
        double scale = Playground.playground.getScaleY();
        double oldScale = scale;

        if (direction) {
            scale *= delta;
        } else {
            scale /= delta;
        }

        double f = (scale / oldScale)-1;

        //Determining the shift in position of the camera as it zooms in on the center of the screen
        Bounds bounds = Playground.playground.localToScene(Playground.playground.getBoundsInLocal());
        double dx = (SimState.initialScene.getWidth()/2.0 - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (SimState.initialScene.getHeight()/2.0 - (bounds.getHeight() / 2 + bounds.getMinY()));

        //Applying the new scale
        Playground.playground.setScaleX(scale);
        Playground.playground.setScaleY(scale);

        //Applying the new translation
        Playground.playground.setTranslateX(Playground.playground.getTranslateX() - f*dx);
        Playground.playground.setTranslateY(Playground.playground.getTranslateY() - f*dy);
    }

    /**
     * Sets the zoom scale back to the default value of one.
     */
    static void resetZoom() {
        Playground.playground.setScaleX(1.0);
        Playground.playground.setScaleY(1.0);
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