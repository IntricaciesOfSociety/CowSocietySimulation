package cowParts.cowMovement;

import buildings.BuildingHandler;
import cowParts.Cow;
import javafx.scene.image.ImageView;
import metaControl.Time;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import societalProductivity.government.Government;
import terrain.Tile;
import userInterface.StaticUI;

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

    /**
     * Handles where the given cow is going to move.
     * @param cowToCheck The how whose movements are being decided
     */
    public static void decideActions(@NotNull Cow cowToCheck) {
        cowToCheck.updateVitals();
        Collision.checkForCollision(cowToCheck);

        //Only one movement action at a time
        if (!cowToCheck.alreadyMoving)
            ExecuteAction.execute(decideMovement(cowToCheck));

        //Any action that is not a movement
        //ExecuteAction.executeMultipleActions(createGeneralActions());
        createGeneralActions(cowToCheck);
    }

    private static Movement decideMovement(@NotNull Cow cowToCheck) {
        if (cowToCheck.self.getThirst() <= 10)
            return ActiveActions.getWater(cowToCheck);
        else if (cowToCheck.self.getSleepiness() > 0)
            return ActiveActions.goWork(cowToCheck);
        else if (Government.isElectionRunning() && !cowToCheck.hasVoted() && random.nextBoolean())
            return ActiveActions.goVote(cowToCheck);
        //If between 10PM and 8AM
        else if (((Time.getHours() > 20 || Time.getHours() < 8) && cowToCheck.self.getSleepiness() < 33) && cowToCheck.getLivingSpace().isConstructed())
            return ActiveActions.goHome(cowToCheck);
        else
            return ActiveActions.goSpin(cowToCheck);
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
            PassiveActions.buyHouse(cowToCheck);
        }
        return null;
    }
}