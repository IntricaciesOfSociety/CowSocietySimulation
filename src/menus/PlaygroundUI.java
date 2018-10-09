package menus;

import control.CameraControl;
import control.Input;
import control.SimState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlaygroundUI {

    private static Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);
    private static Text idText = new Text(234,234, "Animal: N/A");

    public static void create() {
        Rectangle background = new Rectangle(150, 600, Color.DARKGOLDENROD);

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFill(Color.BLACK);
        idText.setX(5);
        idText.setY(30);

        SimState.playgroundUI.getChildren().addAll(background, idText);

    }

    public static void update() {
        SimState.playgroundUI.setLayoutX(CameraControl.getX());
        SimState.playgroundUI.setLayoutY(CameraControl.getY());

        idText.setText("Animal: " + Input.objectMouseIsOn);
    }
}
