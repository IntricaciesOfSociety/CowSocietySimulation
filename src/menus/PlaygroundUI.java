package menus;

import control.CameraControl;
import control.Input;
import control.SimState;
import environment.Cow;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Creates and handles the UI for the left side of the scene.
 */
public class PlaygroundUI {
    //The root for all of the UI elements
    public static Pane playgroundUI = new Pane();

    //idText displays the animal that has either been selected, or has been moused over
    private static Text idText = new Text("Cow: N/A");
    private static Text populationText = new Text("Population : " + Cow.cowList.size());

    //Structure for the cowLinks
    private static VBox cowLinkBox = new VBox();
    private static ScrollPane cowLinkScrollBox = new ScrollPane();

    //Structure for the cow control buttons
    private static Group controlGroup = new Group();
    private static Button heartAttackButton = new Button("Heart Attack");
    private static Button diseaseButton = new Button("Disease");

    /**
     * Handles the creation of all static elements within the playgroundUI. Buttons, text, and containers.
     */
    public static void createUI() {
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(50);

        populationText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        populationText.setFill(Color.BLACK);
        populationText.setX(5);
        populationText.setY(70);

        cowLinkBox.setSpacing(5);

        cowLinkScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cowLinkScrollBox.setContent(cowLinkBox);
        cowLinkScrollBox.setPrefHeight(100);
        cowLinkScrollBox.setLayoutX(5);
        cowLinkScrollBox.setLayoutY(80);

        heartAttackButton.setLayoutX(5);
        heartAttackButton.setLayoutY(190);

        diseaseButton.setLayoutX(5);
        diseaseButton.setLayoutY(220);

        controlGroup.setDisable(true);
        controlGroup.getChildren().add(heartAttackButton);
        controlGroup.getChildren().add(diseaseButton);

        updatePopulationText();

        PlaygroundUI.playgroundUI.getChildren().addAll(
                background, cowLinkBox, cowLinkScrollBox, idText, populationText, controlGroup
        );

        /*
        Creates the buttons used for setting the simSpeed. The id of the buttons is set to the corresponding speed
        that the button sets simSpeed to.
        */
        ToggleGroup simSpeedButtons = new ToggleGroup();

        //Mutated into three different buttons
        ToggleButton speedButton;
        int buttonOffset = 0;

        for (double i = 0.5; i < 4; i *= 2) {
            speedButton = new ToggleButton("x" + ((i == 0.5) ? "1/2" : (int) i + "   "));
            speedButton.setToggleGroup(simSpeedButtons);
            speedButton.setSelected(i == 1);

            speedButton.setLayoutX(5 + (buttonOffset * 35));
            buttonOffset++;
            speedButton.setLayoutY(5);

            speedButton.setId(Long.toString(((long) ((double) 16_666_666L / i))));
            playgroundUI.getChildren().add(speedButton);

            //When the button is clicked, set the simSpeed to the value of the id of the button
            speedButton.setOnAction(event -> SimState.setSimSpeed(Input.getParsedId(event.toString())));
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

        //When the button is clicked, center the camera around that cow. Done by parsing the cow's id.
        cowLink.setOnAction(event -> cowLinkClickEvent(Input.getParsedId(event.getSource().toString())));

        updatePopulationText();
        return cowLink;
    }

    /**
     * Updates the idText and control buttons whenever a cow is clicked.
     */
    public static void cowClickEvent() {
        updateIdText();
        initControlButtons();
    }

    /**
     * Centers the camera around the animal in the clicked hyperlink, updates the corresponding UI, and opens up that
     * cow's menu.
     */
    private static void cowLinkClickEvent(String clickedCowLinkId) {
        Cow cowFromId = Cow.findCow(clickedCowLinkId);
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
            heartAttackButton.setId(Input.selectedCows.toString());
            heartAttackButton.setOnAction(event -> Cow.killAll(Cow.findCows(event.getSource().toString())) );
        }
        else if (Input.selectedCows.size() == 1) {
            heartAttackButton.setId(Input.selectedCows.get(0));
            heartAttackButton.setOnAction(event -> Cow.findCow(Input.getParsedId(event.getSource().toString())).kill() );
        }
    }

    /**
     * Updates the idText variable to Input.selectedCow
     */
    private static void updateIdText() {
        Input.updateSelectedCows();
        if (Input.selectedCows.size() > 1)
            idText.setText(Input.selectedCows.size() + " cows selected");
        else if (Input.selectedCows.size() == 1)
            idText.setText("Cow: " + Input.selectedCows.get(0));
        else
            idText.setText("No cow selected");
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
}