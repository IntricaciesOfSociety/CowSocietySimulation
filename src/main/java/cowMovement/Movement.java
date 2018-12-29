package cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import buildings.SmallDwelling;
import cowParts.Cow;
import cowParts.CowHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.EventLogger;
import resourcesManagement.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import societalProductivity.Economy;
import societalProductivity.Role;
import terrain.Tile;
import userInterface.StaticUI;
import java.util.Random;

/**
 * Handles all the movement decision and execution for all cows. Includes the handling of all animations.
 */
public class Movement extends Cow {

    private static Random random = new Random();

    static Tile tileStandingOn;

    /**
     * Finds the distance as one number between the given cow and the given object
     * @param cowToCheck The cow to find distance to
     * @param objectToCheck The object to find distance to
     * @return The distance between the given cow and object as a single number
     */
    public static double findDistanceBetweenCowAndObject(@NotNull Cow cowToCheck, @NotNull ImageView objectToCheck) {
        double distanceX =  objectToCheck.getLayoutX() - cowToCheck.getAnimatedX();
        double distanceY = objectToCheck.getLayoutY() - cowToCheck.getAnimatedY();
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * TODO: Put into the AI
     * Moves the cow in a specified direction. Used for testing while AI is not implemented.
     * @param movementType The movement that the cow will be performing
     */
    private static void step(@NotNull String movementType, @NotNull Cow cowToMove) {
        cowToMove.alreadyMoving = true;
        ImageView destination;

        switch (movementType) {

            /*TODO: Switch to timeline implementation? Animation has to be stopped.
            Creates an animation to move the cow to the closest waterSource
             */
            case "toWaterSource":
                destination = WaterSource.getClosestResource(cowToMove);
                cowToMove.currentAction = "Going to WaterSource";
                cowToMove.setDestination(destination);

                animateTowardsDestination(cowToMove, destination);
                cowToMove.animation.setOnFinished(event -> {
                    cowToMove.self.setThirst(100);
                    EventLogger.createLoggedEvent(cowToMove, "Getting water", 0, "thirst", 100);

                    openAnimation(100, cowToMove);
                });
                break;

            /*
            Creates an animation to move the cow to their home
             */
            case "toHome":
                destination = cowToMove.getLivingSpace();
                cowToMove.currentAction = "Going home";
                cowToMove.setDestination(destination);

                animateTowardsDestination(cowToMove, destination);
                cowToMove.animation.setOnFinished(event -> {
                    EventLogger.createLoggedEvent(cowToMove, "Going home", 0, "sleepiness", 100 - cowToMove.self.getSleepiness());
                    cowToMove.self.setSleepiness(100);
                    openAnimation(1, cowToMove);
                });
                break;

            /*
            Creates an animation to move the next building that needs constructing
            */
            case "woodworking":
                destination = BuildingHandler.nextHouseToConstruct();
                cowToMove.currentAction = "Wood Constructing";

                if (destination != null) {
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, destination);
                    cowToMove.animation.setOnFinished(event -> {
                        if (!((Building) cowToMove.getDestination()).isConstructed()) {
                            ((Building) cowToMove.getDestination()).contributeResource("wood", 5);
                            ((Building) cowToMove.getDestination()).contributeResource("power", 1);

                            cowToMove.self.setSleepiness(-5);
                            Economy.giveMoney(cowToMove, 10);
                            EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                            openAnimation(1000, cowToMove);
                        }
                        //TODO: Redirect cow to new destination before the end of the current animation
                        else {
                            cowToMove.setDestination(null);
                            openAnimation(1, cowToMove);
                        }
                    });
                }
                else
                    new Role(cowToMove);
                break;

            /*
            Creates an animation to move the cow to the closest tree
             */
            case "choppingWood":
                destination = WoodSource.getClosestResource(cowToMove);
                cowToMove.currentAction = "Chopping Wood";
                cowToMove.setDestination(destination);

                animateTowardsDestination(cowToMove, destination);
                cowToMove.animation.setOnFinished(event -> {
                    if (!((WoodSource) cowToMove.getDestination()).isDestroyed()) {
                        ResourcesHandler.modifyResource("wood", 5);
                        Resource.depleteResource((WoodSource) cowToMove.getDestination(), 5);

                        cowToMove.self.setSleepiness(-5);
                        Economy.giveMoney(cowToMove, 10);
                        EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                        openAnimation(500, cowToMove);
                    }
                    //TODO: Redirect cow to new destination before the end of the current animation
                    else {
                        cowToMove.setDestination(null);
                        openAnimation(1, cowToMove);
                    }
                });
                break;

            /*
            Creates an animation to move the cow to the closest rock
            */
            case "miningRock":
                destination = RockSource.getClosestResource(cowToMove);
                cowToMove.currentAction = "Mining Rock";
                cowToMove.setDestination(destination);

                animateTowardsDestination(cowToMove, destination);
                cowToMove.animation.setOnFinished(event -> {
                    if (!((RockSource) cowToMove.getDestination()).isDestroyed()) {
                        ResourcesHandler.modifyResource("rock", 5);
                        Resource.depleteResource((RockSource) cowToMove.getDestination(), 5);

                        cowToMove.self.setSleepiness(-5);
                        Economy.giveMoney(cowToMove, 10);
                        EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                        openAnimation(500, cowToMove);
                    }
                    //TODO: Redirect cow to new destination before the end of the current animation
                    else {
                        cowToMove.setDestination(null);
                        openAnimation(1, cowToMove);
                    }
                });
                break;

            /*
            Spins the cow around by a random degree
             */
            case "spinning":
                cowToMove.currentAction = "Spinning";
                cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);
                cowToMove.setLayoutX(cowToMove.getLayoutX() + Math.cos(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                cowToMove.setLayoutY(cowToMove.getLayoutY() + Math.sin(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                cowToMove.alreadyMoving = false;
                break;
        }
        StaticUI.updateActionText();
    }

    /**
     * Creates and executes an animation that moves the given cow to the given destination.
     * @param cowToMove The given cow to move towards the destination
     * @param destination The destination that the cow is to move to
     */
    private static void animateTowardsDestination(@NotNull Cow cowToMove, @NotNull ImageView destination) {
        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);

        cowToMove.relocate(cowToMove.getAnimatedX(), cowToMove.getAnimatedY());

        if (cowToMove.getAnimatedX() < 0 || cowToMove.getAnimatedX() > 8000
            || cowToMove.getAnimatedY() < 0 || cowToMove.getAnimatedY() > 8000)
            System.out.println();

        double distanceX =  destination.getLayoutX() - cowToMove.getLayoutX();
        double distanceY = destination.getLayoutY() - cowToMove.getLayoutY();
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        cowToMove.animation = new TranslateTransition(new Duration((distanceTotal * 10)), cowToMove);

        cowToMove.setRotate(Math.toDegrees(Math.atan2(distanceY, distanceX)));

        cowToMove.animation.setToX(destination.getLayoutX() - cowToMove.getAnimatedX());
        cowToMove.animation.setToY(destination.getLayoutY() - cowToMove.getAnimatedY());
        cowToMove.animation.play();
    }

    /**
     * 'Pauses' the movement of the current cow by setting an animation for however long was given. Used for staying within
     * buildings along with making tasks take time.
     * @param centisecondDuration The duration that the 'empty' animation lasts. 0.01 of a second.
     */
    public static void pauseMovement(long centisecondDuration, @NotNull Cow cowToMove) {
        cowToMove.delayTimer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long frameTime) {
                cowToMove.alreadyMoving = true;
                if (lastUpdate == 0) {
                    lastUpdate = frameTime;
                }
                if (frameTime - lastUpdate >= centisecondDuration * 16_666_666L) {
                    stopDelayLoop(cowToMove);
                }
            }
        };
        cowToMove.delayTimer.start();
    }

    /**
     * Stops the delay loop from executing further and assures that any animation has been finalized by setting proper
     * animate/layout X and Y.
     */
    private static void stopDelayLoop(@NotNull Cow cowToMove) {
        cowToMove.delayTimer.stop();
        cowToMove.alreadyMoving = false;
        cowToMove.animation = null;

        cowToMove.setLayoutX(cowToMove.getAnimatedX());
        cowToMove.setLayoutY(cowToMove.getAnimatedY());
        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
        cowToMove.relocate(cowToMove.getAnimatedX(), cowToMove.getAnimatedY());
    }

    /**
     * Sets the alreadyMoving flag to allow for the playing of an animation to happen. Stops animations from being
     * constantly called. Passes through an animation allowance delay to pauseMovement.
     */
    private static void openAnimation(long centisecondsDuration, @NotNull Cow cowToMove) {
        pauseMovement(centisecondsDuration, cowToMove);
    }

    /**
     * Handles where the given cow is going to move.
     * @param cowToCheck The how whose movements are being decided
     */
    public static void decideAction(@NotNull Cow cowToCheck) {
        //Stop the cow from moving normally if the cow is hidden
        if (cowToCheck.isHidden()) {
            if (cowToCheck.alreadyMoving) {
                cowToCheck.updateVitals();
            }
            else {
                cowToCheck.show();
                Collision.checkForCollision(cowToCheck);
            }
            return;
        }
        cowToCheck.updateVitals();
        Collision.checkForCollision(cowToCheck);

        /*
         * Time sensitive stats based movement.
         */
        //TODO: Move movement to AI
        if (!cowToCheck.alreadyMoving) {
            //Roughly if between 10PM and 8AM
            if ((Time.getTime().getTime() > 100860000 || Time.getTime().getTime() < 50400000
                    && cowToCheck.self.getSleepiness() < 33) && cowToCheck.getLivingSpace().isConstructed())
                step("toHome", cowToCheck);
            else if (cowToCheck.self.getThirst() <= 10)
                step("toWaterSource", cowToCheck);
            else if (cowToCheck.self.getSleepiness() >= 90)
                step(cowToCheck.getJob(), cowToCheck);
            else
                step("spinning", cowToCheck);
        }

        /*
         * Static (for now) stats based decisions.
         */
        if (cowToCheck.self.getDebt() <= 10 && cowToCheck.self.getSavings() > 30
                && (BuildingHandler.getDefaultBuilding() == cowToCheck.getLivingSpace())) {

            cowToCheck.setLivingSpace(new SmallDwelling(AssetLoading.basicSmallBuilding, Tile.getRandomTerrainTile()));
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "income", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "bills", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "taxes", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "savings", cowToCheck.self.getSavings());
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "debt", 100 - cowToCheck.self.getSavings());
            cowToCheck.self.setDebt(100);
            cowToCheck.self.setSavings(-100);

            if (cowToCheck.hasOffspring()) {
                cowToCheck.getOffspring().setLivingSpace(cowToCheck.getLivingSpace());
                cowToCheck.getSpouse().setLivingSpace(cowToCheck.getLivingSpace());
            }
        }
    }

    /**
     * Checks for collision between all cows and the given dragBox
     * @param dragBox The dragBox to check for collisions against
     */
    public static void dragBoxSelectionUpdate(Rectangle dragBox) {
        Cow possibleCollide;
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            possibleCollide = CowHandler.liveCowList.get(i);
            if (possibleCollide.getBoundsInParent().intersects(dragBox.getBoundsInParent())) {
                possibleCollide.openMenu();
                StaticUI.cowClickEvent();
            }
        }
    }

    /**
     * Pauses any running cow animation.
     */
    public static void pauseAllAnimation() {
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            try {
                CowHandler.liveCowList.get(i).animation.pause();
            }
            catch (NullPointerException ignored){}
        }
    }

    /**
     * Resumes any running cow animation.
     */
    public static void startAllAnimation() {
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            try {
                CowHandler.liveCowList.get(i).animation.play();
            }
            catch (NullPointerException ignored) {}
        }
    }
}