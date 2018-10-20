package menus;

import control.SimState;
import environment.Cow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * A GenericMenu object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class GenericMenu {

    private Cow clickedCow;
    StackPane stack;

    /**
     * Temp Creates a menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    GenericMenu(@NotNull Cow cow) {
        clickedCow = cow;
        stack = new StackPane();

        //The background for the StackPane
        Rectangle background = new Rectangle(0,0, 100, 150);
        background.setFill(Color.VIOLET);
        background.setOpacity(0.7);

        //The name of the cow
        Text idText = new Text(234,234, cow.getId());
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        idText.setFill(Color.WHITE);

        stack.getChildren().addAll(background, idText);

        MenuHandler.openMenus.add(stack);
        SimState.playground.getChildren().add(stack);

        updateMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    public void updateMenu() {
        stack.setLayoutX(clickedCow.getX() + 55);
        stack.setLayoutY(clickedCow.getY() + 40);
    }
}
