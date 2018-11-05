package environment;

import control.SimState;
import javafx.animation.AnimationTimer;
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

import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals. Extends ImageView to be able to be drawn to the screen
 * as a node.
 */
public class Cow extends ImageView {

    //List that holds every created cow
    public static ArrayList<Cow> cowList = new ArrayList<>();

    /* Control flags
    alreadyMoving: If an animation is to be ran or not (therefor if the cow is to be moved or not).
     */
    private boolean alreadyMoving = false;
    AnimationTimer delayTimer;

    //TEMP: Used for random movement and stats.
    private Random random = new Random();

    /* UI elements
    cowLink: The hyperlink that correlates to the cow
    cowMenu: The menu that correlates to the cow
     */
    private Hyperlink cowLink;
    public MenuCreation cowMenu;
    private boolean menuIsOpened = false;

    /* Logging elements
    currentAction: What the cow is currently doing within the sim
    logger: The instance of EventLogger that holds this cow's unique log
     */
    private String currentAction = "";
    final EventLogger logger = new EventLogger();

    /* What makes a cow
    color: The color effects applied to the cow
    sprite: The image being displayed
     */
    private ColorAdjust color = new ColorAdjust();
    private static Image sprite;

    //Emotions: 0 is low 100 is high
    private int anger = random.nextInt(100);
    private int anticipation = random.nextInt(100);
    private int disgust = random.nextInt(100);
    private int fear = random.nextInt(100);
    private int happiness = random.nextInt(100);
    private int surprise = random.nextInt(100);
    private int trust = random.nextInt(100);

    //Finances 0 is low 100 is high
    private int income = random.nextInt(100);
    private int bills = random.nextInt(100);
    private int food = random.nextInt(100);
    private int taxes = random.nextInt(100);
    private int savings = random.nextInt(100);
    private int debt = random.nextInt(100);

    //Social 0 is low 100 is high
    private int boredom = random.nextInt(100);
    private int companionship = random.nextInt(100);

    //Physical 0 is low 100 is high
    private int hunger = random.nextInt(100);
    private int age = random.nextInt(100);
    private int physicalHealth = random.nextInt(100);

    //Mental 0 is low 100 is high
    private int faith = random.nextInt(100);
    private int mentalHealth = random.nextInt(100);

    //Academic 0 is low 100 is high
    private int intelligence = random.nextInt(100);

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
        EventLogger.createLoggedEvent(this, "creation", 2);

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

        if (!alreadyMoving) {
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

                    alreadyMoving = true;

                    double distanceX = Food.getX() - this.getLayoutX();
                    double distanceY = Food.getY() - this.getLayoutY();
                    double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                    final TranslateTransition transition = new TranslateTransition(new Duration((distanceTotal / 10) * 100), this);

                    transition.setOnFinished(event -> openAnimation(500L));

                    transition.setToX(Food.getX() - this.getLayoutX());
                    transition.setToY(Food.getY() - this.getLayoutY());
                    transition.play();

                    setHunger(100);

                    EventLogger.createLoggedEvent(this, "Getting food", 0);

                    break;

                case "Random":
                    currentAction = "Spinning";
                    this.setRotate(random.nextInt(360 + 1 + 360) - 360);
                    this.setLayoutX(this.getLayoutX() + Math.cos(Math.toRadians(this.getRotate())) * randomNumber);
                    this.setLayoutY(this.getLayoutY() + Math.sin(Math.toRadians(this.getRotate())) * randomNumber);
                    break;
            }
        }

    }

    /**
     * 'Pauses' the movement of the current cow by setting an animation for however long was given.
     * @param milliDuration The duration that the 'empty' animation lasts.
     */
    private void pauseMovement(long milliDuration) {
        Date timeStartedDelay = SimState.getTime();

        AnimationTimer delayTimer = new AnimationTimer() {
            private long lastUpdate = milliDuration;

            @Override
            public void handle(long frameTime) {
                //TODO: Pause correct amount.
            }
        };
        delayTimer.start();
    }

    private void stopDelayLoop() {
        delayTimer.stop();
        alreadyMoving = false;
    }

    /**
     * Sets the alreadyMoving flag to allow for the playing of an animation to happen. Stops animations from being
     * constantly called. Passes through an animation allowance delay to pauseMovement.
     */
    private void openAnimation(long milliDuration) {
        pauseMovement(milliDuration);
        alreadyMoving = false;
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
     * @return If the cow is diseased or not.
     */
    public boolean getDiseased() {
        return diseased;
    }

    public int getAnger() {
        return anger;
    }

    public void setAnger(int anger) {
        this.anger = anger;
    }

    public int getAnticipation() {
        return anticipation;
    }

    public void setAnticipation(int anticipation) {
        this.anticipation = anticipation;
    }

    public int getDisgust() {
        return disgust;
    }

    public void setDisgust(int disgust) {
        this.disgust = disgust;
    }

    public int getFear() {
        return fear;
    }

    public void setFear(int fear) {
        this.fear = fear;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public int getSurprise() {
        return surprise;
    }

    public void setSurprise(int surprise) {
        this.surprise = surprise;
    }

    public int getTrust() {
        return trust;
    }

    public void setTrust(int trust) {
        this.trust = trust;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getBills() {
        return bills;
    }

    public void setBills(int bills) {
        this.bills = bills;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getTaxes() {
        return taxes;
    }

    public void setTaxes(int taxes) {
        this.taxes = taxes;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getBoredom() {
        return boredom;
    }

    public void setBoredom(int boredom) {
        this.boredom = boredom;
    }

    public int getCompanionship() {
        return companionship;
    }

    public void setCompanionship(int companionship) {
        this.companionship = companionship;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhysicalHealth() {
        return physicalHealth;
    }

    public void setPhysicalHealth(int physicalHealth) {
        this.physicalHealth = physicalHealth;
    }

    public int getFaith() {
        return faith;
    }

    public void setFaith(int faith) {
        this.faith = faith;
    }

    public int getMentalHealth() {
        return mentalHealth;
    }

    public void setMentalHealth(int mentalHealth) {
        this.mentalHealth = mentalHealth;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
}