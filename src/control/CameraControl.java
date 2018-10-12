package control;

/**
 * Handles all of the camera movements sent by Input.java. Translates and scales the corresponding scene accordingly.
 */
class CameraControl {

    private static final int MOVEMENTOFFSET = 10;

    /**
     * Moves the camera's layout position according to the given direction
     * @param direction The direction that the camera was told to move in
     */
    static void moveCamera(String direction) {

        switch (direction) {
            case "North":
                SimState.playground.setLayoutY(SimState.playground.getLayoutY() + MOVEMENTOFFSET); break;
            case "East":
                SimState.playground.setLayoutX(SimState.playground.getLayoutX() - MOVEMENTOFFSET); break;
            case "South":
                SimState.playground.setLayoutY(SimState.playground.getLayoutY() - MOVEMENTOFFSET); break;
            case "West":
                SimState.playground.setLayoutX(SimState.playground.getLayoutX() + MOVEMENTOFFSET); break;
            default:
                break;
        }
    }

    /**
     * Zooms the camera in/out depending on the direction given
     * @param direction The direction that the camera is to move in
     */
    static void zoomCamera(boolean direction) {
        double desiredScale = SimState.playground.getScaleX();

        if (direction) {
            if (SimState.playground.getScaleX() < 4.8)
                desiredScale += 0.2;
        }
        else {
            if (SimState.playground.getScaleX() > 0.4)
                desiredScale -= 0.2;
        }

        SimState.playground.setScaleX(desiredScale);
        SimState.playground.setScaleY(desiredScale);

    }
}
