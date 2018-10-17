package menus;

import control.SimState;
import enviornment.Animal;
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

    private Animal clickedAnimal;
    StackPane stack;

    /**
     * Temp Creates a menu for the given animal.
     * @param animal The animal that the menu is to be created from
     */
    public GenericMenu(@NotNull Animal animal) {
        clickedAnimal = animal;
        stack = new StackPane();

        //The background for the StackPane
        Rectangle background = new Rectangle(0,0, 100, 150);
        background.setFill(Color.VIOLET);

        //The name of the animal
        Text idText = new Text(234,234, animal.getId());
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
        stack.setLayoutX(clickedAnimal.getX() + 55);
        stack.setLayoutY(clickedAnimal.getY() + 40);
    }
}
