package menus;

import control.Input;
import control.SimState;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Creates and handles the UI for the left side of the scene.
 */
public class PlaygroundUI {

    //idText displays the animal that hsd either been selected, or has been moused over
    private static Text idText = new Text(234,234, "Animal: N/A");

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

        /* Creates the buttons used for setting the simSpeed. The id of the buttons is set to the corrseponding speed
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

        SimState.playgroundUI.getChildren().addAll(background, idText);
    }

    /**
     * Updates the various elements within playgroundUI that change values.
     */
    public static void update() {
        idText.setText("Animal: " + Input.objectMouseIsOn);
    }
}
