package cowParts.cowMovement;

import buildings.Building;
import cowParts.Cow;
import cowParts.Social;
import javafx.scene.Node;
import metaEnvironment.logging.EventLogger;
import metaEnvironment.Playground;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.Resource;
import terrain.Tile;

import java.util.ConcurrentModificationException;
import java.util.Random;

/**
 * Handles all handled collision events between objects. Collisions are mostly centered around cows.
 * DEPRECATED
 */
class Collision {

    private static Random random = new Random();

    /**
     * Checks for collision by comparing the current cow to the rest of the nodes in the playground. If playground's root
     * list is currently being modified, then try again (Yay recursion).
     * @param cowToMove The cow that is being check for collisions
     */
    static void checkForCollision(Cow cowToMove) {
        /*try {
            for (Node possibleCollide : Playground.playground.getChildren()) {
                if (possibleCollide != cowToMove && cowToMove.getBoundsInParent().intersects(possibleCollide.getBoundsInParent())) {
                    if (possibleCollide instanceof  Tile)
                        cowToTileCollision((Tile) possibleCollide);
                    if (possibleCollide instanceof Cow)
                        cowToCowCollision(cowToMove, (Cow) possibleCollide);
                    else if (possibleCollide instanceof Building)
                        cowToBuildingCollision(cowToMove, (Building) possibleCollide);
                    else if (possibleCollide instanceof Resource)
                        cowToResourceCollision(cowToMove, (Tile) possibleCollide);
                }
            }
        }
        catch (ConcurrentModificationException e) {
            checkForCollision(cowToMove);
        }*/
    }

    /**
     * Handles any cow to resource collision event.
     * @param cowToMove The cow colliding with a resource
     * @param possibleCollide The resource that the cow is colliding with
     */
    private static void cowToResourceCollision(@NotNull Cow cowToMove, Tile possibleCollide) {
        if (cowToMove.getDestination() != null && cowToMove.getDestination() == possibleCollide)
            return;
    }

    /**
     * Handles a collision between the given tile and cow. Sets the tile that the cow is on to this tile.
     * @param possibleCollide The tile that is colliding
     */
    private static void cowToTileCollision(Tile possibleCollide) {
        DecideActions.tileStandingOn = possibleCollide;
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
            cowToMove.self.setCompanionship(5);
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
                Building.enterBuilding(cowToMove, intersectingBuilding);

                //Called as the cow is ready to exit the building indirectly from the cowToMove.show in decideMovement()
            else if (!cowToMove.isHidden())
                Building.exitBuilding(cowToMove, intersectingBuilding);
        }
    }
}
