package control;

import environment.Cow;
import environment.Playground;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import menus.MenuHandler;
import menus.PlaygroundUI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Controls all the main loops for the simulation: updating, drawing, menu management, and general javafx initialization
 */
public class SimState extends Application {

    /*Scene elements
    Root: The root node that is the parent of the scene and everything within the scene
    InitialScene: The scene that the simulation starts out in. Holds the playground and playgroundUI
    */
    private static Group root = new Group();
    private static Scene initialScene = new Scene(root, 800, 600);

    /*Main loop
    SimLoop: The main simulation loop that handles the updating of moving elements
    PlayState: The state that the simLoop is in: 'Paused' or 'Playing'
    SimSpeed: The amount of milliseconds between each simLoop tick.
     */
    private static AnimationTimer simLoop;
    private static String playState = "Playing";
    private static long simSpeed = 16_666_666;

    /**
     * Sets the state that the simulation can be in. For example" paused, playing, etc.
     * @param newState The new state the sim will switch to
     */
    static void setSimState(@NotNull String newState) {
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
    @Contract(pure = true)
    static String getSimState() {
        return playState;
    }

    /**
     * Calls the various initialize methods for: input, drawing, etc. Also starts the main sim loop and adds the playgrounds
     * into the root node.
     */
    private static void simInit() {
        Input.enableInput(initialScene);

        PlaygroundUI.createUI();
        Playground.createBorders();

        root.getChildren().add(Playground.playground);
        root.getChildren().add(PlaygroundUI.playgroundUI);

        for (int i = 0; i < 10; i++) {
            Cow.cowList.add(new Cow());
        }
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
                    lastUpdate = frameTime;
                }
            }
        };
        simLoop.start();
    }

    /**
     * Updates every object in the sim based off of its own AI, player interaction, or other AI interaction. Also calls
     * the collisions methods, and the boundary methods.
     */
    private static void updateTick() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            Cow.cowList.get(i).step("Random");

            //Updates any animal menu that is opened
            if (Cow.cowList.get(i).isMenuOpened())
                Cow.cowList.get(i).cowMenu.updateMenu();
        }
        PlaygroundUI.update();
        MenuHandler.updateOpenMenus();
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
        primaryStage.setScene(initialScene);
        primaryStage.show();

        simInit();
    }

    /**
     * Calls the start method
     * @param args The command-line arguments that are sent when running java in a terminal (done by intellij)
     */
    public static void main(String[] args) {
        launch(args);
    }
}