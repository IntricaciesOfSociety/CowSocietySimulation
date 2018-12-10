package cowParts;

import buildings.Building;
import buildings.BuildingHandler;
import buildings.SmallDwelling;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import metaControl.SimState;
import metaEnvironment.EventLogger;
import metaEnvironment.Playground;
import resourcesManagement.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import societalProductivity.Role;
import terrain.Tile;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Handles all the movement and the collision for the cows.
 */
public class Movement extends Cow {

    private static Random random = new Random();

    private static Tile tileStandingOn;

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
        switch (movementType) {

        /*TODO: Switch to timeline implementation? Animation has to be stopped.
        Creates an animation to move the cow to the food
         */
            case "toWaterSource":
                cowToMove.currentAction = "Going to WaterSource";
                cowToMove.setDestination(WaterSource.getWateringHole());

                animateTowardsDestination(cowToMove, (WaterSource) cowToMove.getDestination());
                cowToMove.animation.setOnFinished(event -> {
                    cowToMove.setHunger(100);
                    EventLogger.createLoggedEvent(cowToMove, "Getting water", 0, "hunger", 100);

                    openAnimation(100, cowToMove);
                });
                break;

            case "toHome":
                cowToMove.currentAction = "Going home";
                cowToMove.setDestination(cowToMove.getLivingSpace());

                cowToMove.setSleepiness(100);
                animateTowardsDestination(cowToMove, (Tile) cowToMove.getDestination());
                cowToMove.animation.setOnFinished(event -> {

                    EventLogger.createLoggedEvent(cowToMove, "Going home", 0, "sleepiness", 100);

                    openAnimation(1, cowToMove);
                });
                break;

            case "woodworking":
                cowToMove.currentAction = "Wood Constructing";

                if (BuildingHandler.nextHouseToConstruct() != null) {
                    cowToMove.setDestination(BuildingHandler.nextHouseToConstruct());

                    //TODO: Handle null house construct issue
                    animateTowardsDestination(cowToMove, (Tile) cowToMove.getDestination());
                    cowToMove.animation.setOnFinished(event -> {
                        BuildingHandler.nextHouseToConstruct().contributeResource("wood", 5);
                        BuildingHandler.nextHouseToConstruct().contributeResource("power", 1);
                        cowToMove.setSleepiness(cowToMove.getSleepiness() - 5);
                        EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                        openAnimation(1000, cowToMove);
                    });
                }
                else
                    new Role(cowToMove);

                break;

            case "choppingWood":
                cowToMove.currentAction = "Chopping Wood";
                cowToMove.setDestination(WoodSource.getClosestResource(cowToMove));

                animateTowardsDestination(cowToMove, (WoodSource) cowToMove.getDestination());
                cowToMove.animation.setOnFinished(event -> {
                    ResourcesHandler.modifyResource("wood", 5);
                    cowToMove.setSleepiness(cowToMove.getSleepiness() - 5);
                    EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                    openAnimation(500, cowToMove);
                });
                break;

            case "miningRock":
                cowToMove.currentAction = "Mining Rock";
                cowToMove.setDestination(RockSource.getClosestResource(cowToMove));

                animateTowardsDestination(cowToMove, (RockSource) cowToMove.getDestination());
                cowToMove.animation.setOnFinished(event -> {
                    ResourcesHandler.modifyResource("rock", 5);
                    cowToMove.setSleepiness(cowToMove.getSleepiness() - 5);
                    EventLogger.createLoggedEvent(cowToMove, "Working", 0, "sleepiness", -5);

                    openAnimation(500, cowToMove);
                });
                break;

            case "spinning":
                cowToMove.currentAction = "Spinning";
                cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);
                cowToMove.setLayoutX(cowToMove.getLayoutX() + Math.cos(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                cowToMove.setLayoutY(cowToMove.getLayoutY() + Math.sin(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                break;
        }
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
        cowToMove.alreadyMoving = true;

        double distanceX =  destination.getLayoutX() - cowToMove.getAnimatedX();
        double distanceY = destination.getLayoutY() - cowToMove.getAnimatedY();
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        cowToMove.animation = new TranslateTransition(new Duration((distanceTotal / 10) * 100), cowToMove);

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
    private static void pauseMovement(long centisecondDuration, @NotNull Cow cowToMove) {
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
     * Calls any collision check to be executed during a normal tick.
     */
    private static void checkForCollisions(Cow cowToMove) {
        checkForCollision(cowToMove);
    }

    /**
     * Checks for collision by comparing the current cow to the rest of the nodes in the playground. If playground's root
     * list is currently being modified, then try again (Yay recursion).
     * @param cowToMove The cow that is being check for collisions
     */
    private static void checkForCollision(Cow cowToMove) {
        try {
            for (Node possibleCollide : Playground.playground.getChildren()) {
                if (possibleCollide != cowToMove && cowToMove.getBoundsInParent().intersects(possibleCollide.getBoundsInParent())) {
                    if (possibleCollide instanceof Cow)
                        cowToCowCollision(cowToMove, (Cow) possibleCollide);
                    else if (possibleCollide instanceof Building)
                        cowToBuildingCollision(cowToMove, (Tile) possibleCollide);
                    else if (possibleCollide instanceof  Tile)
                        cowToTileCollision((Tile) possibleCollide);
                    else if (possibleCollide instanceof Resource)
                        cowToResourceCollision(cowToMove, (Tile) possibleCollide);
                }
            }
        }
        catch (ConcurrentModificationException e) {
            checkForCollisions(cowToMove);
        }
    }

    /**
     * Handles any cow to resource collision event.
     * @param cowToMove The cow colliding with a resource
     * @param possibleCollide The resource that the cow is colliding with
     */
    private static void cowToResourceCollision(@NotNull Cow cowToMove, Tile possibleCollide) {
        if (cowToMove.getDestination().equals(possibleCollide)) {
            return;
        }
    }

    /**
     * Handles a cow to cow collision. If a collision has occurred create a child, if one hasn't been created already.
     * Increase companionship if applicable.
     * @param cowToMove The first cow to collide
     * @param intersectingCow The other cow to collide
     */
    private static void cowToCowCollision(Cow cowToMove, Cow intersectingCow) {
        if (Social.relationExists(cowToMove, intersectingCow)) {
            if (random.nextInt(1000) == 1)
                Social.modifyRelationValue(cowToMove, intersectingCow, random.nextInt(2));
        }
        else {
            Social.newRelation(cowToMove, intersectingCow);
            cowToMove.setCompanionship(cowToMove.getCompanionship() + 5);
            EventLogger.createLoggedEvent(cowToMove, "new relationship", 1, "companionship", 5);
        }
    }

    /**
     * Handles a collision between the given building and cow. Moves the cow into the building as an inhabitant for an
     * anmout of time equal to the cow's buildingTime variable. Stops and resets all animation upon entry, and relocates
     * the cow to the entrance of the building after exiting.
     * @param cowToMove The cow that is colliding
     * @param intersectingBuilding The building that is colliding
     */
    private static void cowToBuildingCollision(@NotNull Cow cowToMove, @NotNull Tile intersectingBuilding) {
        if (cowToMove.getDestination() == intersectingBuilding && cowToMove.currentAction.equals("Going home")) {
            //Called as the cow first enters the building
            if (!((Building)intersectingBuilding).getCurrentInhabitants().contains(cowToMove))
                enterBuilding(cowToMove, intersectingBuilding);

            //Called as the cow is ready to exit the building indirectly from the cowToMove.show in decideMovement()
            else if (!cowToMove.isHidden())
                exitBuilding(cowToMove, intersectingBuilding);
        }
    }

    /**
     * Adds the given cow to the given building and updates that cow's animation accordingly.
     * @param cowToMove The cow to be added into the building
     * @param buildingToMoveInto The building to add the cow into
     */
    private static void enterBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToMoveInto) {
        cowToMove.hide();
        cowToMove.setBuildingIn((Building) buildingToMoveInto);

        ((Building) buildingToMoveInto).addInhabitant(cowToMove);

        if(cowToMove.animation != null) {
            cowToMove.animation.stop();
        }


        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
        cowToMove.setLayoutX(buildingToMoveInto.getLayoutX());
        cowToMove.setLayoutY(buildingToMoveInto.getLayoutY());
        cowToMove.relocate(buildingToMoveInto.getLayoutX(), buildingToMoveInto.getLayoutY());

        pauseMovement(cowToMove.getBuildingTime(), cowToMove);
    }

    /**
     * Removes the cow from the building that it is in.
     * @param cowToMove The cow to remove from the building
     * @param buildingToExitFrom The building to remove the given cow from
     */
    private static void exitBuilding(@NotNull Cow cowToMove, @NotNull Tile buildingToExitFrom) {
        cowToMove.setBuildingIn(null);

        cowToMove.relocate(buildingToExitFrom.getLayoutX() + buildingToExitFrom.getImage().getWidth() / 2,
                buildingToExitFrom.getLayoutY() + buildingToExitFrom.getImage().getHeight() + 75);
        ((Building)buildingToExitFrom).removeInhabitant(cowToMove);
        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
    }

    /**
     * Handles a collision between the given tile and cow. Sets the tile that the cow is on to this tile.
     * @param possibleCollide The tile that is colliding
     */
    private static void cowToTileCollision(Tile possibleCollide) {
        tileStandingOn = possibleCollide;
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
                Movement.checkForCollisions(cowToCheck);
            }
            return;
        }

        cowToCheck.updateVitals();
        Movement.checkForCollisions(cowToCheck);

        /*
         * Time sensitive stats based movement.
         */
        //TODO: Move movement to AI
        if (!cowToCheck.alreadyMoving) {
            //Roughly if between 10PM and 8AM
            if ((SimState.getDate().getTime() > 100860000 || SimState.getDate().getTime() < 50400000)
                    && cowToCheck.getSleepiness() < 100)
                step("toHome", cowToCheck);
            else if (cowToCheck.getHunger() <= 10)
                step("toWaterSource", cowToCheck);
            else
                step(cowToCheck.getJob(), cowToCheck);
        }

        /*
         * Static (for now) stats based decisions.
         */
        if (cowToCheck.getDebt() <= 10) {
            cowToCheck.setLivingSpace(new SmallDwelling(BuildingHandler.loadSprite("CowShack"), tileStandingOn));
            cowToCheck.setDebt(100);
        }
    }
}