package cowParts.cowMovement;

import infrastructure.buildingTypes.GenericBuilding;
import cowParts.Cow;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import metaControl.main.SimState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

class Movement extends Action {

    private Start destination;
    private End endBehavior;

    Movement(Start startBehavior, @NotNull Finish endBehavior, Cow cowToMove) {
        this.destination = startBehavior;
        this.endBehavior = endBehavior::executeFinish;

        completeAction = createMovementAction(cowToMove);
    }

    @Nullable
    private PathTransition createMovementAction(Cow cowToMove) {

        if (destination.startBehavior() != null) {
            cowToMove.setDestination(destination.startBehavior());
            cowToMove.alreadyMoving = true;

            PathTransition newMovement;

            if (destination.startBehavior() instanceof Tile) {
                cowToMove.setRegionIn(((Tile) destination.startBehavior()).getRegion());
                newMovement = animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) destination.startBehavior()));
            }

            else
                newMovement = animateTowardsDestination(cowToMove, (Point2D) destination.startBehavior());

            newMovement.setOnFinished((event) -> endBehavior.endBehavior());

            //TODO: Implement path redirection through executionBehavior
            cowToMove.setCacheHint(CacheHint.SPEED);
            cowToMove.setCache(true);

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
        movementAnimation.setDuration( Duration.millis( (distanceTotal /  5) * SimState.getDeltaTime() * 5) );
        movementAnimation.setNode(cowToMove);
        movementAnimation.setPath(path);
        movementAnimation.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

        cowToMove.animation = movementAnimation;

        return movementAnimation;
    }

    /**TODO: Move to an action?
     * 'Pauses' the movement of the current cow by setting an animation for however long was given. Used for staying within
     * infrastructure along with making tasks take time.
     * @param stopDuration The duration that the 'empty' animation lasts. 0.01 of a second.
     */
    static void pauseMovement(int stopDuration, @NotNull Cow cowToMove) {
        PauseTransition pause = new PauseTransition(Duration.millis(stopDuration));
        pause.setOnFinished(event -> {
            cowToMove.alreadyMoving = false;

            if (cowToMove.isHidden())
                GenericBuilding.exitBuilding(cowToMove, cowToMove.getBuildingIn());

            cowToMove.setImage(cowToMove.skinSprite);
        });

        cowToMove.animation = pause;
        pause.play();
    }
}
