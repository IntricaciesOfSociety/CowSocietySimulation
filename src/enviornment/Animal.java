package enviornment;

import control.SimState;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menus.MenuHandler;

import java.util.ArrayList;

/**
 * Handles the creation and the AI integration for the animals
 */
public class Animal {

    //List that holds every created animal
    public static ArrayList<Animal> animalList = new ArrayList<>();

    private boolean clickedFlag = false;
    private String id; //Also its name
    private Rectangle body;

    /**
     * Calls createAnimal and adds it to the root node
     */
    public Animal() {
        createAnimal();
        SimState.root.getChildren().addAll(body);
    }

    /**
     * TEMP
     * Draws a 'animal' to the screen for testing purposes
     */
    public void createAnimal() {
            body = new Rectangle(50,50, 50, 50);
            body.setFill(Color.BLUE);
            body.setId("Big Beefy");
            id = body.getId();
    }

    /**
     * Executes when the animal is clicked, to check and see the animal is in a menu, or a no menu state. Creates the menu
     * if needed.
     */
    public void isClicked() {
        if(getClicked()) {
            setClicked(false);
            MenuHandler.closeMenu(id);
        }
        else {
            setClicked(true);
            SimState.setCurrentMenu(this);
        }
    }

    /**
     * Sets the state of the animal, if clicked there should be a menu open, if not then there shouldn't be
     * @param clicked If the animal is clicked
     */
    private void setClicked(boolean clicked) {
        clickedFlag = clicked;
    }

    /**
     * @return The clicked state of the animal
     */
    private boolean getClicked() {
        return clickedFlag;
    }

    /**
     * @return A string representation of the animal
     */
    public String toString() {
        return "Clicked: " + clickedFlag + " " + body.toString();
    }

    /**
     * @return The animal's unique id
     */
    public String getId() {
        return id;
    }
}
