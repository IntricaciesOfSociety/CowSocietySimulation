package cowMovement;

import buildings.Building;
import cowParts.Cow;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

class Movement extends Action {

    Start destination;
    Execution executionBehavior;
    End endBehavior;

    Movement(Start startBehavior, Execution executionBehavior, DecideActions.Finish endBehavior, Cow cowToMove) {
        this.destination = startBehavior;
        this.executionBehavior = executionBehavior;
        this.endBehavior = endBehavior::executeFinish;

        completeAction = createMovementAction(cowToMove);
    }

    @Nullable
    private PathTransition createMovementAction(Cow cowToMove) {
        if (destination.startBehavior() != null) {
            cowToMove.setDestination(destination.startBehavior());
            cowToMove.alreadyMoving = true;
            //TODO: Implement action text somehow
            cowToMove.currentAction = "Implement me!";

            PathTransition newMovement = animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination.startBehavior()));
            newMovement.setOnFinished((event) -> endBehavior.endBehavior());

            //TODO: Implement path redirection through executionBehavior
            return newMovement;
        }
        else
            return null;
    }

    /**
     * Creates and executes an animation that moves the given cow to the given endPoint.
     * @param cowToMove The given cow to move towards the endPoint
     * @param destination The endPoint that the cow is to move to
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

        return movementAnimation;
    }

    public static void validateDestination(Object destination) {

    }

    /**TODO: Move to an action?
     * 'Pauses' the movement of the current cow by setting an animation for however long was given. Used for staying within
     * buildings along with making tasks take time.
     * @param stopDuration The duration that the 'empty' animation lasts. 0.01 of a second.
     */
    static void pauseMovement(int stopDuration, @NotNull Cow cowToMove) {
        PauseTransition pause = new PauseTransition(Duration.millis(stopDuration));
        pause.setOnFinished(event -> {
            cowToMove.alreadyMoving = false;

            if (cowToMove.isHidden())
                Building.exitBuilding(cowToMove, cowToMove.getBuildingIn());
        });

        cowToMove.animation = pause;
        pause.play();
    }
}
