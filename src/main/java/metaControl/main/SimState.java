package metaControl.main;

import infrastructure.BuildingHandler;
import cowParts.CowHandler;
import cowParts.cowAI.NaturalSelection;
import cowParts.cowMovement.DecideActions;
import cowParts.cowMovement.ExecuteAction;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import metaEnvironment.LoadConfiguration;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.Regioning.BinRegionHandler;
import metaEnvironment.logging.EventLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import societalProductivity.cityPlanning.CityControl;
import terrain.TileHandler;
import userInterface.playgroundUI.PlaygroundUIHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import userInterface.playgroundUI.ResourcesUI;
import userInterface.playgroundUI.StaticUI;
import userInterface.playgroundUI.TileUI;

/**
 * Controls all the main loops for the simulation: updating, drawing, menu management, and general javafx initialization
 */
public class SimState extends Application {

    public static final Logger logger = LoggerFactory.getLogger(SimState.class);

    /*Scene elements
    Root: The root node that is the parent of the scene and everything within the scene
    InitialScene: The scene that the simulation starts out in. Holds the playground and playgroundStaticUI
    */
    public static Group root = new Group();
    static Scene initialScene = new Scene(root, 800, 600, Color.BLACK);

    /*Main loop
    SimLoop: The main simulation loop that handles the updating of moving elements
    PlayState: The state that the simLoop is in: 'Paused' or 'Playing'
    SimSpeed: The amount of milliseconds between each simLoop tick.
    DeltaTime: The time difference between now and the last frame, used to calculate speeds.
    Paused: If the main loop is to be calling the main update loop or not.
     */
    private static AnimationTimer simLoop;
    private static String playState = "Playing";
    private static long simSpeed = 128_666_666L;
    private static double deltaTime = 0;
    private static boolean paused;
    private static Stage primaryStage;

    /**
     * @return The current simSpeed of the sim
     */
    @Contract(pure = true)
    public static double getDeltaTime() {
        return deltaTime;
    }

    /**
     * Sets the state that the simulation is in. SimState is referenced from outside of this method.
     * @param newState The new state the sim will switch to
     */
    public static void setSimState(@NotNull String newState) {
        playState = newState;

        switch (newState) {
            case "Paused":
                ExecuteAction.pauseAllAnimation();
                paused = true;
                break;
            case "Playing":
                ExecuteAction.startAllAnimation();
                paused = false;
                break;
            case "DetailedView":
                ExecuteAction.pauseAllAnimation();
                paused = true;
                break;
            case "StoryView":
                ExecuteAction.pauseAllAnimation();
                paused = true;
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
        EventLogger.clearLogs();

        LoadConfiguration.loadConfigurationFile();
        AssetLoading.loadBaseAssets();
        Playground.init();

        TileHandler.init();
        BuildingHandler.init();
        ResourcesHandler.init();

        CityControl.init();

        Issue.init();
        CowHandler.init();
        PlaygroundUIHandler.init();

        Platform.runLater(() ->
            root.getChildren().addAll(
                    Playground.playground, PlaygroundUIHandler.resourcesUI, PlaygroundUIHandler.buildingUI, PlaygroundUIHandler.staticUI
            )
        );

        Input.enableInput(initialScene);
        simLoop();
        Time.timeHasStarted = true;
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
                if (getSimState().equals("Paused") || getSimState().equals("Playing") || getSimState().equals("TileView")) {
                    CameraControl.updateCamera();

                    MenuHandler.updateOpenMenus();
                    if (ResourcesUI.isOpened())
                        ResourcesUI.updateUI();
                }

                //Time difference from last frame
                deltaTime = 0.00000001 * (frameTime - lastUpdate);

                if (deltaTime <= 0.1 || deltaTime >= 1.0)
                    deltaTime = 0.00000001 * simSpeed;

                if (frameTime - lastUpdate >= simSpeed) {
                    if (!paused)
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
        //Decides what action each cow should be doing
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            NaturalSelection.calculateFitness(CowHandler.liveCowList.get(i));
            if (!CowHandler.liveCowList.get(i).alreadyMoving)
                DecideActions.decideActions(CowHandler.liveCowList.get(i));
        }
        Time.updateTime();
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

    public static void initFullScreen() {
        primaryStage.setFullScreen(true);
    }

    static void reDraw(Bounds viewport) {
        /*for (int i = 0; i < BinRegionHandler.ghostRegions.size(); i++) {
            System.out.println("Rectangle X: " + BinRegionHandler.ghostRegions.get(i).getLayoutX() + " Y: " + BinRegionHandler.ghostRegions.get(i).getLayoutY());
            System.out.println("Region X: " + BinRegionHandler.binRegionMap.get(i).getLayoutX() + " Y: " + BinRegionHandler.binRegionMap.get(i).getLayoutY());
            if (BinRegionHandler.ghostRegions.get(i).localToScene(BinRegionHandler.ghostRegions.get(i).getBoundsInLocal()).intersects(viewport)) {
                if (!Playground.playground.getChildren().contains(BinRegionHandler.binRegionMap.get(i)))
                    Playground.playground.getChildren().add(BinRegionHandler.binRegionMap.get(i));
                else
                    Playground.playground.getChildren().remove(BinRegionHandler.binRegionMap.get(i));
            }
        }*/
    }

    /**
     * This is the main method that is run to start the whole simulation. Initializes the stage and calls simInit().
     * @param primaryStage The stage for the window that the simulation is in. Required for javafx
     */
    @Override
    public void start(Stage primaryStage) {
        SimState.primaryStage = primaryStage;
        primaryStage.setTitle("Release01");

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            StaticUI.updateUIPlacements();

            if (TileUI.isOpened())
                TileUI.updateUIPlacements();
            if (ResourcesUI.isOpened())
                ResourcesUI.updateUIPlacements();
            if (MenuHandler.getCurrentStoryMenu() != null)
                MenuHandler.updateMenuOnce(MenuHandler.getCurrentStoryMenu());
            if (MenuHandler.getCurrentStatsMenu() != null)
                MenuHandler.updateMenuOnce(MenuHandler.getCurrentStatsMenu());
        };

        primaryStage.widthProperty().addListener(stageSizeListener);
        primaryStage.heightProperty().addListener(stageSizeListener);

        primaryStage.show();
        primaryStage.setScene(initialScene);

        simInit();
    }

    public static int getScreenHeight() {
        return (int) primaryStage.getHeight();
    }

    public static int getScreenWidth() {
        return (int) primaryStage.getWidth();
    }

    /**
     * Calls the start method.
     * @param args The command-line arguments that are sent when running java in a terminal (done by intellij)
     */
    public static void main(String[] args) {
        launch(args);
    }
}