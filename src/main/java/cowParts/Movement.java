package cowParts;

import buildings.Building;
import buildings.BuildingHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import metaControl.SimState;
import metaEnvironment.EventLogger;
import metaEnvironment.Playground;
import resourcesManagement.WaterSource;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Handles all the movement and the collision for the cows.
 */
public class Movement extends Cow {

    private static Random random = new Random();

    private static Tile tileStandingOn;

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

                animateTowardsDestination(cowToMove, WaterSource.getWateringHole(), 100);
                cowToMove.setHunger(100);
                EventLogger.createLoggedEvent(cowToMove, "Getting water", 0, "hunger", 100);

                break;

            case "toHome":
                cowToMove.currentAction = "Going home";

                animateTowardsDestination(cowToMove, cowToMove.getLivingSpace(), 0);
                cowToMove.setSleepiness(100);
                break;

            case "random":
                cowToMove.currentAction = "Spinning";
                cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);
                cowToMove.setLayoutX(cowToMove.getLayoutX() + Math.cos(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                cowToMove.setLayoutY(cowToMove.getLayoutY() + Math.sin(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                break;
        }
    }

    private static void animateTowardsDestination(@NotNull Cow cowToMove, @NotNull ImageView destination, long taskDuration) {
        cowToMove.setTranslateX(0);
        cowToMove.setTranslateY(0);
        cowToMove.alreadyMoving = true;

        double distanceX =  destination.getLayoutX() - cowToMove.getLayoutX();
        double distanceY = destination.getLayoutY() - cowToMove.getLayoutY();
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        cowToMove.animation = new TranslateTransition(new Duration((distanceTotal / 10) * 100), cowToMove);

        cowToMove.setRotate(Math.toDegrees(Math.atan2(distanceY, distanceX)));
        cowToMove.animation.setOnFinished(event -> openAnimation(taskDuration, cowToMove));

        cowToMove.animation.setToX(destination.getLayoutX() - cowToMove.getLayoutX());
        cowToMove.animation.setToY(destination.getLayoutY() - cowToMove.getLayoutY());
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
                if (lastUpdate == 0)
                    lastUpdate = frameTime;
                if (frameTime - lastUpdate >= centisecondDuration * 16_666_666L)
                    stopDelayLoop(cowToMove);
            }
        };
        cowToMove.delayTimer.start();
    }

    /**
     * Stops the delay loop from executing further. If the loop
     */
    private static void stopDelayLoop(@NotNull Cow cowToMove) {
        cowToMove.delayTimer.stop();
        cowToMove.alreadyMoving = false;
    }

    /**
     * Sets the alreadyMoving flag to allow for the playing of an animation to happen. Stops animations from being
     * constantly called. Passes through an animation allowance delay to pauseMovement.
     */
    private static void openAnimation(long centisecondsDuration, Cow cowToMove) {
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
                        cowToBuildingCollision(cowToMove, (Building) possibleCollide);
                    else if (possibleCollide instanceof  Tile)
                        cowToTileCollision((Tile) possibleCollide);
                }
            }
        }
        catch (ConcurrentModificationException e) {
            checkForCollisions(cowToMove);
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
            if (!cowToMove.parent) {
                cowToMove.parent = true;

                Cow newCow = new Cow();
                newCow.parent = true;

                newCow.relocate(cowToMove.getAnimatedX(), cowToMove.getAnimatedY());
                cowList.add(newCow);
            }

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
    private static void cowToBuildingCollision(@NotNull Cow cowToMove, @NotNull Building intersectingBuilding) {
        //Called as the cow first enters the building
        if (!intersectingBuilding.getCurrentInhabitants().contains(cowToMove)) {
            cowToMove.hide();
            intersectingBuilding.addInhabitant(cowToMove);

            if(cowToMove.animation != null)
                cowToMove.animation.stop();

            cowToMove.setTranslateX(0);
            cowToMove.setTranslateY(0);
            cowToMove.setLayoutX(intersectingBuilding.getLayoutX());
            cowToMove.setLayoutY(intersectingBuilding.getLayoutY());
            cowToMove.relocate(intersectingBuilding.getLayoutX(), intersectingBuilding.getLayoutY());

            pauseMovement(cowToMove.getBuildingTime(), cowToMove);
        }
        //Called as the cow is ready to exit the building indirectly from the cowToMove.show in decideMovement()
        else if (!cowToMove.isHidden()) {
            cowToMove.relocate(intersectingBuilding.getLayoutX() + intersectingBuilding.getImage().getWidth() / 2,
                    intersectingBuilding.getLayoutY() + intersectingBuilding.getImage().getHeight() + 75);
            intersectingBuilding.removeInhabitant(cowToMove);
            cowToMove.setTranslateX(0);
            cowToMove.setTranslateY(0);
        }
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
                step("random", cowToCheck);
        }

        /*
         * Static (for now) stats based decisions.
         */
        if (cowToCheck.getDebt() <= 10) {
            cowToCheck.setLivingSpace(BuildingHandler.createBuilding("CowShack", tileStandingOn));
            cowToCheck.setDebt(100);
        }
    }
}
