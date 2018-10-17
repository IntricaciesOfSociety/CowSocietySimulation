package menus;

import control.Input;
import control.SimState;
import enviornment.Animal;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    //idText displays the animal that has either been selected, or has been moused over
    private static Text idText = new Text(234,234, "Animal: N/A");

    //animalCoordsList holds text objects that display the x and y coordinates of every animal in the playground
    private static ArrayList<Text> animalCoordsList = new ArrayList<>();

    //The offset of the simSpeedButtons
    private static int buttonOffset = 0;

    /**
     * Handles the creation of all elements within the playgroundUI. Buttons and text.
     */
    public static void create() {
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);
        ToggleGroup simSpeedButtons = new ToggleGroup();

        //Mutated into three different buttons
        ToggleButton speedButtons;

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(50);

        SimState.playgroundUI.getChildren().addAll(background, idText);

        /* Creates the buttons used for setting the simSpeed. The id of the buttons is set to the corresponding speed
        that the button sets simSpeed to. */
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

            SimState.playgroundUI.getChildren().add(speedButtons);
        }
    }

    /**
     * Creates text objects for every drawn cow then adds them to the playgroundUI node. The text objects display the
     * animal's X and Y coordinates.
     */
    private static void updateForNewCows() {
        Text animalCoords;

        for (int j = 0; j < Animal.animalList.size(); j++) {
            animalCoords = new Text(0,0, "Animal: " + j + ": ");

            animalCoords.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
            animalCoords.setFill(Color.BLACK);
            animalCoords.setX(5);
            animalCoords.setY(70 + (j * 20));

            animalCoordsList.add(animalCoords);
            SimState.playgroundUI.getChildren().add(animalCoords);
        }
    }

    /**
     * Updates the various elements within playgroundUI that change based on some sort of click event. Including: idText
     */
    public static void mouseEventUpdate() {
        idText.setText("Animal: " + Input.objectMouseIsOn);
    }

    /**
     * Periodically updates during main sim loop. Updates various elements that need to be constantly updated. Including:
     * animalCoords
     */
    public static void update() {
        if (animalCoordsList.size() < Animal.animalList.size())
            updateForNewCows();

        for (int i = 0; i < animalCoordsList.size(); i++) {
            animalCoordsList.get(i).setText("Animal " + i + "X: " + (int) Animal.animalList.get(i).getX() + " Y: " + (int) Animal.animalList.get(i).getY());
        }
    }
}
