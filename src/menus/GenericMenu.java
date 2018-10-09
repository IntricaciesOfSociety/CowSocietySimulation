package menus;

import control.SimState;
import enviornment.Animal;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class GenericMenu {

    Animal clickedAnimal;
    StackPane stack;

    /**
     * Temp Creates a generic menu only containing an ID
     * @param id The id of the object the menu is being created from
     */
    public GenericMenu(String id) {
        stack = new StackPane();
        Rectangle background = new Rectangle(0,0, 100, 150);
        background.setFill(Color.VIOLET);
;
        Text idText = new Text(234,234, id);
        MenuHandler.openMenus.add(stack);
        SimState.playground.getChildren().add(stack);
    }

    /**
     * Temp Creates a menu for an animal
     * @param animal The animal that the menu is to be created from
     */
    public GenericMenu(Animal animal) {
        clickedAnimal = animal;
        stack = new StackPane();

        Rectangle background = new Rectangle(0,0, 100, 150);
        background.setFill(Color.VIOLET);

        Text idText = new Text(234,234, animal.getId());
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        idText.setFill(Color.WHITE);

        stack.getChildren().addAll(background, idText);

        MenuHandler.openMenus.add(stack);
        SimState.playground.getChildren().add(stack);

        updateMenu();
    }

    public void updateMenu() {
        stack.setLayoutX(clickedAnimal.getX() + 65);
        stack.setLayoutY(clickedAnimal.getY() + 50);
    }
}
