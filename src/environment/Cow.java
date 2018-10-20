package environment;

import control.SimState;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import menus.GenericMenu;
import menus.MenuHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals
 */
public class Cow {

    //List that holds every created animal
    public static ArrayList<Cow> cowList = new ArrayList<>();

    //The id is also the cow's name
    private String id;

    //The menu of the cow
    public GenericMenu cowMenu;

    // The cow node and its image
    private ImageView body;
    private static Image sprite;

    private boolean menuIsOpened = false;

    /**
     * Calls createAnimal and adds it to the root node
     */
    public Cow() {
        createAnimal();
        SimState.playground.getChildren().addAll(body);
    }

    /**
     * TEMP
     * Draws a cow to the screen for testing purposes
     */
    private void createAnimal() {
        try { //res\\moo.png
            sprite = new Image(new FileInputStream("res/moo.png"));
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }

        body = new ImageView(sprite);
        body.setId("Big Beefy" + new Random().nextInt(100));
        body.relocate(400, 300);
        id = body.getId();

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
     * Executes when the animal is clicked, to check and see the animal is in a menu, or a no menu state.
     * if needed.
     */
    public void setClicked() {
        if (menuIsOpened)
            closeMenu();
        else
            openMenu();
    }

    /**
     * Calls for the opening of the the stats menu for this cow, if the menu is not already open.
     */
    public void openMenu() {
        if (!menuIsOpened) {
            this.cowMenu = MenuHandler.createMenu(this);
            menuIsOpened = true;
        }
    }

    /**
     * Calls for the closing of the stats menu for this cow, if the menu is opened.
     */
    public void closeMenu() {
        if (menuIsOpened) {
            MenuHandler.closeMenu(this.cowMenu);
            menuIsOpened = false;
        }
    }

    /**
     * If the menu for the current cow is opened.
     * @return True if the menu is opened, false if the menu is closed.
     */
    public boolean isMenuOpened() {
        return menuIsOpened;
    }

    /**
     * @return A string representation of the animal
     */
    public String toString() {
        return "MenuOpen: " + menuIsOpened + " :" + body.toString();
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