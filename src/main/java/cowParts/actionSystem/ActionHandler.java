package cowParts.actionSystem;

import cowParts.actionSystem.action.ExecuteAction;
import cowParts.actionSystem.action.GenericAction;
import cowParts.actionSystem.actionTypes.ActiveActions;
import cowParts.actionSystem.actionTypes.PassiveActions;
import cowParts.cowThoughts.traitsSystem.Interests;
import cowParts.creation.Cow;
import cowParts.cowAI.NaturalSelection;
import infrastructure.establishments.EstablishmentCreation;
import javafx.scene.image.ImageView;
import metaControl.timeControl.Time;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles all the movement decision and execution for all cows. Includes the handling of all animations.
 */
public class ActionHandler {

    /**
     * Finds the distance as one number between the given cow and the given object
     * @param cowToCheck The cow to find distance to
     * @param objectToCheck The object to find distance to
     * @return The distance between the given cow and object as a single number
     */
    public static double findDistanceBetweenCowAndObject(@NotNull Cow cowToCheck, @NotNull ImageView objectToCheck) {
        double distanceX;
        double distanceY;

        if (objectToCheck instanceof Tile) {
            distanceX = (objectToCheck.getLayoutX() + ((Tile) objectToCheck).getRegion().getLayoutX()) - cowToCheck.getTranslateX();
            distanceY = (objectToCheck.getLayoutY() + ((Tile) objectToCheck).getRegion().getLayoutY()) - cowToCheck.getTranslateY();
        }
        else {
            distanceX = objectToCheck.getLayoutX() - cowToCheck.getTranslateX();
            distanceY = objectToCheck.getLayoutY() - cowToCheck.getTranslateY();
        }
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Handles where the given cow is going to move.
     * @param cowToCheck The how whose movements are being decided
     */
    public static void decideActions(@NotNull Cow cowToCheck) {
        cowToCheck.updateVitals();

        //Only one movement action at a time
        if (!cowToCheck.alreadyMoving && decideActiveAction(cowToCheck) != null)
            ExecuteAction.execute(decideActiveAction(cowToCheck));

        //Any action that is not a movement
        //ExecuteAction.executeMultipleActions(decidePassiveActions());
        decidePassiveActions(cowToCheck);
    }

    private static GenericAction decideActiveAction(@NotNull Cow cowToCheck) {
        //Vital actions
        if (cowToCheck.self.getThirst() <= 10 || cowToCheck.self.getHunger() <= 10)
            return ActiveActions.getVitalAction(cowToCheck);

        //Economical/GovernmentalBuilding actions
        else if (cowToCheck.self.getSleepiness() > 0)
            return ActiveActions.goWork(cowToCheck);

        //Social actions
        else if (((Time.getHours() > 20 || Time.getHours() < 8) && cowToCheck.self.getSleepiness() < 33) && cowToCheck.getLivingSpace().isConstructed())
            return ActiveActions.goHome(cowToCheck);
        else if (cowToCheck.birth.isFertile() && NaturalSelection.getMostFitAndFertile(cowToCheck) != null)
            return ActiveActions.createChild(cowToCheck);

        else
            return ActiveActions.goSpin(cowToCheck);
    }

    /**TODO: Implement into the actions system
     *
     * @param cowToCheck
     * @return
     */
    @Nullable
    private static ArrayList<GenericAction> decidePassiveActions(@NotNull Cow cowToCheck) {
        /*
         * Static (for now) stats based decisions.
         */
        if (cowToCheck.self.getDebt() <= 10 && cowToCheck.self.getSavings() > 30
                && (PlaygroundHandler.getMotion().getDefaultBuilding() == cowToCheck.getLivingSpace())) {
            PassiveActions.buyHouse(cowToCheck);
        }
        if (!cowToCheck.isLeader() && cowToCheck.self.getCompanionship() > 95 && cowToCheck.personality.meetsThresholds(Interests.LEADERSHIP)) {
            EstablishmentCreation.createFollowing(cowToCheck.getId() + " 's support group", null, cowToCheck);
            cowToCheck.setIsLeader(true);
        }
        return null;
    }
}