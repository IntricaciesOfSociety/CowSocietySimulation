package userInterface.playgroundUI;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import metaControl.main.SimState;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.Contract;
import resourcesManagement.resourceTypes.GenericResource;
import resourcesManagement.ResourcesHandler;

/**
 * Creates and manages the resourceUI. Contains all the UI elements necessary to view the various needs and uses of
 * building resources throughout the city.
 */
public class ResourcesUI {
    
    private static boolean opened = false;

    private static Rectangle background;
    private static Rectangle backgroundTwo;
    private static Rectangle backgroundThree;

    private static Text resourcesText;

    private static ImageView clayIcon;
    private static ImageView coalIcon;
    private static ImageView copperIcon;
    private static ImageView ironIcon;
    private static ImageView rockIcon;
    private static ImageView woodIcon;

    /**
     * Creates the core components of the resourcesUI.
     */
    private static void init() {

        background = new Rectangle();
        backgroundTwo = new Rectangle();
        backgroundThree = new Rectangle();
        resourcesText = new Text(ResourcesHandler.getResourcesAsString());
        clayIcon = new ImageView(AssetLoading.loadUISprite("ClayIcon"));
        coalIcon = new ImageView(AssetLoading.loadUISprite("CoalIcon"));
        copperIcon = new ImageView(AssetLoading.loadUISprite("CopperIcon"));
        ironIcon = new ImageView(AssetLoading.loadUISprite("IronIcon"));
        rockIcon = new ImageView(AssetLoading.loadUISprite("RockIcon"));
        woodIcon = new ImageView(AssetLoading.loadUISprite("WoodIcon"));

        resourcesText.setFill(Color.RED);

        PlaygroundUIHandler.resourcesUI.getChildren().addAll(background, backgroundTwo, backgroundThree, resourcesText, clayIcon, coalIcon, copperIcon, ironIcon, rockIcon, woodIcon);

        updateUIPlacements();
    }

    public static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(100);
        background.setHeight(50);
        backgroundTwo.setWidth(200);
        backgroundTwo.setHeight(50);
        backgroundThree.setWidth(75);
        backgroundThree.setHeight(50);
        background.relocate((screenOffsetX / 2) - background.getWidth() / 2 - 500, screenOffsetY - 50);
        backgroundTwo.relocate((screenOffsetX / 2) - background.getWidth() / 2, screenOffsetY - 50);
        backgroundThree.relocate((screenOffsetX / 2) - background.getWidth() / 2 + 300, screenOffsetY - 50);
        //background.relocate((screenOffsetX / 2) - background.getWidth() / 2, screenOffsetY - 100);
        clayIcon.relocate(backgroundTwo.getLayoutX() + 30, background.getLayoutY() + 25);
        coalIcon.relocate(backgroundTwo.getLayoutX() + 55, background.getLayoutY() + 25);
        copperIcon.relocate(backgroundTwo.getLayoutX() + 80, background.getLayoutY() + 25);
        ironIcon.relocate(backgroundTwo.getLayoutX() + 105, background.getLayoutY() + 25);
        rockIcon.relocate(backgroundTwo.getLayoutX() + 130, background.getLayoutY() + 25);
        woodIcon.relocate(background.getLayoutX() + 155, background.getLayoutY() + 25);

        resourcesText.relocate(background.getLayoutX() + 20, background.getLayoutY() + 15);
    }

    /**
     * Sets the UI to open and calls for the creation of the UI.
     */
    static void create() {
        opened = true;
        init();
    }

    /**
     * Sets the UI to closed and calls for the destruction of the UI.
     */
    static void close() {
        opened = false;
        PlaygroundUIHandler.resourcesUI.getChildren().clear();
    }

    /**
     * @return If the resourcesUI is opened or not.
     */
    @Contract(pure = true)
    public static boolean isOpened() {
        return opened;
    }

    /**
     * Updates the various elements of the resourcesUI that need to be updated such as the resourcesText.
     */
    public static void updateUI() {
        if (TileUI.getSelectedTile() instanceof GenericBuilding)
            resourcesText.setText(((GenericBuilding) TileUI.getSelectedTile()).getResourceRequirement().toString());
        else if (TileUI.getSelectedTile() instanceof GenericResource)
            resourcesText.setText(((GenericResource) TileUI.getSelectedTile()).toString());
        else
            resourcesText.setText(ResourcesHandler.getResourcesAsString());
    }
}
