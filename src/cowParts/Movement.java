package cowParts;

import environment.EventLogger;
import environment.Food;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Handles all the movement and the collision for the cows.
 */
public class Movement extends Cow {

    private static Random random = new Random();

    /**
     * TODO: Put into the AI
     * Moves the cow in a specified direction. Used for testing while AI is not implemented.
     * @param movementType The movement that the cow will be performing
     */
    public static void step(String movementType, @NotNull Cow cowToMove) {
        int randomNumber = random.nextInt(1 + 1 + 5) - 5;

        if (!cowToMove.alreadyMoving) {
            switch (movementType) {
                case "North":
                    cowToMove.setRotate(270);
                    cowToMove.setLayoutY(cowToMove.getLayoutY() + Math.sin(Math.toRadians(cowToMove.getRotate())) * randomNumber);
                    break;
                case "East":
                    cowToMove.setLayoutX(cowToMove.getLayoutX() - Math.cos(Math.toRadians(cowToMove.getRotate())) * randomNumber);
                    break;
                case "South":
                    cowToMove.setRotate(90);
                    cowToMove.setLayoutY(cowToMove.getLayoutY() - Math.sin(Math.toRadians(cowToMove.getRotate())) * randomNumber);
                    break;
                case "West":
                    cowToMove.setRotate(180);
                    cowToMove.setLayoutX(cowToMove.getLayoutX() - Math.cos(Math.toRadians(cowToMove.getRotate())) * randomNumber);
                    break;

            /*TODO: Switch to timeline implementation? Animation has to be stopped.
            Creates an animation to move the cow to the food
             */
                case "toFood":
                    cowToMove.currentAction = "Getting Food";
                    cowToMove.setRotate(random.nextInt(360 + 1 + 360) - 360);

                    cowToMove.alreadyMoving = true;

                    double distanceX = Food.getX() - cowToMove.getLayoutX();
                    double distanceY = Food.getY() - cowToMove.getLayoutY();
                    double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

                    final TranslateTransition transition = new TranslateTransition(new Duration((distanceTotal / 10) * 100), cowToMove);

                    transition.setOnFinished(event -> openAnimation(100, cowToMove));

                    transition.setToX(Food.getX() - cowToMove.getLayoutX());
                    transition.setToY(Food.getY() - cowToMove.getLayoutY());
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
    public static void checkForCollisions(Cow cowToMove) {
        checkForCowCollision(cowToMove);
    }

    /**
     * Checks for cow to cow collision by comparing the current cow to the rest of the cows.
     * @param cowToMove The cow that is being check for collisions
     */
    private static void checkForCowCollision(Cow cowToMove) {
        boolean collision = false;
        for (int i = 0; i < cowList.size(); i++) {
            if (cowToMove.getBoundsInParent().intersects(cowList.get(i).getBoundsInParent()) && cowList.get(i) != cowToMove) {
                collision = true;
                if (!Social.relationExists(cowToMove, cowList.get(i)))
                    Social.newRelation(cowToMove, cowList.get(i));
            }
        }
        if (collision) {
            if (!cowToMove.parent) {
                cowToMove.parent = true;
                Cow newCow = new Cow();
                newCow.parent = true;
                newCow.relocate(cowToMove.getAnimatedX(), cowToMove.getAnimatedY());
                cowList.add(newCow);
            }
        }
    }
}
