package cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import buildings.SmallDwelling;
import cowParts.Cow;
import cowParts.CowHandler;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.EventLogger;
import resourcesManagement.*;
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
        double distanceX =  objectToCheck.getLayoutX() - cowToCheck.getTranslateX();
        double distanceY = objectToCheck.getLayoutY() - cowToCheck.getTranslateY();
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

                if (destination != null) {
                    cowToMove.currentAction = "Going to WaterSource";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        cowToMove.self.setThirst(100);
                        EventLogger.createLoggedEvent(cowToMove, "Getting water", 0, "thirst", 100);

                        pauseMovement(100, cowToMove);
                    });
                    break;
                }

            /*
            Creates an animation to move the cow to their home
             */
            case "toHome":
                destination = cowToMove.getLivingSpace();

                if (destination != null) {
                    cowToMove.currentAction = "Going home";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Building) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        EventLogger.createLoggedEvent(cowToMove, "Going home", 0, "sleepiness", 100 - cowToMove.self.getSleepiness());
                        cowToMove.self.setSleepiness(100);

                        Movement.pauseMovement(cowToMove.getBuildingTime(), cowToMove);
                    });
                    break;
                }

            /*
            Creates an animation to move the next building that needs constructing
            */
            case "woodworking":
                destination = BuildingHandler.nextHouseToConstruct();

                if (destination != null) {
                    cowToMove.currentAction = "Wood Constructing";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        if (!((Building) cowToMove.getDestination()).isConstructed()) {
                            ((Building) cowToMove.getDestination()).contributeResource("wood", 5);
                            ((Building) cowToMove.getDestination()).contributeResource("power", 1);

                            cowToMove.self.setSleepiness(-5);
                            Economy.giveMoney(cowToMove, 10);
                            EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                            pauseMovement(1000, cowToMove);
                        }
                        //TODO: Redirect cow to new destination before the end of the current animation
                        else {
                            cowToMove.setDestination(null);
                            pauseMovement(1, cowToMove);
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

                if (destination != null) {
                    cowToMove.currentAction = "Chopping Wood";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        if (!((WoodSource) cowToMove.getDestination()).isDestroyed()) {
                            ResourcesHandler.modifyResource("wood", 5);
                            Resource.depleteResource((WoodSource) cowToMove.getDestination(), 5);

                            cowToMove.self.setSleepiness(-5);
                            Economy.giveMoney(cowToMove, 10);
                            EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                            pauseMovement(500, cowToMove);
                        }
                        //TODO: Redirect cow to new destination before the end of the current animation
                        else {
                            cowToMove.setDestination(null);
                            pauseMovement(500, cowToMove);
                        }
                    });
                }
                break;

            /*
            Creates an animation to move the cow to the closest rock
            */
            case "miningRock":
                destination = RockSource.getClosestResource(cowToMove);

                if (destination != null) {
                    cowToMove.currentAction = "Mining Rock";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        if (!((RockSource) cowToMove.getDestination()).isDestroyed()) {
                            ResourcesHandler.modifyResource("rock", 5);
                            Resource.depleteResource((RockSource) cowToMove.getDestination(), 5);

                            cowToMove.self.setSleepiness(-5);
                            Economy.giveMoney(cowToMove, 10);
                            EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                            pauseMovement(500, cowToMove);
                        }
                        //TODO: Redirect cow to new destination before the end of the current animation
                        else {
                            cowToMove.setDestination(null);
                            pauseMovement(1, cowToMove);
                        }
                    });
                }
                break;

            /*
            Spins the cow around by a random degree
             */
            case "spinning":
                cowToMove.currentAction = "Spinning";
                cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);
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
    private static PathTransition animateTowardsDestination(@NotNull Cow cowToMove, @NotNull Point2D destination) {
        double distanceX =  destination.getX() - cowToMove.getTranslateX();
        double distanceY = destination.getY() - cowToMove.getTranslateY();
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        Path path = new Path();
        MoveTo moveTo = new MoveTo(cowToMove.getTranslateX(), cowToMove.getTranslateY());
        LineTo pathLine = new LineTo(destination.getX() , destination.getY());
        path.getElements().add(moveTo);
        path.getElements().add(pathLine);
        PathTransition movementAnimation = new PathTransition();
        movementAnimation.setDuration(Duration.millis(distanceTotal * 10));
        movementAnimation.setNode(cowToMove);
        movementAnimation.setPath(path);
        movementAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        cowToMove.animation = movementAnimation;

        movementAnimation.play();

        return movementAnimation;
    }

    /**
     * 'Pauses' the movement of the current cow by setting an animation for however long was given. Used for staying within
     * buildings along with making tasks take time.
     * @param stopDuration The duration that the 'empty' animation lasts. 0.01 of a second.
     */
    private static void pauseMovement(int stopDuration, @NotNull Cow cowToMove) {
        PauseTransition pause = new PauseTransition(Duration.millis(stopDuration));
        pause.setOnFinished(event -> cowToMove.alreadyMoving = false);

        cowToMove.animation = pause;
        pause.play();
    }

    /**
     * Handles where the given cow is going to move.
     * @param cowToCheck The how whose movements are being decided
     */
    public static void decideAction(@NotNull Cow cowToCheck) {
        if (cowToCheck.isHidden() && !cowToCheck.alreadyMoving) {
            cowToCheck.show();
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

            cowToCheck.setLivingSpace(new SmallDwelling(AssetLoading.basicSmallBuilding, Tile.getRandomNonBuiltUponTerrainTile()));
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