package cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import cowParts.Cow;
import cowParts.CowHandler;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import metaEnvironment.EventLogger;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.*;
import societalProductivity.Role;
import societalProductivity.government.Economy;
import societalProductivity.government.Government;
import terrain.Tile;
import userInterface.StaticUI;

import java.util.Random;

public class ExecuteAction {

    private static Random random = new Random();


    /**
     * Executes a predefined movement to the predefined cow
     * @param newMovement The movement that the predefined cow will be performing
     */
     static void execute(Action newMovement) {
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
                }
                break;

            case "voting":
                destination = BuildingHandler.getClosestVotingArea(cowToMove);

                if (destination != null) {
                    cowToMove.currentAction = "Voting";
                    cowToMove.setDestination(destination);

                    animateTowardsDestination(cowToMove, Tile.getEntrance((Building) destination));
                    cowToMove.animation.setOnFinished(event -> {
                        Building.enterBuilding(cowToMove, (Building) cowToMove.getDestination());
                        Government.vote(CowHandler.liveCowList.get(random.nextInt(CowHandler.liveCowList.size())));

                        EventLogger.createLoggedEvent(cowToMove, "Voting", 0, "trust", 10);
                        cowToMove.self.setTrust(10);

                        pauseMovement(cowToMove.getBuildingTime(), cowToMove);
                    });
                }
                break;

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
                        Building.enterBuilding(cowToMove, (Building) cowToMove.getDestination());

                        EventLogger.createLoggedEvent(cowToMove, "Going home", 0, "sleepiness", 100 - cowToMove.self.getSleepiness());
                        cowToMove.self.setSleepiness(100);

                        pauseMovement(cowToMove.getBuildingTime(), cowToMove);
                    });
                }
                break;

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
                        //TODO: Redirect cow to new endPoint before the end of the current animation
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
                        //TODO: Redirect cow to new endPoint before the end of the current animation
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
                        //TODO: Redirect cow to new endPoint before the end of the current animation
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
     * Creates and executes an animation that moves the given cow to the given endPoint.
     * @param cowToMove The given cow to move towards the endPoint
     * @param destination The endPoint that the cow is to move to
     */
    private static void animateTowardsDestination(@NotNull Cow cowToMove, @NotNull Point2D destination) {
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
    }

    /**
     * 'Pauses' the movement of the current cow by setting an animation for however long was given. Used for staying within
     * buildings along with making tasks take time.
     * @param stopDuration The duration that the 'empty' animation lasts. 0.01 of a second.
     */
    private static void pauseMovement(int stopDuration, @NotNull Cow cowToMove) {
        PauseTransition pause = new PauseTransition(Duration.millis(stopDuration));
        pause.setOnFinished(event -> {
            cowToMove.alreadyMoving = false;

            if (cowToMove.isHidden())
                Building.exitBuilding(cowToMove, cowToMove.getBuildingIn());
        });

        cowToMove.animation = pause;
        pause.play();
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