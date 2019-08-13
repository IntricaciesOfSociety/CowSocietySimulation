package cowParts.actionSystem.actionTypes;

import cowParts.actionSystem.action.EndAction;
import infrastructure.buildings.BuildingHandler;
import cowParts.creation.Cow;
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
import infrastructure.terrain.Tile;

class Movement {

    private PathTransition completeMovement;

    Movement(Cow cowToMove, EndAction endOfMovement) {
        completeMovement = createMovementAction(cowToMove);

        if (completeMovement != null)
            completeMovement.setOnFinished(event -> endOfMovement.executeBehavior());
        else
            cowToMove.setDestination(null);
    }

    @Nullable
    private PathTransition createMovementAction(Cow cowToMove) {

        if (cowToMove.getDestination() != null) {
            cowToMove.alreadyMoving = true;

            PathTransition newMovement;

            if (cowToMove.getDestination() instanceof Tile) {
                cowToMove.setRegionIn(((Tile) cowToMove.getDestination()).getRegion());
                newMovement = animateTowardsDestination(cowToMove, Tile.getEntrance((Tile) cowToMove.getDestination()));
            }
            else
                newMovement = animateTowardsDestination(cowToMove, (Point2D) cowToMove.getDestination());

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
                BuildingHandler.exitBuilding(cowToMove, cowToMove.getBuildingIn());

            cowToMove.setImage(cowToMove.skinSprite);
        });

        cowToMove.animation = pause;
        pause.play();
    }

    PathTransition getCompleteMovement() {
        return completeMovement;
    }
}
