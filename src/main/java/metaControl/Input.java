package metaControl;

import buildings.Building;
import cowParts.Cow;
import resourcesManagement.WaterSource;
import metaEnvironment.Playground;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menus.MenuHandler;
import terrain.Tile;
import org.jetbrains.annotations.NotNull;
import userInterface.StaticUI;
import userInterface.TileUI;

import java.util.ArrayList;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks.
 */
public class Input {

    private static Rectangle dragBox = new Rectangle(0,0,0,0);

    //Directions relating to the direction that the dragBox is being created in.
    private static boolean xRight;
    private static boolean yUp;
    private static double startXDrag;
    private static double startYDrag;

    //The current selected cows
    public static ArrayList<Cow> selectedCows = MenuHandler.getCowsWithOpenMenus();

    /**
     * Sets the listener for any key press or mouse event. Will update the corresponding object.
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    static void enableInput(@NotNull Scene scene) {

        initDragBox();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            KeyCode keyPressed = key.getCode();

            //Movement
            if (keyPressed.equals(KeyCode.W)) CameraControl.setNorth(true);
            if (keyPressed.equals(KeyCode.A)) CameraControl.setWest(true);
            if (keyPressed.equals(KeyCode.S)) CameraControl.setSouth(true);
            if (keyPressed.equals(KeyCode.D)) CameraControl.setEast(true);

            //Zooming
            if (keyPressed.equals(KeyCode.Z)) CameraControl.setZoomIn(true);
            if (keyPressed.equals(KeyCode.X)) CameraControl.setZoomOut(true);
            if (keyPressed.equals(KeyCode.C)) {
                CameraControl.resetZoom();
                double waterSize = WaterSource.getWateringHole().getBoundsInParent().getMaxX() - WaterSource.getWateringHole().getBoundsInParent().getMinX();
                CameraControl.moveCamera(WaterSource.getWateringHole().getLayoutX() + waterSize/2.0, WaterSource.getWateringHole().getLayoutY() + waterSize/2.0);
            }

            //Toggles all cow menus
            if (keyPressed.equals(KeyCode.N)) {
                toggleAllCowMenus();
            }

            //Pause/UnPause simulation
            if (keyPressed.equals(KeyCode.P)) {
                if (!SimState.getSimState().equals("Paused"))
                    SimState.setSimState("Paused");
                else
                    SimState.setSimState("Playing");
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode keyReleased = key.getCode();

            //Movement
            if (keyReleased.equals(KeyCode.W)) CameraControl.setNorth(false);
            if (keyReleased.equals(KeyCode.A)) CameraControl.setWest(false);
            if (keyReleased.equals(KeyCode.S)) CameraControl.setSouth(false);
            if (keyReleased.equals(KeyCode.D)) CameraControl.setEast(false);

            //Zooming
            if (keyReleased.equals(KeyCode.Z)) CameraControl.setZoomIn(false);
            if (keyReleased.equals(KeyCode.X)) CameraControl.setZoomOut(false);

        });

        /*
         * Saves the mouse drag point's coords anytime that the mouse is pressed based on the mouse's current coords
         * within the playground.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            startXDrag = mouseEvent.getX();
            startYDrag = mouseEvent.getY();
        });

        /*
         * Handles any scrolling event within the playground and zooms in/out according to the direction of the scroll.
         */
        Playground.playground.addEventFilter(ScrollEvent.SCROLL, scrollEvent -> CameraControl.zoomCamera(scrollEvent.getDeltaY() > 0));

        /*
         * Calls the check to see what cow nodes were within the dragBox, then sets the box coords and size to be out of
         * the way; all on the mouse released event in the playground.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
            checkDragBox();
            dragBox.setWidth(0);
            dragBox.setHeight(0);
        });

        /*
         * Handles any click event that happens within the playground (a complete mouse pressed then a mouse released
         * event) by checking the object that was clicked, then calling the corresponding method.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof Cow) {
                ((Cow) mouseEvent.getTarget()).switchMenuState();
                StaticUI.cowClickEvent();
            }
            else if (mouseEvent.getTarget() instanceof Building) {
                ((Building) mouseEvent.getTarget()).toggleInhabitantsMenu();

                if (SimState.getSimState().equals("TileView")) {
                    TileUI.setSelectedTile((Building) mouseEvent.getTarget());
                    TileUI.updateUI();
                }
            }
            else if (mouseEvent.getTarget() instanceof Tile && SimState.getSimState().equals("TileView")) {
                TileUI.setSelectedTile((Tile) mouseEvent.getTarget());
                TileUI.updateUI();
            }
        });

        /*
         * Handles whenever the the mouse is dragged. Moves the dragBox within the playground depending on the start
         * coordinates of the drag and the current mouse coordinates relative to the playground.
         */
        Playground.playground.addEventFilter(MouseEvent.MOUSE_DRAGGED, mouseEvent -> {
            xRight = mouseEvent.getX() > startXDrag;
            yUp = mouseEvent.getY() < startYDrag;

            //Sets the x y width and height of the drag box based on what direction the mouse is moving.
            dragBox.setX((xRight) ? startXDrag : mouseEvent.getX());
            dragBox.setWidth((xRight) ? (mouseEvent.getX() - startXDrag) : (startXDrag - mouseEvent.getX()));
            dragBox.setY((yUp) ? mouseEvent.getY() : startYDrag);
            dragBox.setHeight((yUp) ? (startYDrag - mouseEvent.getY()) : (mouseEvent.getY() - startYDrag));
        });
    }

    /**
     * Initializes the dragBox rectangle and adds it into the playground node.
     */
    private static void initDragBox() {
        dragBox.setFill(Color.BLACK);
        dragBox.setOpacity(0.5);
        Playground.playground.getChildren().add(dragBox);
    }


    /**TODO: Switch implementation to collision. Yikes
     * Checks to see what cows are within the bounds of the dragBox. Opens those cow's menus.
     */
    private static void checkDragBox() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            if (Cow.cowList.get(i).getAnimatedX() > dragBox.getBoundsInParent().getMinX() && Cow.cowList.get(i).getAnimatedX() < dragBox.getBoundsInParent().getMaxX()
                && Cow.cowList.get(i).getAnimatedY() > dragBox.getBoundsInParent().getMinY() && Cow.cowList.get(i).getAnimatedY() < dragBox.getBoundsInParent().getMaxY()
                && !Cow.cowList.get(i).isHidden()) {
                Cow.cowList.get(i).openMenu();
                StaticUI.cowClickEvent();
            }

        }
    }

    /**
     * Sets all cow menus to open or closed based off of the value of allCowMenusOpen.
     */
    private static void toggleAllCowMenus() {
        if (MenuHandler.allCowMenusOpen) {
            MenuHandler.allCowMenusOpen = false;
            for (Cow cow : Cow.cowList) {
                cow.closeMenu();
            }
        }
        else {
            MenuHandler.allCowMenusOpen = true;
            for (Cow cow : Cow.cowList) {
                if (!cow.isHidden())
                    cow.openMenu();
            }
        }


        StaticUI.cowClickEvent();
    }

    /**
     * Refreshes the selected cow variable equal to the cows from the open menus list in menu handler.
     */
    public static void updateSelectedCows() {
        selectedCows = MenuHandler.getCowsWithOpenMenus();
    }
}