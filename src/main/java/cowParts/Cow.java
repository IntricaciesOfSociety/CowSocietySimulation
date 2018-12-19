package cowParts;

import buildings.Building;
import buildings.BuildingHandler;
import javafx.animation.TranslateTransition;
import metaControl.Time;
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
import societalProductivity.Role;
import terrain.Tile;
import userInterface.StaticUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals. Extends ImageView to be able to be drawn to the screen
 * as a node.
 */
public class Cow extends ImageView {

    //List that holds every created cow
    public static ArrayList<Cow> cowList = new ArrayList<>();

    public Cognition self = new Cognition();
    public BirthEvent birth = new BirthEvent();
    Social socialRelations = new Social();

    /* Control flags
    alreadyMoving: If an animation is to be ran or not (therefor if the cow is to be moved or not)
    delayTimer: The animation timer that is created to delay movement
    counter: A counter that updates based of the main loop ticks
    hidden: If the cow is to be updated from the cowList or not
     */
    public boolean alreadyMoving = false;
    public AnimationTimer delayTimer;
    private int counter = 0;
    private boolean hidden = false;
    public TranslateTransition animation;

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
    public String currentAction = "";
    public final EventLogger logger = new EventLogger();

    /* What makes a cow
    color: The color effects applied to the cow
    sprite: The image being displayed
     */
    private ColorAdjust color;
    private static Image sprite;

    //The job that this cow has
    private String job = "spinning";

    //TODO: Implement amount of work cow can do
    private int workForce = 10;
    boolean parent = false;

    //Statuses
    private boolean diseased = false;

    /* Tile Control variables
    livingSpace: Where the cow currently resides
    buildingTime: How long to stay in the next building
     */
    private Building livingSpace;
    private Building buildingIn;
    private long buildingTime = 500;
    private Object destination;

    //TODO: Implement
    private Tile tileOn;

    /**
     * Updates the time sensitive attributes in the cow based a counter that relates to the simState main loop. Counter
     * increases about 60 times a second.
     */
    public void updateVitals() {
        //Counter increase and reset
        counter++;
        if (counter % 1000 == 0)
            counter = 0;

        //Constantly updated vitals
        if (counter % 100 == 0) {
            self.setHunger(self.getThirst() - 1);
            self.setSleepiness(self.getSleepiness() - 2);
        }

        //If cow is in a building
        if (this.isHidden())
            updateBuildingVitals();
    }

    /**
     * Updates the necessary vitals while the cow is within a building such as building relationships faster and having
     * children.
     */
    private void updateBuildingVitals() {
        Cow otherCow;
        //Updated vitals specifically if cow is in a building
        for (int i = 0; i < buildingIn.getCurrentInhabitants().size(); i++) {
            otherCow = buildingIn.getCurrentInhabitants().get(i);

            if (Social.relationExists(this, otherCow)) {
                if (!this.parent && this.birth.isFertile() && otherCow.birth.isFertile())
                    BirthEvent.createChild(this, otherCow);

                if (random.nextInt(50) == 1)
                    Social.modifyRelationValue(this, otherCow, random.nextInt(5 + 1 -5) + -5);
            }
            else {
                Social.newRelation(this, otherCow);
                self.setCompanionship(self.getCompanionship() + 1);
                EventLogger.createLoggedEvent(this, "new relationship", 1, "companionship", 1);
            }
        }
    }

    /**
     * Removes the cow and any mention of the cow from the simulation by deleting the cow from the playground and the
     * cow's link from PlaygroundUI. Logs the death event as a city-wide important event.
     */
    public void kill() {
        if (menuIsOpened)
            MenuHandler.closeMenu(this.cowMenu);

        cowList.remove(this);
        StaticUI.cowDeathEventUpdate(cowLink);
        Playground.playground.getChildren().remove(this);

        EventLogger.createLoggedEvent(this, "death", 2, "N/A", 0);
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
    public void hide() {
        if (menuIsOpened)
            MenuHandler.closeMenu(this.cowMenu);

        hidden = true;
        Playground.playground.getChildren().remove(this);
    }

    /**
     * Allows the cow to be updated again by adding it back into the cowList and setting hidden to false.
     */
    public void show() {
        if (hidden) {
            hidden = false;
            Playground.playground.getChildren().add(this);
        }
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
        self.setHunger(0);
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
            cowToDisease.self.setHunger(0);
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

    public long getBuildingTime() {
        return buildingTime;
    }

    public void setBuildingTime(long buildingTime) {
        this.buildingTime = buildingTime;
    }

    public Building getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(Building livingSpace) {
        this.livingSpace = livingSpace;
    }

    public Building getBuildingIn() {
        return buildingIn;
    }

    public void setBuildingIn(Building buildingIn) {
        this.buildingIn = buildingIn;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    void setCowLink(Hyperlink cowCreationEvent) {
        cowLink = cowCreationEvent;
    }
}