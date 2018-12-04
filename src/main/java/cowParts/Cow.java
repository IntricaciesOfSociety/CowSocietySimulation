package cowParts;

import buildings.Building;
import buildings.BuildingHandler;
import metaEnvironment.EventLogger;
import metaEnvironment.Playground;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuCreation;
import menus.MenuHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import userInterface.StaticUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals. Extends ImageView to be able to be drawn to the screen
 * as a node.
 */
public class Cow extends ImageView {

    //List that holds every created cow
    public static ArrayList<Cow> cowList = new ArrayList<>();

    //The list that holds every hidden cow
    public static ArrayList<Cow> hiddenCows = new ArrayList<>();

    Social socialRelations = new Social();

    /* Control flags
    alreadyMoving: If an animation is to be ran or not (therefor if the cow is to be moved or not)
    delayTimer: The animation timer that is created to delay movement
    counter: A counter that updates based of the main loop ticks
    hidden: If the cow is to be updated from the cowList or not
     */
    boolean alreadyMoving = false;
    AnimationTimer delayTimer;
    private int counter = 0;
    private boolean hidden = false;

    //TEMP: Used for random movement and stats.
    private Random random = new Random();

    /* UI elements
    cowLink: The hyperlink that correlates to the cow
    cowMenu: The menu that correlates to the cow
    menuIsOpened: If the menu is opened or not
     */
    private Hyperlink cowLink;
    private MenuCreation cowMenu;
    private boolean menuIsOpened = false;

    /* Logging elements
    currentAction: What the cow is currently doing within the sim
    logger: The instance of EventLogger that holds this cow's unique log
     */
    String currentAction = "";
    public final EventLogger logger = new EventLogger();

    /* What makes a cow
    color: The color effects applied to the cow
    sprite: The image being displayed
     */
    private ColorAdjust color = new ColorAdjust();
    private static Image sprite;

    //The job that this cow has
    private String job = "Spinning";
    boolean parent = false;

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

    //Where the cow currently resides
    private Building livingSpace;

    /**
     * Calls createCow and adds the resulting cow body to the playground node
     */
    public Cow() {
        createCow();
        Playground.playground.getChildren().add(this);
    }

    /**
     * TEMP
     * Draws a cow to the screen for testing purposes. Moves the cow to a random location then creates and saves a link
     * for the cow to be used in PlaygroundUI.
     */
    private void createCow() {
        try {
             // creates a URL for a random cow from the resources
            ArrayList<String> cowFiles= new ArrayList<String>();
            Collections.addAll(cowFiles, "BlackRedGradientCow.png","BlueCheeseCow.png", "BrownBull.png","BlueCheeseCow.png","BrownCow.png","ChickenMaskCow.png","ZackCow.png","ThanosCow.png");// adds several cow names from the file to cowFiles

            int randCow = (int)(Math.random() * cowFiles.size());
            String cowURL = ("src/main/resources/Cows/" + cowFiles.get(randCow)); // picks a random cow name from the cowName list and poperly concatenates it to cowURl

            sprite = new Image(new FileInputStream(cowURL),0, 0, true, false); // creates a sprite, using cowURL as the cow texture to use
        }
        catch (FileNotFoundException error) {
            error.printStackTrace();
        }

        ArrayList<String> cowIDs= new ArrayList<String>();
        Collections.addAll(cowIDs, "Jeremy","Bob", "Hubert", "Cassandra", "Sandy", "Bartholomew", "Sarah");

        this.setImage(sprite);
        this.setId(cowIDs.get(new Random().nextInt(cowIDs.size() - 1)) + new Random().nextInt(100));
        this.relocate(random.nextInt( (int) Playground.playground.getPrefWidth()), random.nextInt( (int) Playground.playground.getPrefHeight()));
        this.setEffect(color);
        this.setScaleX(3);
        this.setScaleY(3);
        this.setSmooth(false);

        setLivingSpace(BuildingHandler.getBuildingAssignment(this.getId()));

        cowLink = StaticUI.cowCreationEvent(this.getId());
        EventLogger.createLoggedEvent(this, "creation", 2, "age", 0);
    }

    /**
     * Updates the time sensitive attributes in the cow based a counter that relates to the simState main loop. Counter
     * increases about 60 times a second.
     */
    void updateVitals() {
        counter++;
        if (counter % 1000 == 0)
            counter = 0;

        if (counter % 80 == 0)
            setHunger(getHunger() - 1);
    }

    /**
     * Removes the cow and any mention of the cow from the simulation by deleting the cow from the playground and the
     * cow's link from PlaygroundUI.
     */
    public void kill() {
        if (menuIsOpened)
            MenuHandler.closeMenu(this.cowMenu);

        cowList.remove(this);
        StaticUI.cowDeathEventUpdate(cowLink);
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
     * Closes the cows menu if applicable then stops the cow from being updated by removing it from the cowList and
     * playground node. This cow cannot be directly selected while its hidden value is true.
     */
    void hide() {
        if (menuIsOpened)
            MenuHandler.closeMenu(this.cowMenu);

        hidden = true;
        cowList.remove(this);
        hiddenCows.add(this);
        Playground.playground.getChildren().remove(this);
    }

    /**
     * Allows the cow to be updated again by adding it back into the cowList and setting hidden to false.
     */
    public void show() {
        hidden = false;
        cowList.add(this);
        Playground.playground.getChildren().add(this);
        hiddenCows.remove(this);
    }

    /**
     * @return If the cow is hidden or not
     */
    public boolean isHidden() {
        return hidden;
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
            this.cowMenu = MenuHandler.createPopupMenu(this);
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

    void setCompanionship(int companionship) {
        this.companionship = companionship;
    }

    public int getHunger() {
        return hunger;
    }

    void setHunger(int hunger) {
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

    public EventLogger getLogger() {
        return logger;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    Social getSocialRelations() {
        return socialRelations;
    }

    /**
     * @return The sum of the emotions as a string over 700, as a string.
     */
    public String getEmotionAggregate() {
        return Integer.toString((anger + anticipation + disgust + fear + happiness + surprise + trust)) + "/700";
    }

    /**
     * @return The sum of finances as a string over 600, as a string.
     */
    public String getFinanceAggregate() {
        return Integer.toString((income + bills + food + taxes + savings + debt)) + "/600";
    }

    /**
     * @return The sum of socials as a string over 200, as a string.
     */
    public String getSocialAggregate() {
        return Integer.toString((boredom + companionship)) + "/200";
    }

    /**
     * @return The sum of physicals as a string over 300, as a string.
     */
    public String getPhysicalAggregate() {
        return Integer.toString((hunger + age + physicalHealth)) + "/300";
    }

    /**
     * @return The sum of mentals as a string over 200, as a string.
     */
    public String getMentalAggregate() {
        return Integer.toString((faith + mentalHealth)) + "/200";
    }

    /**
     * @return The sum of the emotions as a string over 100, as a string.
     */
    public String getAcademicAggregate() {
        return Integer.toString((intelligence)) + "/100";
    }

    public Building getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(Building livingSpace) {
        this.livingSpace = livingSpace;
    }
}