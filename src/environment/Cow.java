package environment;

import javafx.scene.control.Hyperlink;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuCreation;
import menus.MenuHandler;
import menus.PlaygroundUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals
 */
public class Cow {

    //List that holds every created cow
    public static ArrayList<Cow> cowList = new ArrayList<>();

    //The sprite used for every cow
    private static Image sprite;

    //TEMP: Used for random movement and stats.
    private Random random = new Random();

    //The unique menu for the cow
    public MenuCreation cowMenu;
    private boolean menuIsOpened = false;

    //Link for PlaygroundUI link list
    private Hyperlink cowLink;

    /* What makes a cow
    body: The actual object being displayed to the playground
    color: The color effects applied to the cow
    id: The unique id and also the name of the cow
     */
    private ImageView body;
    private ColorAdjust color = new ColorAdjust();
    private String id;

    //Emotions: 0 is low 100 is high
    private int hunger = random.nextInt(100);
    private int happiness = random.nextInt(100);
    private int age = random.nextInt(1 + 1 + 100);

    //Statuses
    private boolean diseased = false;

    /**
     * Calls createAnimal and adds the resulting cow body to the root node
     */
    public Cow() {
        createAnimal();
        Playground.playground.getChildren().add(body);
    }

    /**
     * TEMP
     * Draws a cow to the screen for testing purposes. Moves the cow to a random location then creates and saves a link
     * for the cow to be used in PlaygroundUI.
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
        body.setEffect(color);

        id = body.getId();

        cowLink = PlaygroundUI.cowCreationEvent(id);
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
            case "toFood":
                if (Food.getX() > body.getLayoutX())
                    body.setLayoutX(body.getLayoutX() + 1);
                else
                    body.setLayoutX(body.getLayoutX() - 1);

                if (Food.getY() > body.getLayoutY())
                    body.setLayoutY(body.getLayoutY() + 1);
                else
                    body.setLayoutY(body.getLayoutY() - 1);
                break;
            case "Random":
                body.setRotate(random.nextInt(360 + 1 + 360) - 360);
                body.setLayoutX(body.getLayoutX() + Math.cos(Math.toRadians(body.getRotate())) * randomNumber);
                body.setLayoutY(body.getLayoutY() + Math.sin(Math.toRadians(body.getRotate())) * randomNumber);
                break;
        }
    }

    /**
     * Removes the cow and any mention of the cow from the simulation by deleting the cow from the playground and the
     * cow's link from PlaygroundUI.
     */
    public void kill() {
        closeMenu();
        cowList.remove(this);
        PlaygroundUI.cowDeathEventUpdate(cowLink);
        Playground.playground.getChildren().remove(body);
    }

    /**
     * Kills a whole list of cows
     * @param killList The list of cows to kill
     */
    public static void killAll(@NotNull ArrayList<Cow> killList) {
        for (Cow cowToKill : killList) {
            cowToKill.kill();
        }
    }

    /**
     * Diseases the cow that called this method.
     */
    public void disease() {
        this.diseased = true;
        this.color.setBrightness(-1.0);
    }

    /**
     * Diseases all cows from the list given.
     * @param diseaseList The list of cows to disease
     */
    public static void diseaseAll(@NotNull ArrayList<Cow> diseaseList) {
        for (Cow cowToDisease : diseaseList) {
            cowToDisease.diseased = true;
            cowToDisease.color.setBrightness(-1.0);
        }
    }

    /**
     * Searches for the cow matching the given id and returns the match (null if there was no match).
     * @param givenId The id of the cow that is being searched for
     * @return The cow with id matching givenId if a cow is found. Else null
     */
    @Nullable
    public static Cow findCow(String givenId) {
        for (Cow aCowList : cowList) {
            if (aCowList.getId().equals(givenId))
                return aCowList;
        }
        return null;
    }

    /**
     * Searches the cow list for multiple cows with the matching ids.
     * @param givenId The cow's ids to search for separated by commas and enclosed in brackets.
     * @return The found cows with matching ids.
     */
    public static ArrayList<Cow> findCows(@NotNull String givenId) {
        givenId = givenId.substring(givenId.indexOf('[') + 5, givenId.indexOf(']'));
        ArrayList<Cow> cowList = new ArrayList<>();
        for (String cowFromString : givenId.split(", ")) {
            cowList.add(findCow(cowFromString));
        }
        return cowList;
    }

    /**
     * Opens or closes the cow's menu dependant on if the menu is already opened or not.
     */
    public void switchMenuState() {
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
     * Calls for the closing of the stats menu for this cow, if the menu is already opened.
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

    /**
     * @return If the cow is diseased or not.
     */
    public boolean getDiseased() {
        return diseased;
    }
}