package menus;

import environment.Cow;
import environment.Playground;
import javafx.scene.layout.Pane;
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

    private Text hungerText;
    private Text happinessText;
    private Text ageText;

    Pane stack;

    /**
     * Temp Creates a menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    GenericMenu(@NotNull Cow cow) {
        clickedCow = cow;
        stack = new Pane();

        //The background for the StackPane
        Rectangle background = new Rectangle(0,0, 110, 150);
        background.setFill(Color.BLACK);
        //background.setOpacity(0.7);

        //The name of the cow
        Text idText = new Text(5,15, cow.getId());
        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        idText.setFill(Color.WHITE);

        //The age of the cow
        ageText = new Text(5, 30, "Age: " + Integer.toString(clickedCow.getAge()));
        ageText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        ageText.setFill(Color.RED);

        //The hunger of the cow
        hungerText = new Text(5, 45, "Hunger: " + Integer.toString(clickedCow.getHunger()));
        hungerText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        hungerText.setFill(Color.RED);

        happinessText = new Text(5, 60, "Happiness: " + Integer.toString(clickedCow.getHappiness()));
        happinessText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        happinessText.setFill(Color.RED);

        stack.getChildren().addAll(background, idText, hungerText, happinessText, ageText);

        Playground.playground.getChildren().add(stack);

        updateMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    public void updateMenu() {
        stack.relocate((clickedCow.getX() + 55), (clickedCow.getY() + 40));
        hungerText.setText("Hunger: " + Integer.toString(clickedCow.getHunger()));
        happinessText.setText("Happiness: " + Integer.toString(clickedCow.getHappiness()));
        ageText.setText("Age: " + Integer.toString(clickedCow.getAge()));
    }
}
