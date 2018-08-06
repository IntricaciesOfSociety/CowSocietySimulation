package menus;

import control.SimState;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class GenericMenu {

    StackPane stack;

    /**
     * Temp Creates a menu
     * @param id The id of the object the menu is being created from
     */
    GenericMenu(String id) {
        stack = new StackPane();
        stack.setId(id);

        Rectangle background = new Rectangle(0,0, 100, 150);

        background.setFill(Color.VIOLET);
;
        Text idText = new Text(234,234,id);
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        idText.setFill(Color.WHITE);

        stack.setLayoutX(100);
        stack.setLayoutY(75);
        stack.getChildren().addAll(background, idText);

        MenuHandler.openMenus.add(stack);
        SimState.root.getChildren().add(stack);
    }
}
