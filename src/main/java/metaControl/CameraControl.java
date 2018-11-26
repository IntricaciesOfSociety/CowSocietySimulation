package metaControl;

import cowParts.Cow;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.transform.Scale;
import metaEnvironment.Playground;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
public class CameraControl {

    private static final int MOVEMENTOFFSET = 10;
    private static boolean cameraDisable = false;

    private static Scale scaleTransformation = new Scale();

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
                    if (Playground.playground.getLayoutX() - MOVEMENTOFFSET >= -Playground.playground.getWidth() + 800)
                        Playground.playground.setLayoutX(Playground.playground.getLayoutX() - MOVEMENTOFFSET);
                    break;
                case "South":
                    if (Playground.playground.getLayoutY() - MOVEMENTOFFSET >= -Playground.playground.getHeight() + 600)
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
        Playground.playground.relocate(-xCoord + 400, -yCoord + 300);
    }

    /**TODO: Fix zooming
     * Zooms the camera in/out depending on the direction given by input.
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {

        Bounds bounds = Playground.playground.getBoundsInLocal();
        Point2D point = Playground.playground.sceneToLocal(new Point2D(bounds.getMinX() + 450, bounds.getMinY() + 300));
        scaleTransformation.setPivotX(point.getX());
        scaleTransformation.setPivotY(point.getY());

        Playground.playground.getTransforms().add(scaleTransformation);

        if (!cameraDisable) {
            if (direction) {
                scaleTransformation.setX(scaleTransformation.getX() + 0.01);
                scaleTransformation.setY(scaleTransformation.getY() + 0.01);
            }
            else {
                scaleTransformation.setX(scaleTransformation.getX() - 0.01);
                scaleTransformation.setY(scaleTransformation.getY() - 0.01);
            }
        }
    }

    /**
     * Sets the zoom scale back to the default value of one.
     */
    static void resetZoom() {
        scaleTransformation.setX(1.0);
        scaleTransformation.setY(1.0);
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