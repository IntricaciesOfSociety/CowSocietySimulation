package cowMovement;

import buildings.BuildingHandler;
import buildings.SmallDwelling;
import cowParts.Cow;
import javafx.scene.image.ImageView;
import metaControl.LoadConfiguration;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.EventLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.RockSource;
import societalProductivity.government.Government;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Handles all the movement decision and execution for all cows. Includes the handling of all animations.
 */
public class DecideActions {

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
    }

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

    @Nullable
    private static Movement createMovement(@NotNull Cow cowToCheck) {
        //TODO: Move movement decision to AI
        Movement newMovement;
        Object destination;

        //Vitals are first priority
        if (cowToCheck.self.getThirst() <= 10) {
        }
            step("toWaterSource", cowToCheck);
        else if (cowToCheck.self.getSleepiness() >= 10)
            step(cowToCheck.getJob(), cowToCheck);

        //Social duties are next priority
        else if (Government.isElectionRunning() && !cowToCheck.hasVoted() && random.nextBoolean()) {
            cowToCheck.setHasVoted(true);
        }
        //If between 10PM and 8AM
        else if (((Time.getHours() > 22 || Time.getHours() < 8)
                && cowToCheck.self.getSleepiness() < 33) && cowToCheck.getLivingSpace().isConstructed())
            step("toHome", cowToCheck);

        else
            step("spinning", cowToCheck);
            return null;

        newMovement = new Movement() {

            @Override
            void defineMovementAction() {
                test = () -> System.out.println("");
            }z
        };


    }
}