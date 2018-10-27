package environment;

import control.Input;
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
public class Cow {

    //TEMP: Used for random movement and stats.
    private Random random = new Random();

    //List that holds every created animal
    public static ArrayList<Cow> cowList = new ArrayList<>();

    //The id is also the cow's name
    private String id;

    //The menu of the cow
    public GenericMenu cowMenu;

    // The cow node and its image
    private ImageView body;
    private static Image sprite;

    //Emotions: 0 is low 100 is high
    private int hunger = random.nextInt(100);
    private int happiness = random.nextInt(100);
    private int age = random.nextInt(1 + 1 + 100);

    private boolean menuIsOpened = false;

    /**
     * Calls createAnimal and adds the resulting cow body to the root node
     */
    public Cow() {
        createAnimal();
        Playground.playground.getChildren().addAll(body);
    }

    /**
     * TEMP
     * Draws a cow to the screen for testing purposes
     */
    private void createAnimal() {
        try { //res\\moo.png <--- correct path
            sprite = new Image(new FileInputStream("res/moo.png"));
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }

        body = new ImageView(sprite);
        body.setId("Big Beefy" + new Random().nextInt(100));
        body.relocate(random.nextInt(800), random.nextInt(600));
        id = body.getId();
    }

    /**
     * TEMP
     * Moves the cow in a specified direction. Used for testing while AI is not implemented.
     * @param movementType The movement that the cow will be performing
     */
    public void step(String movementType) {

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
     * Searches for the cow matching the given id.
     * @param givenId The id of the cow that is being searched for
     * @return The cow with id matching givenId if a cow is found. Else null
     */
    public static Cow findCow(String givenId) {
        for (Cow aCowList : cowList) {
            if (aCowList.getId().equals(givenId))
                return aCowList;
        }
        return null;
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
     * @return A string representation of the cow
     */
    public String toString() {
        return "MenuOpen: " + menuIsOpened + " :" + body.toString();
    }

    /**
     * @return The X coordinate of the cow
     */
    public double getX() {
        return body.getLayoutX();
    }

    /**
     * @return The Y coordinate of the cow
     */
    public double getY() {
        return body.getLayoutY();
    }

    /**
     * @return The cow's unique id
     */
    public String getId() {
        return id;
    }

    /**
     * @return The hunger value of the cow
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Sets the hunger value of the cow
     * @param newHunger the new hunger that the cow is being set to
     */
    public void setHunger(int newHunger) {
        hunger = newHunger;
    }

    /**
     * @return The happiness value of the cow.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets the happiness value of the cow.
     * @param newHappiness the happiness that the cow is being set to
     */
    public void setHappiness(int newHappiness) {
        happiness = newHappiness;
    }

    /**
     * @return The age value of the cow.
     */
    public int getAge() {
        return age;
    }
}