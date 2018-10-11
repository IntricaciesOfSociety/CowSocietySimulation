package control;

import enviornment.Animal;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menus.MenuHandler;
import menus.PlaygroundUI;

/**
 * Controls all the main loops for the simulation: updating, drawing, meu management, and general javafx initialization
 */
public class SimState extends Application {

    //The root group that everything is a child of
    private static Group root = new Group();

    //The main part of the simulation that houses the cows
    public static Pane playground = new Pane();

    //The main part of the UI
    public static Pane playgroundUI = new Pane();

    private static Scene initialScene = new Scene(root, 800, 600);

    private static AnimationTimer simLoop;

    private static String playState = "Playing";
    private static Object currentMenu;

    private static long simSpeed = 16_666_666;

    /**
     * Sets the state that the simulation can be in. For example" paused, playing, etc.
     * @param newState The new state the sim will switch to
     */
    public static void setSimState(String newState) {

        playState = newState;
        switch (newState) {
            case "Paused":
                simLoop.stop();
                break;

            case "Playing":
                simLoop.start();
                break;
        }
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
        PlaygroundUI.create();

        root.getChildren().add(playground);
        root.getChildren().add(playgroundUI);

        Animal.animalList.add(new Animal());
        simLoop();
    }

    /**
     * Calls the update, then the draw methods for the whole system
     */
    private static void simLoop() {
        //timer runs constantly
        simLoop = new AnimationTimer() {
            private long lastUpdate = 0 ;

            @Override
            public void handle(long frameTime) {
                if (frameTime - lastUpdate >= (simSpeed) ) {
                    updateTick();
                    drawTick();
                    lastUpdate = frameTime;
                }
                drawTick();

            }
        };
        simLoop.start();
    }

    /**
     * Updates every object in the sim based off of its own AI, player interaction, or other AI interaction. Also calls
     * the collisions methods, and the boundary methods.
     */
    private static void updateTick() {
        for (int i = 0; i < Animal.animalList.size(); i++) {
            Animal.animalList.get(i).step("Random");

            if (Animal.animalList.get(i).getClicked())
                Animal.animalList.get(i).animalMenu.updateMenu();

        }
        //System.out.println(playground.getChildren().size());
        System.out.println(simSpeed);

        PlaygroundUI.update();
        CameraControl.forceUpdateCamera();

    }

    /**
     * Draws the updates that were made by the updateTick() method to the screen.
     */
    private static void drawTick() {

    }

    /**
     * Sets the speed of the simLoop given the button clicked's id to change the speed. Changes the speed based off button
     * metadata
     */
    public static void setSimSpeed(String objectId) {
        simSpeed = Long.parseLong(objectId);
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