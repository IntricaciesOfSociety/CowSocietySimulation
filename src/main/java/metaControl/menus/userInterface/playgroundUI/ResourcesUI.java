package metaControl.menus.userInterface.playgroundUI;

import infrastructure.buildings.buildingTypes.GenericBuilding;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import metaControl.main.SimState;
import metaControl.metaEnvironment.AssetLoading;
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
    private static ImageView powerIcon;

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
        backgroundTwo = new Rectangle();
        backgroundThree = new Rectangle();

        resourcesText = new Text(ResourcesHandler.getResourcesAsString());
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
        powerIcon = new ImageView(AssetLoading.loadUISprite("PowerIcon"));
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
                background, backgroundTwo, backgroundThree, resourcesText, clayIcon, coalIcon, copperIcon, ironIcon, rockIcon, woodIcon, clayText, coalText,
                copperText, ironText, rockText, woodText, powerText, powerIcon
        );

        updateUIPlacements();
    }

    public static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        background.setWidth(300);
        background.setHeight(50);
        background.relocate((screenOffsetX / 2) - background.getWidth() / 2 + 50, screenOffsetY - 75);

        backgroundTwo.setWidth(75);
        backgroundTwo.setHeight(50);
        backgroundTwo.relocate((screenOffsetX / 2) - background.getWidth() + 175 / 2, screenOffsetY - 75);

        backgroundThree.setWidth(110);
        backgroundThree.setHeight(50);
        backgroundThree.relocate((screenOffsetX / 2) - background.getWidth() / 2 + 400, screenOffsetY - 75);

        clayIcon.relocate(background.getLayoutX() + 15, background.getLayoutY() + 10);
        coalIcon.relocate(background.getLayoutX() + 65, background.getLayoutY() + 10);
        copperIcon.relocate(background.getLayoutX() + 120, background.getLayoutY() + 10);
        ironIcon.relocate(background.getLayoutX() + 185, background.getLayoutY() + 10);
        rockIcon.relocate(background.getLayoutX() + 230, background.getLayoutY() + 10);

        woodIcon.relocate(backgroundTwo.getLayoutX() + 20, backgroundTwo.getLayoutY() + 10);

        powerIcon.relocate(backgroundThree.getLayoutX() + 30, backgroundThree.getLayoutY() + 10);

        resourcesText.relocate(background.getLayoutX() + 25, background.getLayoutY() + 15);

        clayText.relocate(background.getLayoutX() + 10, background.getLayoutY() + 30);
        coalText.relocate(background.getLayoutX() + 60, background.getLayoutY() + 30);
        copperText.relocate(background.getLayoutX() + 110, background.getLayoutY() + 30);
        ironText.relocate(background.getLayoutX() + 180, background.getLayoutY() + 30);
        rockText.relocate(background.getLayoutX() + 225, background.getLayoutY() + 30);

        woodText.relocate(backgroundTwo.getLayoutX() + 10, backgroundTwo.getLayoutY() + 30);

        powerText.relocate(backgroundThree. getLayoutX() + 10, backgroundThree.getLayoutY() + 30);
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

        else {
            clayText.setText("Clay: " + ResourcesHandler.getClayAmount());
            coalText.setText("Coal: " + ResourcesHandler.getCoalAmount());
            copperText.setText("Copper: " + ResourcesHandler.getCopperAmount());
            ironText.setText("Iron: " + ResourcesHandler.getIronAmount());
            rockText.setText("Rock: " + ResourcesHandler.getRockAmount());
            woodText.setText("Wood: " + ResourcesHandler.getWoodAmount());
            powerText.setText("Cow Power: " + ResourcesHandler.getPowerAmount());
        }
    }
}
