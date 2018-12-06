package metaControl;

import cowParts.Cow;
import javafx.geometry.Bounds;
import metaEnvironment.Playground;

import java.util.Objects;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
public class CameraControl {

    private static final int MOVEMENTOFFSET = 10;
    private static boolean cameraDisable = false;

    private static boolean north = false,
            south = false,
            east = false,
            west = false,
            zoomIn = false,
            zoomOut = false;

    static void updateCamera() {
        //Movement
        if (north) {
            if (Playground.playground.getBoundsInParent().getMinY() + MOVEMENTOFFSET < 0) {
                Playground.playground.setTranslateY(Playground.playground.getTranslateY() + MOVEMENTOFFSET);
            }
        }
        if (east) {
            if (Playground.playground.getBoundsInParent().getMaxX() - MOVEMENTOFFSET > 200) {
                Playground.playground.setTranslateX(Playground.playground.getTranslateX() - MOVEMENTOFFSET);
            }
        }
        if (south) {
            if (Playground.playground.getBoundsInParent().getMaxY() - MOVEMENTOFFSET > 200) {
                Playground.playground.setTranslateY(Playground.playground.getTranslateY() - MOVEMENTOFFSET);
            }
        }
        if (west) {
            if (Playground.playground.getBoundsInParent().getMinX() + MOVEMENTOFFSET < 0) {
                Playground.playground.setTranslateX(Playground.playground.getTranslateX() + MOVEMENTOFFSET);
            }
        }

        //Zooming
        if (zoomIn) {
            zoomCamera(true);
        }
        if (zoomOut) {
            zoomCamera(false);
        }

    }

    /**
     * Attempts to move the playground to have the given coordinates be in the exact center of the screen.
     * @param xCoord The x coordinate to move to
     * @param yCoord The y coordinate to move to
     */
    static void moveCamera(double xCoord, double yCoord) {
        Playground.playground.setTranslateX(-xCoord + SimState.initialScene.getWidth() / 2.0);
        Playground.playground.setTranslateY(-yCoord + SimState.initialScene.getHeight() / 2.0);
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
        resetZoom();
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

    static void setNorth(boolean moving) {
        north = moving;
    }

    static void setEast(boolean moving) {
        east = moving;
    }

    static void setSouth(boolean moving) {
        south = moving;
    }

    static void setWest(boolean moving) {
        west = moving;
    }

    static void setZoomIn(boolean zooming) {
        zoomIn = zooming;
    }

    static void setZoomOut(boolean zooming) {
        zoomOut = zooming;
    }
}