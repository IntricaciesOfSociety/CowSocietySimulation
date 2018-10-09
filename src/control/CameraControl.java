package control;

import javafx.scene.Camera;
import javafx.scene.ParallelCamera;

public class CameraControl {

    private static ParallelCamera sceneCamera;
    private static int movementOffset = 10;

    /**
     * Initializes the parallelCamera and returns it
     * @return Returns the parallelCamera so that SimState.java can set the camera to the stage
     */
    public static Camera initSceneCamera() {
        sceneCamera = new ParallelCamera();
        return sceneCamera;
    }

    /**
     * Moves the camera's layout position according to the given direction
     * @param direction The direction that the camera was told to move in
     */
    public static void moveCamera(String direction) {

        switch (direction) {
            case "North":
                sceneCamera.setLayoutY(sceneCamera.getLayoutY() - movementOffset); break;
            case "East":
                sceneCamera.setLayoutX(sceneCamera.getLayoutX() + movementOffset); break;
            case "South":
                sceneCamera.setLayoutY(sceneCamera.getLayoutY() + movementOffset); break;
            case "West":
                sceneCamera.setLayoutX(sceneCamera.getLayoutX() - movementOffset); break;
            default:
                break;
        }
    }

    /**
     * Zooms the camera in/out depending on the direction given
     * @param direction The direction that the camera is to move in
     */
    public static void zoomCamera(boolean direction) {
        double desiredScale = SimState.playground.getScaleX();

        if (direction) {
            if (SimState.playground.getScaleX() < 4.8)
                desiredScale += 0.2;
        }
        else {
            if (SimState.playground.getScaleX() > 0.2)
                desiredScale -= 0.2;
        }


        SimState.playground.setScaleX(desiredScale);
        SimState.playground.setScaleY(desiredScale);

    }

    /**
     * Moves the camera one pixel to the right then, one to the left to force redraw
     */
    public static void forceUpdateCamera() {
        sceneCamera.setLayoutX(sceneCamera.getLayoutX() + 1);
        sceneCamera.setLayoutX(sceneCamera.getLayoutX() - 1);
    }

    public static double getX() {
        return sceneCamera.getLayoutX();
    }

    public static double getY() {
        return sceneCamera.getLayoutY();
    }

}
