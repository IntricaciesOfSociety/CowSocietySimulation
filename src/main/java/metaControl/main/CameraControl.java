package metaControl.main;

import cowParts.Cow;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import metaEnvironment.Playground;
import metaEnvironment.Regioning.BinRegionHandler;

import java.util.Objects;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
public class CameraControl {

    private static final double MOVEMENTOFFSET = 10;

    private static boolean
            north = false,
            south = false,
            east = false,
            west = false,
            zoomIn = false,
            zoomOut = false;

    /**
     * Updates the camera movement based off of the direction set to true.
     */
    static void updateCamera() {
        Bounds viewport = new Rectangle(0,0, SimState.initialScene.getWidth(), SimState.initialScene.getHeight()).getBoundsInLocal();

        //DecideActions
        if (north) { Playground.playground.setLayoutY(Playground.playground.getLayoutY() + MOVEMENTOFFSET); SimState.reDraw(viewport); }
        if (east)  {Playground.playground.setLayoutX(Playground.playground.getLayoutX() - MOVEMENTOFFSET); SimState.reDraw(viewport); }
        if (south)  { Playground.playground.setLayoutY(Playground.playground.getLayoutY() - MOVEMENTOFFSET); SimState.reDraw(viewport); }
        if (west)  { Playground.playground.setLayoutX(Playground.playground.getLayoutX() + MOVEMENTOFFSET); SimState.reDraw(viewport); }

        //Zooming
        if (zoomIn) zoomCamera(true);
        if (zoomOut) zoomCamera(false);

        /*Rectangle testRec = new Rectangle(40000, 40000, 20000, 20000);
        testRec.setOpacity(0);
        Playground.playground.getChildren().add(testRec);
        //testRec.setOpacity(0);
        if (testRec.localToScene(testRec.getBoundsInLocal()).intersects(viewport)) {
            if (!Playground.playground.getChildren().contains(BinRegionHandler.binRegionMap.get(12))) {
                System.out.println("Here");
                Playground.playground.getChildren().add(BinRegionHandler.binRegionMap.get(12));
            }
        }
        else {
            Playground.playground.getChildren().remove(BinRegionHandler.binRegionMap.get(12));
        }


        if (Playground.playground.getChildren().contains(BinRegionHandler.binRegionMap.get(12)))
            System.out.println("Drawing 5");
        else
            System.out.println("Not Drawing 5");*/
    }

    /**
     * Attempts to move the playground to have the given coordinates be in the exact center of the screen.
     * @param xCoord The x coordinate to move to
     * @param yCoord The y coordinate to move to
     */
    static void moveCamera(double xCoord, double yCoord) {
        Playground.playground.setLayoutX(-xCoord + SimState.initialScene.getWidth() / 2.0);
        Playground.playground.setLayoutY(-yCoord + SimState.initialScene.getHeight() / 2.0);
    }

    /**
     * Zooms the camera in/out depending on the direction given by input.
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {

        double delta = 1.2;
        double scale = Playground.playground.getScaleY();
        double oldScale = scale;

        if (direction && scale < 2.4)
            scale *= delta;
        else if (!direction && scale > 200 / Playground.playground.getWidth() * 10)
            scale /= delta;

        double f = (scale / oldScale) - 1;

        //Determining the shift in position of the camera as it zooms in on the center of the screen
        Bounds bounds = Playground.playground.localToScene(Playground.playground.getBoundsInLocal());
        double dx = (SimState.initialScene.getWidth()/2.0 - (bounds.getWidth() / 2 + bounds.getMinX()));
        double dy = (SimState.initialScene.getHeight()/2.0 - (bounds.getHeight() / 2 + bounds.getMinY()));

        //Applying the new scale
        Playground.playground.setScaleX(scale);
        Playground.playground.setScaleY(scale);

        //Applying the new translation
        Playground.playground.setLayoutX(Playground.playground.getLayoutX() - f * dx);
        Playground.playground.setLayoutY(Playground.playground.getLayoutY() - f * dy);
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
        moveCamera(Objects.requireNonNull(cowToMoveTo).getLayoutX(), cowToMoveTo.getLayoutY());
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