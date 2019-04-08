package cowParts.creation;

import cowParts.CowHandler;
import cowParts.cowThoughts.Traits;
import infrastructure.buildings.buildingTypes.GenericBuilding;
import cowParts.cowThoughts.Cognition;
import cowParts.cowAI.NaturalSelection;
import cowParts.cowThoughts.PersonalViews;
import cowParts.cowThoughts.Social;
import javafx.animation.Transition;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import menus.GenericMenu;
import metaControl.main.Input;
import metaEnvironment.Regioning.BinRegion;
import metaEnvironment.logging.EventLogger;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import menus.MenuCreation;
import menus.MenuHandler;
import societalProductivity.jobs.Occupation;
import userInterface.playgroundUI.StaticUI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles the creation and the AI integration for the animals. Extends ImageView to be able to be drawn to the screen
 * as a node.
 */
public class Cow extends ImageView {

    /* ImageView/General java properties */
    public Image skinSprite;
    private ColorAdjust color = new ColorAdjust();
    private Random random = new Random();

    /* Menu and UI elements */
    private Hyperlink cowLink;
    private GenericMenu cowMenu;
    private String currentBehavior = "";

    /* The mind/self of a cow */
    public Cognition self;
    public BirthEvent birth;
    public Social socialRelations;
    public PersonalViews views;
    public Traits personality;
    private Occupation job;

    /* Misc Control flags */
    private int numberOfVotes;
    private boolean voted = false;
    private int counter = 0;
    boolean parent = false;
    private boolean leader = false;

    /* Movement */
    public boolean alreadyMoving = false;
    public Transition animation;
    private Object destination;
    private boolean hidden = false;

    /* Tile Members*/
    private GenericBuilding livingSpace;
    private GenericBuilding buildingIn;
    private BinRegion regionIn;

    public Cow(Cow parent1, Cow parent2) {
        if (parent1 != null) {
            self = NaturalSelection.crossover(parent1, parent2);
            personality = Traits.crossover(parent1, parent2);
        }
        else {
            self = new Cognition();
            personality = new Traits();
        }

        birth = new BirthEvent();
        socialRelations = new Social();
        views = new PersonalViews();

    }

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
            self.setThirst(-1);
            self.setSleepiness(-1);
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
                if (random.nextInt(50) == 1)
                    Social.modifyRelationValue(this, otherCow, random.nextInt(5 + 1 + 5) + -5);
            }
            else {
                Social.newRelation(this, otherCow);
                self.setCompanionship(1);
                EventLogger.createLoggedEvent(this, "new relationship", 1, "companionship", 1);
            }
        }
    }

    /**
     * Removes the cow and any mention of the cow from the simulation by deleting the cow from the playground and the
     * cow's link from PlaygroundUIHandler. Logs the death event as a city-wide important event.
     */
    public void kill() {
        if (cowMenu != null) {
            MenuHandler.closeMenu(this.cowMenu);
            Input.selectedCows.remove(this);
        }

        if (animation != null)
            animation.stop();

        hidden = false;
        CowHandler.liveCowList.remove(this);
        regionIn.getPlayground().getChildren().remove(this);

        StaticUI.cowDeathEventUpdate(cowLink);

        EventLogger.createLoggedEvent(this, "death", 2, "N/A", 0);
    }

    /**
     * Closes the cows menu if applicable then stops the cow from being updated by removing it from the liveCowList and
     * playground node. This cow cannot be directly selected while its hidden value is true.
     */
    public void hide() {
        if (cowMenu != null)
            MenuHandler.closeMenu(this.cowMenu);

        hidden = true;
        regionIn.getPlayground().getChildren().remove(this);
        StaticUI.updateIdText();
    }

    /**
     * Allows the cow to be updated again by adding it back into the liveCowList and setting hidden to false.
     */
    public void show() {
        if (hidden) {
            hidden = false;

            if (!regionIn.getPlayground().getChildren().contains(this))
                regionIn.getPlayground().getChildren().add(this);
            else
                System.out.println("Duplicate???? " + this.getId());
        }
    }

    /**
     * @return If the cow is hidden or not
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Diseases the cow that called this method. Causes the cow to be thirsty.
     */
    public void disease() {
        self.setThirst(-100);
        color.setBrightness(-1.0);
    }

    /**
     * Opens or closes the cow's menu dependant on if the menu is already opened or not.
     */
    public void switchMenuState() {
        if (cowMenu != null)
            closeMenu();
        else
            openMenu();
    }

    /**
     * Calls for the opening of the the stats menu for this cow, if the menu is not already open.
     */
    public void openMenu() {
        if (cowMenu == null) {
            this.cowMenu = MenuCreation.createCowPopup(this);
            Input.addSelectedCow(this);
        }
    }

    /**
     * Calls for the closing of the stats menu for this cow, if the menu is already opened.
     */
    public void closeMenu() {
        if (cowMenu != null) {
            MenuHandler.closeMenu(this.cowMenu);
            Input.removeSelectedCow(this);
            cowMenu = null;
        }
    }

    /**
     * @return The action that the cow is currently doing.
     */
    public String getcurrentBehavior() {
        return currentBehavior;
    }

    public Occupation getJob() {
        return job;
    }

    public void setJob(Occupation newJob) {
        this.job = newJob;
    }

    public Social getSocialRelations() {
        return socialRelations;
    }

    public GenericBuilding getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(GenericBuilding livingSpace) {
        this.livingSpace = livingSpace;
    }

    public GenericBuilding getBuildingIn() {
        return buildingIn;
    }

    public void setBuildingIn(GenericBuilding buildingIn) {
        this.buildingIn = buildingIn;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public void setCowLink(Hyperlink cowCreationEvent) {
        cowLink = cowCreationEvent;
    }

    public boolean hasOffspring() {
        return parent;
    }

    public ArrayList<Cow> getOffspring() {
        return birth.getOffspring();
    }

    public Cow getSpouse() {
        return birth.getSpouse();
    }

    public Effect getColor() {
        return color;
    }

    public void setColor(ColorAdjust colorAdjust) {
        color = colorAdjust;
    }

    public void increaseVote() {
        numberOfVotes ++;
    }

    public int getVotes() {
        return numberOfVotes;
    }

    public void setVotes(int i) {
        numberOfVotes = i;
    }

    public boolean hasVoted() {
        return voted;
    }

    public void setHasVoted(boolean b) {
        voted = b;
    }

    public BinRegion getRegionIn() {
        return regionIn;
    }

    public void setRegionIn(BinRegion newRegion) {
        regionIn = newRegion;
    }

    public void setCurrentBehavior(String newBehavior) {
        this.currentBehavior = newBehavior;
    }

    public void rescale(double scaleX, double scaleY) {
        this.setScaleX(scaleX);
        this.setScaleY(scaleY);
    }

    public boolean isLeader() {
        return leader;
    }

    public void setIsLeader(boolean isLeader) {
        leader = isLeader;
    }
}