package userInterface;

import com.sun.istack.internal.NotNull;
import cowParts.Cow;
import cowParts.CowHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import metaControl.CameraControl;
import metaControl.Input;
import metaControl.SimState;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.Playground;
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
    private static Group simSpeedGroup = new Group();

    //Structure for the cow control buttons
    private static Group controlGroup = new Group();
    private static Button heartAttackButton = new Button("Heart Attack");
    private static Button diseaseButton = new Button("Disease");
    private static Button detailedViewButton = new Button("Detailed View");
    private static Button storyViewButton = new Button("Story View");

    //Structure for buttons that open the other UIs
    private static Group differentUIGroup = new Group();
    private static Button tileUIButton = new Button("TileUI");
    private static Button resourcesUIButton = new Button("ResourcesUI");

    /**
     * Handles the creation of all static elements within the playgroundStaticUI. Buttons, text, and containers.
     */
    static void init() {
        createSpeedButtons();
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);

        populationText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        populationText.setFill(Color.BLACK);
        populationText.setX(5);
        populationText.setY(50);

        cowLinkBox.setSpacing(5);

        cowLinkScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cowLinkScrollBox.setContent(cowLinkBox);
        cowLinkScrollBox.setPrefHeight(100);
        cowLinkScrollBox.setLayoutX(5);
        cowLinkScrollBox.setLayoutY(60);

        heartAttackButton.setLayoutX(5);
        heartAttackButton.setLayoutY(170);
        //heartAttackButton.setGraphic(new ImageView(AssetLoading.loadUISprite("HeartAttackButton")));

        diseaseButton.setLayoutX(5);
        diseaseButton.setLayoutY(200);

        detailedViewButton.setLayoutX(5);
        detailedViewButton.setLayoutY(230);
        detailedViewButton.setGraphic(new ImageView(AssetLoading.loadUISprite("DetailedView")));

        storyViewButton.setLayoutX(5);
        storyViewButton.setLayoutY(260);
        storyViewButton.setGraphic(new ImageView(AssetLoading.loadUISprite("StoryView")));

        controlGroup.setDisable(true);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(310);

        actionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        actionText.setLayoutX(5);
        actionText.setLayoutY(320);

        accommodationsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        accommodationsText.setLayoutX(5);
        accommodationsText.setLayoutY(360);

        tileUIButton.setLayoutX(5);
        tileUIButton.setLayoutY(400);
        tileUIButton.setGraphic(new ImageView(AssetLoading.loadUISprite("TileUI")));


        resourcesUIButton.setLayoutX(5);
        resourcesUIButton.setLayoutY(430);
        resourcesUIButton.setGraphic(new ImageView(AssetLoading.loadUISprite("ResourcesUI")));

        timeOfDay.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        timeOfDay.setLayoutX(20);
        timeOfDay.setLayoutY(590);

        controlGroup.getChildren().addAll(heartAttackButton, diseaseButton, detailedViewButton, storyViewButton);
        UIText.getChildren().addAll(populationText, idText, actionText, accommodationsText, timeOfDay);
        differentUIGroup.getChildren().addAll(tileUIButton, resourcesUIButton);

        PlaygroundUI.staticUI.getChildren().addAll(
                background, simSpeedGroup, cowLinkBox, cowLinkScrollBox, UIText, controlGroup, differentUIGroup
        );

        detailedViewButton.setOnAction(event -> {
            SimState.setSimState("DetailedView");
            Playground.setPlayground("DetailedView");
            controlGroup.setDisable(true);
        });

        storyViewButton.setOnAction(event ->  {
            SimState.setSimState("StoryView");
            Playground.setPlayground("StoryView");
            controlGroup.setDisable(true);
        });

        tileUIButton.setOnAction(event -> {
            SimState.setSimState("TileView");
            PlaygroundUI.toggleTileUI();
        });

        resourcesUIButton.setOnAction(event -> PlaygroundUI.toggleResourcesUI());

        updatePopulationText();
    }

    /**
     * Handles the creation and implementation of the three simSpeed control buttons. Sets the id of the buttons
     * equal to the speed that they set the sim to.
     */
    private static void createSpeedButtons() {
        /*
        Creates the buttons used for setting the simSpeed. The id of the buttons is set to the corresponding speed
        that the button sets simSpeed to. SpeedButton is mutated to the three different buttons.
        */
        ToggleGroup simSpeedButtons = new ToggleGroup();
        ToggleButton speedButton;
        int buttonOffset = 0;

        for (double i = 0.5; i < 4; i *= 2) {
            speedButton = new ToggleButton("x" + ((i == 0.5) ? "1/2" : (int) i + "   "));
            speedButton.setToggleGroup(simSpeedButtons);
            speedButton.setSelected(i == 1);
            speedButton.setLayoutX(5 + (buttonOffset * 35));
            speedButton.setLayoutY(5);
            speedButton.setId(Long.toString(((long) ((double) 16_666_666L / i))));
            speedButton.setOnAction(event -> SimState.setSimSpeed(((ToggleButton) event.getTarget()).getId()));

            simSpeedGroup.getChildren().add(speedButton);
            buttonOffset++;
        }
    }

    /**
     * Updates the population text then creates text objects for every drawn cow then adds them to the playgroundStaticUI node.
     * The text objects display the animal's X and Y coordinates. Attached to 1 unique cow.
     */
    public static Hyperlink cowCreationEvent(String cowId) {
        Hyperlink cowLink;

        cowLink = new Hyperlink("Cow: " + cowId);
        cowLink.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
        cowLink.setTextFill(Color.BLACK);

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
        updateIdText();
        updateActionText();
        updateAccommodationsText();
        initControlButtons();
        updatePopulationText();
    }

    /**
     * Centers the camera around the animal in the clicked hyperlink, updates the corresponding UI, and opens up that
     * cow's menu.
     */
    private static void cowLinkClickEvent(@org.jetbrains.annotations.NotNull @NotNull String clickedCowLinkId) {
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
    private static void updateIdText() {
        Input.updateSelectedCows();

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
            actionText.setText(Input.selectedCows.get(0).getCurrentAction());
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
        timeOfDay.setText(new SimpleDateFormat("hh:mm").format(Time.getTime())
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
    }

    /**
     * Sets the containers for the intractable UI to enabled.
     */
    public static void enableUI() {
        simSpeedGroup.setDisable(false);
        cowLinkScrollBox.setDisable(false);

        if (!Input.selectedCows.isEmpty())
            controlGroup.setDisable(false);
    }
}