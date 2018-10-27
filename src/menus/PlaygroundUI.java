package menus;

import control.CameraControl;
import control.Input;
import control.SimState;
import environment.Cow;
import javafx.scene.Group;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * Creates and handles the UI for the left side of the scene.
 */
public class PlaygroundUI {

    public static Pane playgroundUI = new Pane();

    //idText displays the animal that has either been selected, or has been moused over
    private static Text idText = new Text(234,234, "Cow: N/A");

    //cowLinkList holds clickable text objects that centers the camera view around that cow.
    private static ArrayList<Hyperlink> cowLinkList = new ArrayList<>();
    private static Group cowLinkBox = new Group();
    private static ScrollPane cowLinkScrollBox = new ScrollPane();

    //The offset of the simSpeedButtons
    private static int buttonOffset = 0;

    /**
     * Handles the creation of all elements within the playgroundUI. Buttons and text.
     */
    public static void createUI() {
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);

        ToggleGroup simSpeedButtons = new ToggleGroup();
        //Mutated into three different buttons
        ToggleButton speedButtons;

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(50);

        cowLinkScrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        cowLinkScrollBox.setFitToHeight(false);
        cowLinkScrollBox.setHmax(10);
        cowLinkScrollBox.setContent(cowLinkBox);

        PlaygroundUI.playgroundUI.getChildren().addAll(background, idText, cowLinkBox, cowLinkScrollBox);

        /*
        Creates the buttons used for setting the simSpeed. The id of the buttons is set to the corresponding speed
        that the button sets simSpeed to.
        */
        for (double i = 0.5; i < 4; i *= 2) {
            speedButtons = new ToggleButton("x" + ((i == 0.5) ? "1/2" : (int) i + "   "));
            speedButtons.setToggleGroup(simSpeedButtons);
            speedButtons.setSelected(i == 1);

            speedButtons.setLayoutX(5 + (buttonOffset * 35));
            buttonOffset++;
            speedButtons.setLayoutY(5);

            speedButtons.setId(Long.toString(((long) ((double) 16_666_666L / i))));

            //When the button is clicked, set the simSpeed to the value of the id of the button
            speedButtons.setOnAction(event -> SimState.setSimSpeed(Input.getParsedId(event.toString())));

            //PlaygroundUI.playgroundUI.getChildren().add(speedButtons);
        }
    }

    /**
     * Creates text objects for every drawn cow then adds them to the playgroundUI node. The text objects display the
     * animal's X and Y coordinates.
     */
    private static void updateForNewCows() {
        Hyperlink cowLink;

        for (int i = 0; i < Cow.cowList.size(); i++) {
            cowLink = new Hyperlink("Cow: " + Cow.cowList.get(i).getId() + ": ");

            cowLink.setFont(Font.font("Verdana", FontWeight.BOLD, 8));
            cowLink.setTextFill(Color.BLACK);
            cowLink.setLayoutX(5);
            cowLink.setLayoutY(70 + (i * 20));

            //Hyperlink@268f86f3[styleClass=hyperlink]'Cow: Big Beefy29: '
            cowLink.setOnAction(event -> {
                CameraControl.moveCameraToCow(Input.getParsedId(event.getSource().toString()));
            });

            cowLinkList.add(cowLink);
            cowLinkBox.getChildren().add(cowLink);
        }
    }

    /**
     * Updates the various elements within playgroundUI that change based on some sort of click event. Including: idText
     */
    public static void mouseEventUpdate() {
        idText.setText("Cow: " + Input.selectedCow);
    }

    /**
     * Periodically updates during main sim loop. Updates various elements that need to be constantly updated. Including:
     * animalCoords
     */
    public static void update() {
        if (cowLinkList.size() < Cow.cowList.size())
            updateForNewCows();
    }
}
