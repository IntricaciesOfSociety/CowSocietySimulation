package control;

import javafx.scene.Camera;
import javafx.scene.ParallelCamera;

class CameraControl {

    private static ParallelCamera sceneCamera;
    private static int movementOffset = 10;

    public static int zoomOffset;

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
        if (direction) {
            SimState.root.setScaleX(5);
            SimState.root.setScaleY(5);
        }
        else {
            SimState.root.setScaleX(1);
            SimState.root.setScaleY(1);
        }
    }

    /**
     * Moves the camera one pixel to the right then, one to the left to force redraw
     */
    public static void forceUpdateCamera() {
        sceneCamera.setLayoutX(sceneCamera.getLayoutX() + 1);
        sceneCamera.setLayoutX(sceneCamera.getLayoutX() - 1);
    }

}
