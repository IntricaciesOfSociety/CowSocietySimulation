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

    private static Text resourcesText;

    private static ImageView clayIcon;
    private static ImageView coalIcon;
    private static ImageView copperIcon;
    private static ImageView ironIcon;
    private static ImageView rockIcon;
    private static ImageView woodIcon;

    private static Text clayText;
    private static Text coalText;
    private static Text copperText;
    private static Text ironText;
    private static Text rockText;
    private static Text woodText;
    private static Text powerText;

    /**
     * Creates the core components of the resourcesUI.
     */
    private static void init() {

        background = new Rectangle();
        resourcesText = new Text();

        clayText = new Text("" + ResourcesHandler.getClayAmount());
        coalText = new Text("" + ResourcesHandler.getCoalAmount());
        copperText = new Text("" + ResourcesHandler.getCopperAmount());
        ironText = new Text("" + ResourcesHandler.getIronAmount());
        rockText = new Text("" + ResourcesHandler.getRockAmount());
        woodText = new Text("" + ResourcesHandler.getWoodAmount());
        powerText = new Text("" + ResourcesHandler.getPowerAmount());

        clayIcon = new ImageView(AssetLoading.loadUISprite("ClayIcon"));
        coalIcon = new ImageView(AssetLoading.loadUISprite("CoalIcon"));
        copperIcon = new ImageView(AssetLoading.loadUISprite("CopperIcon"));
        ironIcon = new ImageView(AssetLoading.loadUISprite("IronIcon"));
        rockIcon = new ImageView(AssetLoading.loadUISprite("RockIcon"));
        woodIcon = new ImageView(AssetLoading.loadUISprite("WoodIcon"));
        //powerIcon = new ImageView();

        clayText.setFill(Color.RED);
        coalText.setFill(Color.RED);
        copperText.setFill(Color.RED);
        ironText.setFill(Color.RED);
        rockText.setFill(Color.RED);
        woodText.setFill(Color.RED);
        powerText.setFill(Color.RED);

        resourcesText.setFill(Color.RED);

        PlaygroundUIHandler.resourcesUI.getChildren().addAll(
                background, resourcesText, clayIcon, coalIcon, copperIcon, ironIcon, rockIcon, woodIcon, clayText, coalText,
                copperText, ironText, rockText, woodText, powerText
        );

        updateUIPlacements();
    }

    public static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(500);
        background.setHeight(50);
        background.relocate((screenOffsetX / 2) - background.getWidth() / 2, screenOffsetY - 100);

        clayIcon.relocate(background.getLayoutX() + 30, background.getLayoutY() + 25);
        coalIcon.relocate(background.getLayoutX() + 55, background.getLayoutY() + 25);
        copperIcon.relocate(background.getLayoutX() + 80, background.getLayoutY() + 25);
        ironIcon.relocate(background.getLayoutX() + 105, background.getLayoutY() + 25);
        rockIcon.relocate(background.getLayoutX() + 130, background.getLayoutY() + 25);
        woodIcon.relocate(background.getLayoutX() + 155, background.getLayoutY() + 25);

        resourcesText.relocate(background.getLayoutX() + 20, background.getLayoutY() + 15);

        clayText.relocate(background.getLayoutX() + 30, background.getLayoutY() + 25);
        coalText.relocate(background.getLayoutX() + 55, background.getLayoutY() + 25);
        copperText.relocate(background.getLayoutX() + 80, background.getLayoutY() + 25);
        ironText.relocate(background.getLayoutX() + 105, background.getLayoutY() + 25);
        rockText.relocate(background.getLayoutX() + 130, background.getLayoutY() + 25);
        woodText.relocate(background.getLayoutX() + 155, background.getLayoutY() + 25);
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
            resourcesText.setText(TileUI.getSelectedTile().toString());
        else {
            clayText.setText("" + ResourcesHandler.getClayAmount());
            coalText.setText("" + ResourcesHandler.getCoalAmount());
            copperText.setText("" + ResourcesHandler.getCopperAmount());
            ironText.setText("" + ResourcesHandler.getIronAmount());
            rockText.setText("" + ResourcesHandler.getRockAmount());
            woodText.setText("" + ResourcesHandler.getWoodAmount());
            powerText.setText("" + ResourcesHandler.getPowerAmount());
        }

    }
}
