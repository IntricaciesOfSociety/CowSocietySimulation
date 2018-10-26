package environment;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class Playground {

    public static Pane playground = new Pane();

    /**
     * Creates the black border outline for the playground node. Is bound to the bounds of the playground, and is resized
     * accordingly. Automatically updates.
     */
    public static void createBorders() {
        playground.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}
