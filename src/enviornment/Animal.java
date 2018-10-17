package enviornment;

import control.SimState;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.GenericMenu;
import menus.MenuHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals
 */
public class Animal {

    //List that holds every created animal
    public static ArrayList<Animal> animalList = new ArrayList<>();

    private boolean clickedFlag = false;

    //The id is also the cow's name
    private String id;

    public GenericMenu animalMenu;

    // The cow node and its image
    private ImageView body;
    private static Image sprite;

    /**
     * Calls createAnimal and adds it to the root node
     */
    public Animal() {
        createAnimal();
        SimState.playground.getChildren().addAll(body);
    }

    /**
     * TEMP
     * Draws a 'animal' to the screen for testing purposes
     */
    private void createAnimal() {
            try {
                sprite = new Image(new FileInputStream("/Users/10200126/IdeaProjects/ZackPrototype01/res/moo.png"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            body = new ImageView(sprite);
            body.setId("Big Beefy");
            body.relocate(400, 300);
            id = body.getId();
    }

    /**
     * Executes when the animal is clicked, to check and see the animal is in a menu, or a no menu state. Creates the menu
     * if needed.
     */
    public void isClicked() {
        if(getClicked()) {
            setClicked(false);
            MenuHandler.closeMenu(animalMenu);
        }
        else {
            setClicked(true);
            animalMenu = new GenericMenu(this);
        }
    }

    /**
     * TEMP
     * Moves the cow in a specified direction. Used for testing while AI is not implemented.
     * @param movementType The movement that the cow will be performing
     */
    public void step(String movementType) {
        Random random = new Random();
        int randomNumber = random.nextInt(1 + 1 + 5) - 5;

        switch (movementType) {
            case "North":
                body.setRotate(270);
                body.setLayoutY(body.getLayoutY() + Math.sin(Math.toRadians(body.getRotate())) * randomNumber);
                break;
            case "East":
                body.setLayoutX(body.getLayoutX() - Math.cos(Math.toRadians(body.getRotate())) * randomNumber);
                break;
            case "South":
                body.setRotate(90);
                body.setLayoutY(body.getLayoutY() - Math.sin(Math.toRadians(body.getRotate())) * randomNumber);
                break;
            case "West":
                body.setRotate(180);
                body.setLayoutX(body.getLayoutX() - Math.cos(Math.toRadians(body.getRotate())) * randomNumber);
                break;
            case "Random":
                body.setRotate(random.nextInt(360 + 1 + 360) - 360);
                body.setLayoutX(body.getLayoutX() + Math.cos(Math.toRadians(body.getRotate())) * randomNumber);
                body.setLayoutY(body.getLayoutY() + Math.sin(Math.toRadians(body.getRotate())) * randomNumber);
                break;
        }
    }

    /**
     * Sets the state of the animal. If clicked there should be a menu open, if not then there shouldn't be
     * @param clicked If the animal is clicked
     */
    private void setClicked(boolean clicked) {
        clickedFlag = clicked;
    }

    /**
     * @return The clicked state of the animal
     */
    public boolean getClicked() {
        return clickedFlag;
    }

    /**
     * @return A string representation of the animal
     */
    public String toString() {
        return "Clicked: " + clickedFlag + " " + body.toString();
    }

    /**
     * @return The X coordinate of the animal
     */
    public double getX() {
        return body.getLayoutX();
    }

    /**
     * @return The Y coordinate of the animal
     */
    public double getY() {
        return body.getLayoutY();
    }

    /**
     * @return The animal's unique id
     */
    public String getId() {
        return id;
    }
}
