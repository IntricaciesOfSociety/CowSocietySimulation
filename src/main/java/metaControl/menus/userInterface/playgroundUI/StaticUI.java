package metaControl.menus.userInterface.playgroundUI;

import cowParts.CowHandler;
import cowParts.creation.Cow;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import metaControl.main.CameraControl;
import metaControl.main.Input;
import metaControl.main.SimState;
import metaControl.timeControl.Time;
import metaControl.metaEnvironment.AssetLoading;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import resourcesManagement.ResourcesHandler;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Handles the creation and updating of all staticUI elements that are needed during a 'playing' simState.
 */
public class StaticUI {

    //UI text and container
    private static Group UIText = new Group();
    private static Text idText = new Text("Cow: N/A");
    private static Text populationText = new Text("Population : " + CowHandler.liveCowList.size());
    private static Label actionText = new Label();
    private static Label accommodationsText = new Label();
    private static Text timeOfDay = new Text();

    //Structure for the cowLinks
    private static VBox cowLinkBox = new VBox();
    private static ScrollPane cowLinkScrollBox = new ScrollPane();

    //Structure for sim speed buttons
    private static HBox simSpeedGroup = new HBox();

    //Structure for the cow control buttons
    private static Group controlGroup = new Group();
    private static Button heartAttackButton = new Button();
    private static Button diseaseButton = new Button();
    private static Button detailedViewButton = new Button();
    private static Button storyViewButton = new Button();
    private static Button trackingButton = new Button();
    private static Button establishmentViewButton = new Button();

    //Structure for buttons that open the other UIs
    private static Group differentUIGroup = new Group();
    private static Button tileUIButton = new Button();
    private static Button resourcesUIButton = new Button();
    private static Button techTreeButton = new Button();

    /**
     * Handles the creation of all static elements within the playgroundStaticUI. Buttons, text, and containers.
     */
    static void init() {
        ToggleGroup simSpeedButtons = new ToggleGroup();

        ImageView halfSpeed = new ImageView(AssetLoading.loadUISprite("X0.5SpeedButton"));
        ToggleButton speedButton1 = new ToggleButton();
        halfSpeed.setScaleX(2.0);
        halfSpeed.setScaleY(2.0);
        speedButton1.setGraphic(halfSpeed);
        speedButton1.setId(Long.toString(((long) ((double) 128_666_666L / 0.5))));
        speedButton1.setOnAction(event -> SimState.setSimSpeed(((ToggleButton) event.getTarget()).getId()));
        speedButton1.setToggleGroup(simSpeedButtons);

        ImageView normalSpeed = new ImageView(AssetLoading.loadUISprite("X1SpeedButton"));
        ToggleButton speedButton2 = new ToggleButton();
        normalSpeed.setScaleX(2.0);
        normalSpeed.setScaleY(2.0);
        speedButton2.setGraphic(normalSpeed);
        speedButton2.setId(Long.toString(((long) ((double) 128_666_666L / 1))));
        speedButton2.setOnAction(event -> SimState.setSimSpeed(((ToggleButton) event.getTarget()).getId()));
        speedButton2.setToggleGroup(simSpeedButtons);

        ImageView doubleSpeed = new ImageView(AssetLoading.loadUISprite("X2SpeedButton"));
        ToggleButton speedButton3 = new ToggleButton();
        doubleSpeed.setScaleX(2.0);
        doubleSpeed.setScaleY(2.0);
        speedButton3.setGraphic(doubleSpeed);
        speedButton3.setId(Long.toString(((long) ((double) 128_666_666L / 2))));
        speedButton3.setOnAction(event -> SimState.setSimSpeed(((ToggleButton) event.getTarget()).getId()));
        speedButton3.setToggleGroup(simSpeedButtons);

        simSpeedGroup.setSpacing(5);

        populationText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        populationText.setFill(Color.RED);

        cowLinkBox.setSpacing(5);

        cowLinkScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cowLinkScrollBox.setContent(cowLinkBox);
        cowLinkScrollBox.setPrefHeight(105);
        cowLinkScrollBox.setFocusTraversable(false);

        ImageView heartAttackImage = new ImageView(AssetLoading.loadUISprite("HeartAttack"));
        heartAttackImage.setScaleX(2);
        heartAttackImage.setScaleY(2);
        heartAttackButton.setFocusTraversable(false);
        heartAttackButton.setGraphic(heartAttackImage);

        ImageView diseaseImage = new ImageView(AssetLoading.loadUISprite("Disease"));
        diseaseImage.setScaleX(2);
        diseaseImage.setScaleY(2);
        diseaseButton.setFocusTraversable(false);
        diseaseButton.setGraphic(diseaseImage);

        ImageView detailedImage = new ImageView(AssetLoading.loadUISprite("DetailedView"));
        detailedImage.setScaleX(2);
        detailedImage.setScaleY(2);
        detailedViewButton.setFocusTraversable(false);
        detailedViewButton.setGraphic(detailedImage);

        ImageView storyImage = new ImageView(AssetLoading.loadUISprite("StoryView"));
        storyImage.setScaleX(2);
        storyImage.setScaleY(2);
        storyViewButton.setFocusTraversable(false);
        storyViewButton.setGraphic(storyImage);

        establishmentViewButton.setFocusTraversable(false);

        controlGroup.setDisable(true);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFill(Color.RED);

        actionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        actionText.setTextFill(Color.RED);

        accommodationsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        accommodationsText.setTextFill(Color.RED);

        actionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        actionText.setTextFill(Color.RED);

        accommodationsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        accommodationsText.setTextFill(Color.RED);

        ImageView tileImage = new ImageView(AssetLoading.loadUISprite("TileUI"));
        tileImage.setScaleX(2);
        tileImage.setScaleY(2);

        tileUIButton.setFocusTraversable(false);
        tileUIButton.setGraphic(tileImage);

        ImageView resourcesImage = new ImageView(AssetLoading.loadUISprite("ResourcesUI"));
        resourcesImage.setScaleX(2);
        resourcesImage.setScaleY(2);
        resourcesUIButton.setFocusTraversable(false);
        resourcesUIButton.setGraphic(resourcesImage);

        ImageView trackingImage = new ImageView(AssetLoading.loadUISprite("Tracking"));
        trackingImage.setScaleX(2);
        trackingImage.setScaleY(2);
        trackingButton.setLayoutX(15);
        trackingButton.setLayoutY(240);
        trackingButton.setFocusTraversable(false);
        trackingButton.setGraphic(trackingImage);

        ImageView techTreeButtonImage = new ImageView(AssetLoading.loadUISprite("techTreeButton"));
        techTreeButtonImage.setScaleX(2);
        techTreeButtonImage.setScaleY(2);
        techTreeButtonImage.setFitHeight(15);
        techTreeButtonImage.setPreserveRatio(true);
        techTreeButton.setLayoutX(15);
        techTreeButton.setLayoutY(270);
        techTreeButton.setFocusTraversable(false);
        techTreeButton.setGraphic(techTreeButtonImage);

        timeOfDay.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        timeOfDay.setFill(Color.RED);

        simSpeedGroup.getChildren().addAll(speedButton1, speedButton2, speedButton3);
        controlGroup.getChildren().addAll(trackingButton, heartAttackButton, diseaseButton, detailedViewButton, storyViewButton);
        UIText.getChildren().addAll(populationText, idText, actionText, accommodationsText, timeOfDay);
        differentUIGroup.getChildren().addAll(tileUIButton, resourcesUIButton, techTreeButton, establishmentViewButton);

        PlaygroundUIHandler.staticUI.getChildren().addAll(
                 simSpeedGroup, cowLinkBox, cowLinkScrollBox, UIText, controlGroup, differentUIGroup
        );

        detailedViewButton.setOnAction(event -> {
            SimState.setSimState("DetailedView");
            PlaygroundHandler.setPlayground("DetailedView");
            controlGroup.setDisable(true);
        });

        storyViewButton.setOnAction(event ->  {
            SimState.setSimState("StoryView");
            PlaygroundHandler.setPlayground("StoryView");
            controlGroup.setDisable(true);
        });

        tileUIButton.setOnAction(event -> {
            SimState.setSimState("TileView");
            PlaygroundUIHandler.toggleTileUI();
        });

        resourcesUIButton.setOnAction(event -> {
            SimState.setSimState("ResourcesView");
            PlaygroundUIHandler.toggleResourcesUI();
        });

        trackingButton.setOnAction(event -> {

        });

        techTreeButton.setOnAction(event -> {
            SimState.setSimState("TechTreeView");
            PlaygroundHandler.setPlayground("TechTreeView");
        });

        establishmentViewButton.setOnAction(event -> {
            SimState.setSimState("EstablishmentView");
            PlaygroundHandler.setPlayground("EstablishmentView");
        });

        updatePopulationText();
        updateUIPlacements();
    }

    public static void updateUIPlacements() {
        int screenOffsetX = SimState.getScreenWidth();
        int screenOffsetY = SimState.getScreenHeight();

        simSpeedGroup.relocate(5,5);

        populationText.setX(5);
        populationText.setY(60);

        cowLinkScrollBox.setLayoutX(5);
        cowLinkScrollBox.setLayoutY(70);

        heartAttackButton.setLayoutX(screenOffsetX - 85);
        heartAttackButton.setLayoutY(20);

        diseaseButton.setLayoutX(screenOffsetX - 85);
        diseaseButton.setLayoutY(50);

        detailedViewButton.setLayoutX(15);
        detailedViewButton.setLayoutY(180);

        storyViewButton.setLayoutX(15);
        storyViewButton.setLayoutY(210);

        idText.setX(5);
        idText.setY(310);

        actionText.setLayoutX(5);
        actionText.setLayoutY(330);

        accommodationsText.setLayoutX(5);
        accommodationsText.setLayoutY(350);

        tileUIButton.setLayoutX(15);
        tileUIButton.setLayoutY(390);

        resourcesUIButton.setLayoutX(15);
        resourcesUIButton.setLayoutY(420);

        establishmentViewButton.relocate(15, 440);

        timeOfDay.setLayoutX(20);
        timeOfDay.setLayoutY(screenOffsetY - 25);

    }

    /**
     * Updates the population text then creates text objects for every drawn cow then adds them to the playgroundStaticUI node.
     * The text objects display the animal's X and Y coordinates. Attached to 1 unique cow.
     */
    public static Hyperlink cowCreationEvent(String cowId) {
        Hyperlink cowLink;

        cowLink = new Hyperlink("Cow: " + cowId);
        cowLink.setFocusTraversable(false);
        cowLink.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
        cowLink.setTextFill(Color.RED);

        cowLinkBox.getChildren().add(cowLink);

        //When the link is clicked, center the camera around that cow. Done by parsing the cow's id.
        cowLink.setOnAction(event -> cowLinkClickEvent(((Hyperlink) event.getTarget()).getText()));

        updatePopulationText();
        ResourcesHandler.updatePower();
        return cowLink;
    }

    /**
     * Updates the idText and control buttons whenever a cow is clicked.
     */
    public static void cowClickEvent() {
        StaticUI.updateIdText();
        updateActionText();
        updateAccommodationsText();
        initControlButtons();
        updatePopulationText();
    }

    /**
     * Centers the camera around the animal in the clicked hyperlink, updates the corresponding UI, and opens up that
     * cow's menu.
     */
    private static void cowLinkClickEvent(@org.jetbrains.annotations.NotNull String clickedCowLinkId) {
        Cow cowFromId = CowHandler.findCow(clickedCowLinkId.substring(5));
        Objects.requireNonNull(cowFromId).openMenu();

        cowClickEvent();
        CameraControl.moveCameraToCow(cowFromId);
    }

    /**
     * Sets the control buttons to visible and updates the button listeners to the correct cow.
     */
    private static void initControlButtons() {
        controlGroup.setDisable(false);

        if (Input.selectedCows.size() > 1) {
            heartAttackButton.setOnAction(event ->  CowHandler.killAll(Input.selectedCows));
            diseaseButton.setOnAction(event -> CowHandler.diseaseAll(Input.selectedCows));
        }
        else if (Input.selectedCows.size() == 1) {
            heartAttackButton.setOnAction(event -> Input.selectedCows.get(0).kill());
            diseaseButton.setOnAction(event -> Input.selectedCows.get(0).disease());
        }
        else
            controlGroup.setDisable(true);
    }

    /**
     * Updates the idText variable to Input.selectedCow
     */
    public static void updateIdText() {
        if (Input.selectedCows.size() > 1)
            idText.setText(Input.selectedCows.size() + " cows selected");
        else if (Input.selectedCows.size() == 1)
            idText.setText("Cow: " + Input.selectedCows.get(0).getId());
        else
            idText.setText("No cow selected");
    }

    /**
     * Updates the action text within the staticUI based off the cows selected
     */
    public static void updateActionText() {
        if (Input.selectedCows.size() > 1)
            actionText.setText("Many actions");
        else if (Input.selectedCows.size() == 1)
            actionText.setText(Input.selectedCows.get(0).getcurrentBehavior());
        else
            actionText.setText("");
    }

    /**
     * Updates the accommodations text within the staticUI based off the cows selected
     */
    private static void updateAccommodationsText() {
        if (Input.selectedCows.size() > 1)
            accommodationsText.setText("Many residences");
        else if (Input.selectedCows.size() == 1) {
            accommodationsText.setText(Input.selectedCows.get(0).getLivingSpace().getStreetAddress());
        }
        else
            accommodationsText.setText("");
    }

    /**
     * Updates the populationText based off the size of Cow.liveCowList
     */
    private static void updatePopulationText() {
        populationText.setText("Population : " + (CowHandler.liveCowList.size()));
    }

    /**
     * Updates the timeOfDay text equal to the time given by SimState.getDate.
     */
    public static void updateTimeOfDayText(int timeInDay) {
        timeOfDay.setText(new SimpleDateFormat("d/HH:mm").format(Time.getTime())
                + ((timeInDay <= 1200) ? " AM" : " PM"));
    }

    /**
     * Removes a dead cow from the hyperlink list.
     * @param cowLink The link corresponding to the dead cow
     */
    public static void cowDeathEventUpdate(Hyperlink cowLink) {
        cowClickEvent();
        updatePopulationText();
        cowLinkBox.getChildren().remove(cowLink);
    }

    /**
     * Sets the containers for the intractable UI to disabled.
     */
    public static void disableUI() {
        cowLinkScrollBox.setDisable(true);
        controlGroup.setDisable(true);
        simSpeedGroup.setDisable(true);
        differentUIGroup.setDisable(true);
    }

    /**
     * Sets the containers for the intractable UI to enabled.
     */
    public static void enableUI() {
        simSpeedGroup.setDisable(false);
        cowLinkScrollBox.setDisable(false);
        differentUIGroup.setDisable(false);

        if (!Input.selectedCows.isEmpty())
            controlGroup.setDisable(false);
    }
}