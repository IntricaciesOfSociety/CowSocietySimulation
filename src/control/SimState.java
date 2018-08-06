package control;

import enviornment.Animal;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menus.MenuHandler;

/**
 * Controls all the main loops for the simulation: updating, drawing, meu management, and general javafx initialization
 */
public class SimState extends Application {

    public static Group root = new Group();
    private static Scene initialScene = new Scene(root, 300, 275);

    private static String playState;
    private static Object currentMenu;

    /**
     * Sets the state that the simulation can be in. For example" paused, playing, etc.
     * @param newState The new state the sim will switch to
     */
    public static void setSimState(String newState) {
        playState = newState;
    }

    /**
     * Gets the state of the sim
     * @return A string of the state that the sim is in as defined by setSimState()
     */
    public static String getSimState() {
        return playState;
    }

    /**
     * Sets the menu that is being drawn to the screen
     * @param newMenu The new menu that the sim is being set to
     */
    public static void setCurrentMenu(Object newMenu) {
        MenuHandler.drawMenu(MenuHandler.fetchMenu(newMenu));
        currentMenu = newMenu;
    }

    /**
     * Gets the current menu being drawn
     * @return The current menu that is being drawn as defined by setCurrentMenu()
     */
    public static Object getCurrentMenu() {
        return currentMenu;
    }

    /**
     * Calls the various initialize methods for: input, drawing, etc. Also starts the main sim loop
     */
    private static void simInit() {
        Input.enableInput(initialScene);
        Animal.animalList.add(new Animal());
        simLoop();
    }

    /**
     * Calls the update, then the draw methods for the whole system
     */
    private static void simLoop() {

        //timer runs constantly
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0 ;

            @Override
            public void handle(long frameTime) {
                if (frameTime - lastUpdate >= (30_000_000) ) {
                    updateTick();
                    drawTick();
                    lastUpdate = frameTime;
                }
            }
        };
        timer.start();
    }

    /**
     * Updates every object in the sim based off of its own AI, player interaction, or other AI interaction. Also calls
     * the collisions methods, and the boundary methods.
     */
    private static void updateTick() {
        CameraControl.forceUpdateCamera();
        System.out.println(root.getChildren().size());
        //System.out.println(Animal.animalList.get(0).getClicked());
    }

    /**
     * Draws the updates that were made by the updateTick() method to the screen.
     */
    private static void drawTick() {

    }


    /**
     * This is the main method that is run to start the whole simulation. Initializes the stage and calls simInit()
     * @param primaryStage The stage for the window that the simulation is in. Required for javafx
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Prototype01");

        initialScene.setCamera(CameraControl.initSceneCamera());
        primaryStage.setScene(initialScene);

        simInit();

        primaryStage.show();
    }


    /**
     * Calls the start method
     * @param args The command-line arguments that are sent when running java in a terminal (done by intellij)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
