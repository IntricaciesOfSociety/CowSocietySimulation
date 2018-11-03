package environment;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
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
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals. Extends ImageView to be able to be drawn to the screen
 * as a node.
 */
public class Cow extends ImageView {

    //List that holds every created cow
    public static ArrayList<Cow> cowList = new ArrayList<>();

    //If an animation is to be ran or not
    private boolean notAlreadyMoving = true;

    //TEMP: Used for random movement and stats.
    private Random random = new Random();

    /* UI elements
    cowLink: The hyperlink that correlates to the cow
    cowMenu: The menu that correlates to the cow
     */
    private Hyperlink cowLink;
    public MenuCreation cowMenu;
    private boolean menuIsOpened = false;

    private String currentAction = "";

    /* What makes a cow
    color: The color effects applied to the cow
    sprite: The image being displayed
     */
    private ColorAdjust color = new ColorAdjust();
    private static Image sprite;

    //Emotions: 0 is low 100 is high
    private int hunger = random.nextInt(100);
    private int happiness = random.nextInt(100);
    private int age = random.nextInt(1 + 1 + 100);

    //Statuses
    private boolean diseased = false;

    /**
     * Calls createAnimal and adds the resulting cow body to the playground node
     */
    public Cow() {
        createAnimal();
        Playground.playground.getChildren().add(this);
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
        this.setImage(sprite);
        this.setId("Big Beefy" + new Random().nextInt(100));
        this.relocate(random.nextInt(1000), random.nextInt(1000));
        this.setEffect(color);

        cowLink = PlaygroundUI.cowCreationEvent(this.getId());
        addListeners();
    }

    /**
     * Creates and applies the various listeners that the cows need to respond to.
     */
    private void addListeners() {
        MenuHandler.allCowMenusOpen.addListener(allMenusOpen -> {
            if (((BooleanProperty) allMenusOpen).getValue())
                openMenu();
            else
                closeMenu();
        });
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
                this.setRotate(270);
                this.setLayoutY(this.getLayoutY() + Math.sin(Math.toRadians(this.getRotate())) * randomNumber);
                break;
            case "East":
                this.setLayoutX(this.getLayoutX() - Math.cos(Math.toRadians(this.getRotate())) * randomNumber);
                break;
            case "South":
                this.setRotate(90);
                this.setLayoutY(this.getLayoutY() - Math.sin(Math.toRadians(this.getRotate())) * randomNumber);
                break;
            case "West":
                this.setRotate(180);
                this.setLayoutX(this.getLayoutX() - Math.cos(Math.toRadians(this.getRotate())) * randomNumber);
                break;

            /*TODO: Switch to timeline implementation?
            Creates an animation to move the cow to the food
             */
            case "toFood":
                currentAction = "Getting Food";
                this.setRotate(random.nextInt(360 + 1 + 360) - 360);
                if (notAlreadyMoving) {
                    notAlreadyMoving = false;

                    double distanceX = Food.getX() - this.getLayoutX();
                    double distanceY = Food.getY() - this.getLayoutY();
                    double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                    final TranslateTransition transition = new TranslateTransition(new Duration((distanceTotal / 10) * 100), this);

                    transition.setOnFinished(event -> openAnimation());

                    transition.setToX(Food.getX() - this.getLayoutX());
                    transition.setToY(Food.getY() - this.getLayoutY());
                    transition.play();
                }
                break;

            case "Random":
                currentAction = "Spinning";
                this.setRotate(random.nextInt(360 + 1 + 360) - 360);
                this.setLayoutX(this.getLayoutX() + Math.cos(Math.toRadians(this.getRotate())) * randomNumber);
                this.setLayoutY(this.getLayoutY() + Math.sin(Math.toRadians(this.getRotate())) * randomNumber);
                break;
        }
    }

    /**
     * Allows the playing of an animation to happen. Stops the animation start from being constantly called.
     */
    private void openAnimation() {
        notAlreadyMoving = true;
    }

    /**
     * Removes the cow and any mention of the cow from the simulation by deleting the cow from the playground and the
     * cow's link from PlaygroundUI.
     */
    public void kill() {
        closeMenu();
        cowList.remove(this);
        PlaygroundUI.cowDeathEventUpdate(cowLink);
        Playground.playground.getChildren().remove(this);
    }

    /**
     * Kills a whole list of cows
     * @param killList The list of cows to kill
     */
    public static void killAll(@NotNull ArrayList<Cow> killList) {
        for (Cow cowToKill : killList)
            cowToKill.kill();
    }

    /**
     * Diseases the cow that called this method. Causes the cow to be hungry.
     */
    public void disease() {
        diseased = true;
        setHunger(0);
        color.setBrightness(-1.0);
    }

    /**
     * Diseases all cows from the list given.
     * @param diseaseList The list of cows to disease
     */
    public static void diseaseAll(@NotNull ArrayList<Cow> diseaseList) {
        for (Cow cowToDisease : diseaseList) {
            cowToDisease.diseased = true;
            cowToDisease.color.setBrightness(-1.0);
            cowToDisease.setHunger(0);
        }
    }

    /**
     * Searches for the cow matching the given id and returns the match (null if there was no match).
     * @param givenId The id of the cow that is being searched for
     * @return The cow with id matching givenId if a cow is found. Else null
     */
    @Nullable
    public static Cow findCow(String givenId) {
        for (Cow aCowList : cowList)
            if (aCowList.getId().equals(givenId))
                return aCowList;
        return null;
    }

    public void checkForCollisions() {
        checkForCowCollision();
    }

    private void checkForCowCollision() {
        boolean collision = false;
        for (int i = 0; i < cowList.size(); i++) {
            if (this.getBoundsInParent().intersects(cowList.get(i).getBoundsInParent()) && cowList.get(i) != this) {
                collision = true;
                cowList.get(i).color.setContrast(-1);
            }
        }
        if (collision)
            this.color.setContrast(-1);
        else
            this.color.setContrast(0);
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
    private void closeMenu() {
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
     * @return The action that the cow is currently doing.
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @return The X layout coordinate of the cow plus the X translate coordinate of the cow.
     */
    public double getAnimatedX() {
        return this.getLayoutX() + this.getTranslateX();
    }

    /**
     * @return The Y layout coordinate of the cow plus the Y translate coordinate of the cow.
     */
    public double getAnimatedY() {
        return this.getLayoutY() + this.getTranslateY();
    }

    /**
     * @return The hunger value of the cow.
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * @return The happiness value of the cow.
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets the hunger value of the cow.
     * @param newHunger the new hunger that the cow is being set to
     */
    public void setHunger(int newHunger) {
        hunger = newHunger;
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