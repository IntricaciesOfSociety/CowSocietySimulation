package menus;

import control.CameraControl;
import control.Input;
import control.SimState;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlaygroundUI {

    private static Text idText = new Text(234,234, "Animal: N/A");



    public static void create() {
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);

        ToggleGroup gameSpeedButtons = new ToggleGroup();
        ToggleButton speedButtons;

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(50);

        SimState.playgroundUI.getChildren().addAll(background, idText);

        for (int i = 0; i < 3; i++) {
            speedButtons = new ToggleButton("x" + ((i == 0) ? "1/2" : i + "   "));
            speedButtons.setLayoutX(5 + (i * 35));
            speedButtons.setLayoutY(5);
            speedButtons.setToggleGroup(gameSpeedButtons);

            long idLogic = (16_666_666 * ( ((long) i + 1) * 2) ) - ((16_666_666 * ( (long) i + 1) * 2) / 2);
            String buttonId = Long.toString(idLogic);
            speedButtons.setId(buttonId);

            speedButtons.setOnAction(event -> SimState.setSimSpeed(Input.getParsedId(event.toString())));
            SimState.playgroundUI.getChildren().add(speedButtons);
        }
    }

    public static void update() {
        SimState.playgroundUI.setLayoutX(CameraControl.getX());
        SimState.playgroundUI.setLayoutY(CameraControl.getY());

        idText.setText("Animal: " + Input.objectMouseIsOn);
    }
}
