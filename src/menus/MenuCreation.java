package menus;

import control.SimState;
import environment.Cow;
import environment.Playground;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A MenuCreation object is a StackPane menu that holds information based off of the object given. Used currently for the
 * specific animal menus.
 */
public class MenuCreation {

    private Cow clickedCow;

    //Status texts
    private Text hungerText;
    private Text happinessText;
    private Text ageText;

    Pane stack;

    /**
     * Creates a new menu (detailedView) from the cows selected when the menu was opened.
     * @param cowsPreviouslySelected The cows that were previously selected.
     */
    MenuCreation(@NotNull ArrayList<Cow> cowsPreviouslySelected) {
        StringBuilder idTextString = new StringBuilder();
        StringBuilder statsTextString = new StringBuilder();

        for (Cow cowAlreadySelected : cowsPreviouslySelected) {
            idTextString.append(cowAlreadySelected.getId()).append(" | ");

            statsTextString.append("HUNGER: ").append(cowAlreadySelected.getHunger()).append("\n");
            statsTextString.append("AGE: ").append(cowAlreadySelected.getAge()).append("\n");
            statsTextString.append("HAPPINESS: ").append(cowAlreadySelected.getHappiness()).append("\n");
            statsTextString.append("Lorunm Ipsum foo jhefiu" + "\n" + "hsioufhsouiefhsoiuehfi" + "\n" + "gfuisegfushefiuhseid");
        }

        Label statsText = new Label(statsTextString.toString());
        Button exitButton = new Button("EXIT");

        Text idText = new Text(160, 30, idTextString.toString());
        Rectangle background = new Rectangle(150, 0, 650, 600);


        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
        statsText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        background.setFill(Color.BLACK);
        idText.setFill(Color.WHITE);
        idText.setFill(Color.RED);

        statsText.setLayoutX(160);
        statsText.setLayoutY(50);

        exitButton.setLayoutX(160);
        exitButton.setLayoutY(560);
        exitButton.setOnAction(event -> {
            SimState.setSimState("Playing");
            Playground.setPlayground("Motion");
        });

        Playground.playground.getChildren().addAll(background, idText, statsText, exitButton);
    }
    /**
     * Temp Creates a menu for the given cow.
     * @param cow The cow that the menu is to be created from
     */
    MenuCreation(@NotNull Cow cow) {
        clickedCow = cow;

        stack = new Pane();
        Rectangle background = new Rectangle(0,0, 110, 150);
        Text idText = new Text(5,15, cow.getId());

        ageText = new Text(5, 30, "Age: " + Integer.toString(clickedCow.getAge()));
        hungerText = new Text(5, 45, "Hunger: " + Integer.toString(clickedCow.getHunger()));
        happinessText = new Text(5, 60, "Happiness: " + Integer.toString(clickedCow.getHappiness()));

        idText.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        ageText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        hungerText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        happinessText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        background.setFill(Color.BLACK);
        idText.setFill(Color.WHITE);
        ageText.setFill(Color.RED);
        hungerText.setFill(Color.RED);
        happinessText.setFill(Color.RED);

        stack.getChildren().addAll(background, idText, hungerText, happinessText, ageText);
        Playground.playground.getChildren().add(stack);

        updateCowMenu();
    }

    /**
     * Updates the position of the open menu depending on the animal that has its menu opened's position.
     */
    public void updateCowMenu() {
        stack.relocate((clickedCow.getAnimatedX() + 55), (clickedCow.getAnimatedY() + 40));
        hungerText.setText("Hunger: " + Integer.toString(clickedCow.getHunger()));
        happinessText.setText("Happiness: " + Integer.toString(clickedCow.getHappiness()));
        ageText.setText("Age: " + Integer.toString(clickedCow.getAge()));
    }

    /**
     * @return The id of the cow who this menu belongs to
     */
    Cow getCowFromMenu() {
        return clickedCow;
    }
}