package metaEnvironment.Regioning.regionContainers;

import cowParts.actionSystem.action.ExecuteAction;
import infrastructure.buildings.buildingTypes.GenericBuilding;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import metaControl.main.CameraControl;
import metaControl.main.Input;

/**
 * Creates and handles the switching of the panes within the main window (the playground).
 */
public class Playground extends Pane {

    private int regionId;
    private int minBinRegionId;
    private int maxBinRegionId;
    private ColorAdjust brightnessAdjust = new ColorAdjust();
    private GenericBuilding defaultBuilding;

    private Rectangle dragBox;

    //Directions relating to the direction that the dragBox is being created in.
    private static boolean xRight;
    private static boolean yUp;
    private static double startXDrag;
    private static double startYDrag;

    Playground(int id) {
        this.regionId = id;
        this.setEffect(brightnessAdjust);

        if (id == 0 || id == 1)
            createDragBox();
    }

    public void setBinRegionIds(int min, int max) {
        minBinRegionId = min;
        maxBinRegionId = max;
    }

    public void setBrightness(double newBrightness) {
        brightnessAdjust.setBrightness(newBrightness);
    }

    public int getMinBinRegionId() {
        return minBinRegionId;
    }

    public int getMaxBinRegionId() {
        return maxBinRegionId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setDefaultBuilding(GenericBuilding building) {
        defaultBuilding = building;
    }

    public GenericBuilding getDefaultBuilding() {
        return defaultBuilding;
    }

    private void createDragBox() {
        dragBox = new Rectangle(-1,-1,0,0);
        dragBox.setFill(Color.BLACK);
        dragBox.setOpacity(0.5);
        this.getChildren().add(dragBox);

        /*
         * Saves the mouse drag point's coords anytime that the mouse is pressed based on the mouse's current coords
         * within the playground.
         */
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            startXDrag = mouseEvent.getX();
            startYDrag = mouseEvent.getY();
        });

        /*
         * Handles any scrolling event within the playground and zooms in/out according to the direction of the scroll.
         */
        this.addEventFilter(ScrollEvent.SCROLL, scrollEvent -> CameraControl.zoomCamera(scrollEvent.getDeltaY() > 0));

        /*
         * Calls the check to see what cow nodes were within the dragBox, then sets the box coords and size to be out of
         * the way; all on the mouse released event in the playground.
         */
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            ExecuteAction.dragBoxSelectionUpdate(dragBox);
            dragBox.setWidth(-1);
            dragBox.setHeight(-1);
        });

        /*
         * Handles whenever the the mouse is dragged. Moves the dragBox within the playground depending on the start
         * coordinates of the drag and the current mouse coordinates relative to the playground.
         */
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            xRight = mouseEvent.getX() > startXDrag;
            yUp = mouseEvent.getY() < startYDrag;

            //Sets the x y width and height of the drag box based on what direction the mouse is moving.
            dragBox.setX((xRight) ? startXDrag : mouseEvent.getX());
            dragBox.setWidth((xRight) ? (mouseEvent.getX() - startXDrag) : (startXDrag - mouseEvent.getX()));
            dragBox.setY((yUp) ? mouseEvent.getY() : startYDrag);
            dragBox.setHeight((yUp) ? (startYDrag - mouseEvent.getY()) : (mouseEvent.getY() - startYDrag));
        });
    }

    void resetDragBox() {
        xRight = false;
        yUp = false;
        startXDrag = 0.0;
        startYDrag = 0.0;

        this.dragBox.setWidth(-1);
        this.dragBox.setHeight(-1);
    }
}