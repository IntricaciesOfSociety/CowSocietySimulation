package menus;

import control.CameraControl;
import control.Input;
import control.SimState;
import environment.Cow;
import environment.Playground;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Creates and handles the UI for the left side of the scene.
 */
public class PlaygroundUI {
    //The root for all of the UI elements
    public static Pane playgroundUI = new Pane();

    //UI text and container
    private static Group UIText = new Group();
    private static Text idText = new Text("Cow: N/A");
    private static Text populationText = new Text("Population : " + Cow.cowList.size());
    private static Label actionText = new Label();
    private static Label accommodationsText = new Label("Lives at 939 Drive \n with BigBeefy");

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

    /**
     * Handles the creation of all static elements within the playgroundUI. Buttons, text, and containers.
     */
    public static void createUI() {
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

        diseaseButton.setLayoutX(5);
        diseaseButton.setLayoutY(200);

        detailedViewButton.setLayoutX(5);
        detailedViewButton.setLayoutY(230);

        controlGroup.setDisable(true);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(280);

        actionText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        actionText.setLayoutX(5);
        actionText.setLayoutY(290);

        accommodationsText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        accommodationsText.setLayoutX(5);
        accommodationsText.setLayoutY(330);

        controlGroup.getChildren().addAll(heartAttackButton, diseaseButton, detailedViewButton);
        UIText.getChildren().addAll(populationText, idText, actionText, accommodationsText);

        PlaygroundUI.playgroundUI.getChildren().addAll(
                background, simSpeedGroup, cowLinkBox, cowLinkScrollBox, UIText, controlGroup
        );

        detailedViewButton.setOnAction(event -> {
            SimState.setSimState("Menu");
            Playground.setPlayground("DetailedView");
            controlGroup.setDisable(true);
        });

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
     * Updates the population text then creates text objects for every drawn cow then adds them to the playgroundUI node.
     * The text objects display the animal's X and Y coordinates. Attached to 1 unique cow.
     */
    public static Hyperlink cowCreationEvent(String cowId) {
        Hyperlink cowLink;
        ArrayList<Hyperlink> cowLinkList = new ArrayList<>();

        cowLink = new Hyperlink("Cow: " + cowId);
        cowLink.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
        cowLink.setTextFill(Color.BLACK);

        cowLinkList.add(cowLink);
        cowLinkBox.getChildren().add(cowLink);

        //When the link is clicked, center the camera around that cow. Done by parsing the cow's id.
        cowLink.setOnAction(event -> cowLinkClickEvent(((Hyperlink) event.getTarget()).getText()));

        updatePopulationText();
        return cowLink;
    }

    /**
     * Updates the idText and control buttons whenever a cow is clicked.
     */
    public static void cowClickEvent() {
        updateIdText();
        updateActionText();
        initControlButtons();
    }

    /**
     * Centers the camera around the animal in the clicked hyperlink, updates the corresponding UI, and opens up that
     * cow's menu.
     */
    private static void cowLinkClickEvent(@NotNull String clickedCowLinkId) {
        Cow cowFromId = Cow.findCow(clickedCowLinkId.substring(5));
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
            heartAttackButton.setOnAction(event -> Cow.killAll(Input.selectedCows));
            diseaseButton.setOnAction(event -> Cow.diseaseAll(Input.selectedCows));
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

    private static void updateActionText() {
        if (Input.selectedCows.size() > 1)
            actionText.setText("Many actions");
        else if (Input.selectedCows.size() == 1)
            actionText.setText(Input.selectedCows.get(0).getCurrentAction());
        else
            actionText.setText("");
    }

    /**
     * Updates the populationText based off the size of Cow.cowList.
     */
    private static void updatePopulationText() {
        populationText.setText("Population : " + Cow.cowList.size());
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