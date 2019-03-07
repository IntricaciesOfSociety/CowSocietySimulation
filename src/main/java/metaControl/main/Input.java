package metaControl.main;

import infrastructure.buildingTypes.GenericBuilding;
import infrastructure.BuildingHandler;
import cowParts.cowMovement.ExecuteAction;
import cowParts.Cow;
import cowParts.CowHandler;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.Playground;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.Regioning.BinRegionHandler;
import terrain.Tile;
import org.jetbrains.annotations.NotNull;
import userInterface.playgroundUI.ResourcesUI;
import userInterface.playgroundUI.StaticUI;
import userInterface.playgroundUI.TileUI;

import java.util.ArrayList;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks.
 */
public class Input {

    private static boolean cowPopupMenuToggle = false;

    //TEST VARIABLE
    private static boolean testToggle = false;

    //Drag Box
    private static Rectangle dragBox = new Rectangle(-1,-1,0,0);

    //Directions relating to the direction that the dragBox is being created in.
    private static boolean xRight;
    private static boolean yUp;
    private static double startXDrag;
    private static double startYDrag;

    //The current selected cows
    public static ArrayList<Cow> selectedCows = new ArrayList<>();

    /**
     * Sets the listener for any key press or mouse event. Will update the corresponding object.
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    static void enableInput(@NotNull Scene scene) {

        initDragBox();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            KeyCode keyPressed = key.getCode();

            //DecideActions
            if (keyPressed.equals(KeyCode.W)) CameraControl.setNorth(true);
            if (keyPressed.equals(KeyCode.A)) CameraControl.setWest(true);
            if (keyPressed.equals(KeyCode.S)) CameraControl.setSouth(true);
            if (keyPressed.equals(KeyCode.D)) CameraControl.setEast(true);

            if (keyPressed.equals(KeyCode.UP)) CameraControl.setNorth(true);
            if (keyPressed.equals(KeyCode.LEFT)) CameraControl.setWest(true);
            if (keyPressed.equals(KeyCode.DOWN)) CameraControl.setSouth(true);
            if (keyPressed.equals(KeyCode.RIGHT)) CameraControl.setEast(true);

            //Zooming
            if (keyPressed.equals(KeyCode.Z)) CameraControl.setZoomIn(true);
            if (keyPressed.equals(KeyCode.X)) CameraControl.setZoomOut(true);
            if (keyPressed.equals(KeyCode.C)) {
                CameraControl.resetZoom();

                GenericBuilding defaultBuilding = BuildingHandler.getDefaultBuilding();
                CameraControl.moveCamera(
                        defaultBuilding.getLayoutX() + defaultBuilding.getRegion().getLayoutX(), defaultBuilding.getLayoutY() + defaultBuilding.getRegion().getLayoutY()
                );
            }

            //Toggles all cow menus
            if (keyPressed.equals(KeyCode.N)) toggleAllCowMenus();

            //Fullscreens the sim window
            if (keyPressed.equals(KeyCode.F)) SimState.initFullScreen();

            //TODO: Remove debug
            if (keyPressed.equals(KeyCode.O)) {
                System.out.println(Playground.playground.getChildren());
            }

            //Pause/UnPause simulation
            if (keyPressed.equals(KeyCode.P)) {
                if (!SimState.getSimState().equals("Paused")) SimState.setSimState("Paused");
                else SimState.setSimState("Playing");
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode keyReleased = key.getCode();

            //DecideActions
            if (keyReleased.equals(KeyCode.W)) CameraControl.setNorth(false);
            if (keyReleased.equals(KeyCode.A)) CameraControl.setWest(false);
            if (keyReleased.equals(KeyCode.S)) CameraControl.setSouth(false);
            if (keyReleased.equals(KeyCode.D)) CameraControl.setEast(false);

            if (keyReleased.equals(KeyCode.UP)) CameraControl.setNorth(false);
            if (keyReleased.equals(KeyCode.LEFT)) CameraControl.setWest(false);
            if (keyReleased.equals(KeyCode.DOWN)) CameraControl.setSouth(false);
            if (keyReleased.equals(KeyCode.RIGHT)) CameraControl.setEast(false);

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
            ExecuteAction.dragBoxSelectionUpdate(dragBox);
            dragBox.setWidth(-1);
            dragBox.setHeight(-1);
        });

        /*
         * Handles any click event that happens within the playground (a complete mouse pressed then a mouse released
         * event) by checking the object that was clicked, then calling the corresponding method.
         */
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof Cow) {
                ((Cow) mouseEvent.getTarget()).switchMenuState();
                StaticUI.cowClickEvent();
            }
            else if (mouseEvent.getTarget() instanceof Tile) {
                if (mouseEvent.getTarget() instanceof GenericBuilding)
                    ((GenericBuilding) mouseEvent.getTarget()).toggleInhabitantsMenu();

                if (SimState.getSimState().equals("TileView")) {
                    TileUI.setSelectedTile((Tile) mouseEvent.getTarget());
                    TileUI.updateUI();

                    if (ResourcesUI.isOpened())
                        ResourcesUI.updateUI();
                }
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

    /**
     * Sets all cow menus to open or closed based off of the value of allCowMenusOpen.
     */
    private static void toggleAllCowMenus() {
        if (cowPopupMenuToggle) {
            for (Cow cow : CowHandler.liveCowList)
                cow.closeMenu();
            cowPopupMenuToggle = false;
        }
        else {
            for (Cow cow : CowHandler.liveCowList)
                if (!cow.isHidden())
                    cow.openMenu();
            cowPopupMenuToggle = true;
        }
        StaticUI.cowClickEvent();
    }

    public static void addSelectedCow(Cow cow) {
        if (!selectedCows.contains(cow))
            selectedCows.add(cow);
    }

    public static void removeSelectedCow(Cow cow) {
        selectedCows.remove(cow);
    }
}