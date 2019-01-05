package cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import buildings.SmallDwelling;
import cowParts.Cow;
import cowParts.CowHandler;
import javafx.scene.image.ImageView;
import metaControl.LoadConfiguration;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.EventLogger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.WaterSource;
import societalProductivity.Role;
import societalProductivity.government.Economy;
import societalProductivity.government.Government;
import terrain.Tile;
import userInterface.StaticUI;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles all the movement decision and execution for all cows. Includes the handling of all animations.
 */
public class DecideActions {

    private static Object destination;

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

    /**TODO: Implement AI
     * Handles where the given cow is going to move.
     * @param cowToCheck The how whose movements are being decided
     */
    public static void decideActions(@NotNull Cow cowToCheck) {
        cowToCheck.updateVitals();
        Collision.checkForCollision(cowToCheck);

        //Only one movement action at a time
        if (!cowToCheck.alreadyMoving)
            ExecuteAction.execute(createMovement(cowToCheck));

        //Any action that is not a movement
        //ExecuteAction.executeMultipleActions(createGeneralActions());
        createGeneralActions(cowToCheck);
    }

    public interface Finish {
        void executeFinish();
    }

    @Nullable
    @Contract("_ -> new")
    private static Movement createMovement(@NotNull Cow cowToCheck) {
        //TODO: Move movement decision to AI

        //Vitals are first priority
        Finish finishBehavior;
        
        if (cowToCheck.self.getThirst() <= 10) {
            destination = WaterSource.getClosestResource(cowToCheck);

            finishBehavior = () -> {
                cowToCheck.self.setThirst(100);
                EventLogger.createLoggedEvent(cowToCheck, "Getting water", 0, "thirst", 100);
                Movement.pauseMovement(100, cowToCheck);
            };
        }
        else if (cowToCheck.self.getSleepiness() >= 10) {
            destination = Role.getRoleDestination(cowToCheck);

            finishBehavior = () -> {
                Role.getRoleCompletionBehavior(cowToCheck);

                cowToCheck.self.setSleepiness(-20);
                Economy.giveMoney(cowToCheck, 10);
                EventLogger.createLoggedEvent(cowToCheck, "Working", 0, "sleepiness", -20);

                Movement.pauseMovement(500, cowToCheck);
            };
        }
        //Social duties are next priority
        else if (Government.isElectionRunning() && !cowToCheck.hasVoted() && random.nextBoolean()) {
            cowToCheck.setHasVoted(true);
            destination = BuildingHandler.getClosestVotingArea(cowToCheck);

            finishBehavior = () -> {
                Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());
                Government.vote(CowHandler.liveCowList.get(random.nextInt(CowHandler.liveCowList.size())));

                EventLogger.createLoggedEvent(cowToCheck, "Voting", 0, "trust", 10);
                cowToCheck.self.setTrust(10);

                Movement.pauseMovement(cowToCheck.getBuildingTime(), cowToCheck);
            };
        }
        //If between 10PM and 8AM
        else if (((Time.getHours() > 20 || Time.getHours() < 8) && cowToCheck.self.getSleepiness() < 33) && cowToCheck.getLivingSpace().isConstructed()) {
            destination = cowToCheck.getLivingSpace();

            finishBehavior = () -> {
                Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());

                EventLogger.createLoggedEvent(cowToCheck, "Going home", 0, "sleepiness", 100 - cowToCheck.self.getSleepiness());
                cowToCheck.self.setSleepiness(100);

                Movement.pauseMovement(cowToCheck.getBuildingTime(), cowToCheck);
            };
        }
        //TODO: Create spinning action
        else {
            cowToCheck.setRotate(random.nextInt(360 + 1 + 360) - 360);
            cowToCheck.alreadyMoving = false;
            return null;
        }

        StaticUI.updateActionText();

        return new Movement (
                () -> destination,
                () -> Movement.validateDestination(destination),
                finishBehavior,
                cowToCheck);
    }

    /**TODO: Implement into the actions system
     *
     * @param cowToCheck
     * @return
     */
    @Nullable
    private static ArrayList<Action> createGeneralActions(@NotNull Cow cowToCheck) {
        /*
         * Static (for now) stats based decisions.
         */
        if (cowToCheck.self.getDebt() <= 10 && cowToCheck.self.getSavings() > 30
                && (BuildingHandler.getDefaultBuilding() == cowToCheck.getLivingSpace())) {

            cowToCheck.setLivingSpace(new SmallDwelling(AssetLoading.basicSmallBuilding, LoadConfiguration.getBasicSmallDwelling(), Tile.getRandomNonBuiltUponTerrainTile()));
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
        return null;
    }
}