package metaControl;

import buildings.BuildingHandler;
import cowMovement.DecideActions;
import cowMovement.ExecuteAction;
import cowParts.CowHandler;
import javafx.scene.shape.Rectangle;
import metaEnvironment.AssetLoading;
import resourcesManagement.ResourcesHandler;
import metaEnvironment.Playground;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import menus.MenuHandler;
import societalProductivity.Issue;
import terrain.Tile;
import userInterface.PlaygroundUI;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import userInterface.ResourcesUI;

/**
 * Controls all the main loops for the simulation: updating, drawing, menu management, and general javafx initialization
 */
public class SimState extends Application {

    /*Scene elements
    Root: The root node that is the parent of the scene and everything within the scene
    InitialScene: The scene that the simulation starts out in. Holds the playground and playgroundStaticUI
    */
    public static Group root = new Group();
    static Scene initialScene = new Scene(root, 800, 600, Color.GREEN);

    /*Main loop
    SimLoop: The main simulation loop that handles the updating of moving elements
    PlayState: The state that the simLoop is in: 'Paused' or 'Playing'
    SimSpeed: The amount of milliseconds between each simLoop tick.
     */
    private static AnimationTimer simLoop;
    private static String playState = "Playing";
    private static long simSpeed = 16_666_666;

    /**
     * Sets the state that the simulation is in. SimState is referenced from outside of this method.
     * @param newState The new state the sim will switch to
     */
    public static void setSimState(@NotNull String newState) {
        playState = newState;

        switch (newState) {
            case "Paused":
                ExecuteAction.pauseAllAnimation();
                simLoop.stop();
                break;
            case "Playing":
                ExecuteAction.startAllAnimation();
                simLoop.start();
                break;
            case "DetailedView":
                ExecuteAction.pauseAllAnimation();
                simLoop.stop();
                break;
            case "StoryView":
                ExecuteAction.pauseAllAnimation();
                simLoop.stop();
                break;
            case "TileView":
                break;
        }
    }

    /**
     * Gets the state of the sim.
     * @return A string of the state that the sim is in as defined by setSimState()
     */
    @Contract(pure = true)
    public static String getSimState() {
        return playState;
    }

    /**
     * Calls the various initialize methods for: input, drawing, etc. Also starts the main sim loop and adds the playgrounds
     * into the root node.
     */
    private static void simInit() {
        LoadConfiguration.loadConfigurationFile();
        AssetLoading.loadBaseAssets();
        Playground.init();

        Tile.createTiles();
        BuildingHandler.init();
        ResourcesHandler.init();

        Issue.init();
        CowHandler.init();
        PlaygroundUI.init();

        root.getChildren().addAll(Playground.playground,
                PlaygroundUI.resourcesUI, PlaygroundUI.buildingUI, PlaygroundUI.staticUI
        );

        Input.enableInput(initialScene);
        simLoop();
    }

    /**
     * Calls the update, then the draw methods for the whole system
     */
    private static void simLoop() {
        /*for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            DecideActions.animateTowardsDestination(CowHandler.liveCowList.get(i), BuildingHandler.getDefaultBuilding());
        }*/


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
        Rectangle drawBounds = new Rectangle(0,0,initialScene.getWidth(),initialScene.getHeight());
        CameraControl.updateCamera();

        //Decides what action each cow should be doing
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            DecideActions.decideActions(CowHandler.liveCowList.get(i));
        }

        //Checks whether or not any node is on the screen, and draws it accordingly
        for (int i = 0; i < Playground.playground.getChildren().size(); i++) {
            if (Playground.playground.getChildren().get(i).localToScene(Playground.playground.getChildren().get(i).getBoundsInLocal()).intersects(drawBounds.getBoundsInLocal())) {
                Playground.playground.getChildren().get(i).setVisible(true);
            } else {
                Playground.playground.getChildren().get(i).setVisible(false);
            }
        }
        Time.updateTime();

        MenuHandler.updateOpenMenus();
        if (ResourcesUI.isOpened())
            ResourcesUI.updateUI();
    }

    /**
     * Sets the speed of the simLoop given the button clicked's id to change the speed. Changes the speed based off button
     * metadata
     */
    public static void setSimSpeed(String objectId) {
        simSpeed = Long.parseLong(objectId);
    }

    /**
     * Adds the given playground to the root node.
     * @param playground The playground to add to the root node.
     */
    public static void addPlayground(Pane playground) {
        root.getChildren().add(0, playground);
    }

    /**
     * This is the main method that is run to start the whole simulation. Initializes the stage and calls simInit().
     * @param primaryStage The stage for the window that the simulation is in. Required for javafx
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Release01");
        primaryStage.setScene(initialScene);
        primaryStage.show();

        simInit();
    }

    /**
     * Calls the start method.
     * @param args The command-line arguments that are sent when running java in a terminal (done by intellij)
     */
    public static void main(String[] args) {
        launch(args);
    }
}