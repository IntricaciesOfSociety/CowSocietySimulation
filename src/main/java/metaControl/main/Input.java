package metaControl.main;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import cowParts.creation.Cow;
import cowParts.CowHandler;
import infrastructure.buildings.buildingTypes.IndustrialBuilding;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import societyProduction.technology.CurrentTechnology;
import infrastructure.terrain.Tile;
import org.jetbrains.annotations.NotNull;
import metaControl.menus.userInterface.playgroundUI.ResourcesUI;
import metaControl.menus.userInterface.playgroundUI.StaticUI;
import metaControl.menus.userInterface.playgroundUI.TileUI;

import java.util.ArrayList;

/**
 * Handles any user input, calling the corresponding methods for the key presses or mouse clicks.
 */
public class Input {

    //Drag Box
    private static Rectangle dragBox;

    private static boolean cowPopupMenuToggle = false;
    private static boolean mineToggle = false;

    //The current selected cows
    public static ArrayList<Cow> selectedCows = new ArrayList<>();

    /**
     * Sets the listener for any key press or mouse event. Will update the corresponding object.
     * @param scene The initial scene from SimState.java, required to implement a listener
     */
    static void enableInput(@NotNull Scene scene) {

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {

            KeyCode keyPressed = key.getCode();

            if (SimState.getSimState().equals("Playing") || SimState.getSimState().equals("Paused") || SimState.getSimState().equals("TileView") || SimState.getSimState().equals("ResourcesView")) {

                //ActionHandler
                if (keyPressed.equals(KeyCode.W)) CameraControl.setNorth(true);
                if (keyPressed.equals(KeyCode.A)) CameraControl.setWest(true);
                if (keyPressed.equals(KeyCode.S)) CameraControl.setSouth(true);
                if (keyPressed.equals(KeyCode.D)) CameraControl.setEast(true);

                if (keyPressed.equals(KeyCode.UP)) CameraControl.setNorth(true);
                if (keyPressed.equals(KeyCode.LEFT)) CameraControl.setWest(true);
                if (keyPressed.equals(KeyCode.DOWN)) CameraControl.setSouth(true);
                if (keyPressed.equals(KeyCode.RIGHT)) CameraControl.setEast(true);

                if (keyPressed.equals(KeyCode.M)) {
                    if (mineToggle) {
                        cowPopupMenuToggle = true;
                        toggleAllCowMenus();
                        PlaygroundHandler.setPlayground("Motion");
                        mineToggle = false;
                    } else {
                        cowPopupMenuToggle = true;
                        toggleAllCowMenus();
                        PlaygroundHandler.setPlayground("Mines");
                        mineToggle = true;
                    }
                }

                //Pause/UnPause simulation
                if (keyPressed.equals(KeyCode.P)) {
                    if (!SimState.getSimState().equals("Paused")) SimState.setSimState("Paused");
                    else SimState.setSimState("Playing");
                }

                //Zooming
                if (keyPressed.equals(KeyCode.Z)) CameraControl.setZoomIn(true);
                if (keyPressed.equals(KeyCode.X)) CameraControl.setZoomOut(true);
                if (keyPressed.equals(KeyCode.C)) {
                    CameraControl.resetZoom();

                    GenericBuilding defaultBuilding = PlaygroundHandler.playground.getDefaultBuilding();
                    CameraControl.moveCamera(
                            defaultBuilding.getLayoutX() + defaultBuilding.getRegion().getLayoutX(), defaultBuilding.getLayoutY() + defaultBuilding.getRegion().getLayoutY()
                    );
                }

                //Toggles all cow metaControl.menus
                if (keyPressed.equals(KeyCode.N)) toggleAllCowMenus();
            }

            //Fullscreens the sim window
            if (keyPressed.equals(KeyCode.F)) SimState.initFullScreen();

            //TODO: Remove debug
            if (keyPressed.equals(KeyCode.O)) {

                System.out.println("DEBUG");
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, (key) -> {
            KeyCode keyReleased = key.getCode();

            //ActionHandler
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
         * Handles any click event that happens within the playground (a complete mouse pressed then a mouse released
         * event) by checking the object that was clicked, then calling the corresponding method.
         */
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getTarget() instanceof Cow) {
                ((Cow) mouseEvent.getTarget()).switchMenuState();
                StaticUI.cowClickEvent();
            }
            else if (mouseEvent.getTarget() instanceof Tile) {
                if (mouseEvent.getTarget() instanceof GenericBuilding) {
                    ((GenericBuilding) mouseEvent.getTarget()).toggleInhabitantsMenu();
                    if (mouseEvent.getTarget() instanceof IndustrialBuilding  && ((IndustrialBuilding) mouseEvent.getTarget()).isConstructed()) {
                        if (((IndustrialBuilding) mouseEvent.getTarget()).getId().equals(CurrentTechnology.getMineName())) {
                            PlaygroundHandler.setPlayground("Mines");
                            mineToggle = true;
                        }

                        else if (((IndustrialBuilding) mouseEvent.getTarget()).getId().equals("MineExit")) {
                            PlaygroundHandler.setPlayground("Motion");
                            mineToggle = false;
                        }
                    }
                }

                if (SimState.getSimState().equals("TileView")) {
                    TileUI.setSelectedTile((Tile) mouseEvent.getTarget());
                    TileUI.updateUI();

                    if (ResourcesUI.isOpened())
                        ResourcesUI.updateUI();
                }
            }
        });
    }

    /**
     * Sets all cow metaControl.menus to open or closed based off of the value of allCowMenusOpen.
     */
    private static void toggleAllCowMenus() {
        if (cowPopupMenuToggle) {
            for (Cow cow : CowHandler.liveCowList) {
                if (cow.getRegionIn().getPlayground() == PlaygroundHandler.playground)
                    cow.closeMenu();
            }
            cowPopupMenuToggle = false;
        }
        else {
            for (Cow cow : CowHandler.liveCowList)
                if (!cow.isHidden() && cow.getRegionIn().getPlayground() == PlaygroundHandler.playground)
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