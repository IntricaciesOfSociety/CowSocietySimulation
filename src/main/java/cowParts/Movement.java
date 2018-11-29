package cowParts;

import buildings.Building;
import buildings.BuildingHandler;
import javafx.scene.Node;
import metaEnvironment.EventLogger;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import resourcesManagement.Water;
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
    private static void step(String movementType, @NotNull Cow cowToMove) {
        if (!cowToMove.alreadyMoving) {
            switch (movementType) {

            /*TODO: Switch to timeline implementation? Animation has to be stopped.
            Creates an animation to move the cow to the food
             */
                case "toFood":
                    cowToMove.currentAction = "Getting Water";
                    cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);

                    cowToMove.alreadyMoving = true;

                    double distanceX = Water.getX() - cowToMove.getLayoutX();
                    double distanceY = Water.getY() - cowToMove.getLayoutY();
                    double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                    final TranslateTransition transition = new TranslateTransition(new Duration((distanceTotal / 10) * 100), cowToMove);

                    cowToMove.setRotate(Math.toDegrees(Math.atan2(distanceY, distanceX)));
                    transition.setOnFinished(event -> openAnimation(100, cowToMove));

                    transition.setToX(Water.getX() - cowToMove.getLayoutX());
                    transition.setToY(Water.getY() - cowToMove.getLayoutY());
                    transition.play();

                    cowToMove.setHunger(100);
                    EventLogger.createLoggedEvent(cowToMove, "Getting food", 0, "hunger", 100);

                    break;

                case "random":
                    cowToMove.currentAction = "Spinning";
                    cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);
                    cowToMove.setLayoutX(cowToMove.getLayoutX() + Math.cos(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                    cowToMove.setLayoutY(cowToMove.getLayoutY() + Math.sin(Math.toRadians(cowToMove.getRotate())) * random.nextInt(10));
                    break;
            }
        }
    }

    /**
     * 'Pauses' the movement of the current cow by setting an animation for however long was given.
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
     * Stops the delay loop from executing further.
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
        cowToMove.alreadyMoving = false;
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
     * Handles a collision between the given building and cow. Moves the cow into the building as an inhabitant.
     * @param cowToMove The cow that is colliding
     * @param intersectingBuilding The building that is colliding
     */
    private static void cowToBuildingCollision(@NotNull Cow cowToMove, @NotNull Building intersectingBuilding) {
        cowToMove.hide();
        if (!intersectingBuilding.getCurrentInhabitants().contains(cowToMove))
            intersectingBuilding.addInhabitant(cowToMove);
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
            cowToCheck.updateVitals();
            return;
        }

        //TODO: Move movement to AI
        if (cowToCheck.getHunger() <= 10)
            step("toFood", cowToCheck);
        else
            step("random", cowToCheck);

        cowToCheck.updateVitals();
        Movement.checkForCollisions(cowToCheck);

        if (cowToCheck.getDebt() <= 10) {
            cowToCheck.setLivingSpace(BuildingHandler.createBuilding("CowShack", tileStandingOn));
            cowToCheck.setDebt(100);
        }
    }

    /**
     * Gets the tile that the last cow checked is standing on.
     * @return The tile that the cow is standing on
     */
    @Contract(pure = true)
    public static Tile getStandingTile() {
        return tileStandingOn;
    }
}
