package cowParts.cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import cowParts.BirthEvent;
import cowParts.Cow;
import cowParts.CowHandler;
import cowParts.cowAI.NaturalSelection;
import javafx.geometry.Point2D;
import metaControl.SimState;
import metaEnvironment.logging.EventLogger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.WaterSource;
import societalProductivity.Role;
import societalProductivity.government.Economy;
import societalProductivity.government.Government;
import userInterface.StaticUI;

import java.util.Random;

interface Finish {
    void executeFinish();
}

class ActiveActions extends Action {

    private static Random random = new Random();

    @NotNull
    @Contract("_, _, _, _ -> new")
    private static Movement returnAction(@NotNull Cow cowToMakeMovement, Object destination, String currentAction, Finish finishBehavior) {
        if (currentAction != null)
            cowToMakeMovement.currentAction = currentAction;

        StaticUI.updateActionText();
        return (new Movement(
                () -> destination,
                finishBehavior,
                cowToMakeMovement)
        );
    }

    @Nullable
    static Movement getVitalAction(@NotNull Cow cowToCheck) {
        int lowestVitalValue = 11;

        if (cowToCheck.self.getThirst() < lowestVitalValue)
            lowestVitalValue = cowToCheck.self.getThirst();
        else if (cowToCheck.self.getHunger() < lowestVitalValue)
            lowestVitalValue = cowToCheck.self.getHunger();

        if (cowToCheck.self.getThirst() == lowestVitalValue)
            return getWater(cowToCheck);
        else if (cowToCheck.self.getHunger() == lowestVitalValue)
            return getFood(cowToCheck);

        return null;
    }

    @NotNull
    private static Movement getWater(Cow cowToCheck) {
        return returnAction(cowToCheck, WaterSource.getClosestResource(cowToCheck), "Getting Water",
            () -> {
                EventLogger.createLoggedEvent(cowToCheck, "Getting water", 0, "thirst", 100 - cowToCheck.self.getThirst());
                cowToCheck.self.setThirst(100);
                Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
            }
        );
    }

    @NotNull
    private static Movement getFood(Cow cowToCheck) {
        return returnAction(cowToCheck, BuildingHandler.getClosestGroceryStore(cowToCheck), "Getting Food",
            () -> {
                EventLogger.createLoggedEvent(cowToCheck, "Getting food", 0, "hunger", 100 - cowToCheck.self.getHunger());
                cowToCheck.self.setHunger(100);
                Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
            }
        );
    }

    @NotNull
    static Movement goWork(Cow cowToCheck) {
        return returnAction(cowToCheck, Role.getRoleDestination(cowToCheck), null,
            () -> {
                Role.getRoleCompletionBehavior(cowToCheck);

                EventLogger.createLoggedEvent(cowToCheck, "Working", 0, "sleepiness", -50);
                cowToCheck.self.setSleepiness(-50);
                Economy.giveMoney(cowToCheck, 10);

                Movement.pauseMovement( ((int) (SimState.getDeltaTime() * 5000)), cowToCheck);
            }
        );
    }

    @NotNull
    static Movement goVote(@NotNull Cow cowToCheck) {
        cowToCheck.setHasVoted(true);
        return returnAction(cowToCheck, BuildingHandler.getClosestVotingArea(cowToCheck), "Voting",
            () -> {
                Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());
                Government.vote(CowHandler.liveCowList.get(random.nextInt(CowHandler.liveCowList.size())));

                EventLogger.createLoggedEvent(cowToCheck, "Voting", 0, "trust", 10);
                cowToCheck.self.setTrust(10);

                Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
            }
        );
    }

    @NotNull
    static Movement goHome(@NotNull Cow cowToCheck) {
        return returnAction(cowToCheck, cowToCheck.getLivingSpace(), "Going Home",
            () -> {
                Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());

                EventLogger.createLoggedEvent(cowToCheck, "Going home", 0, "sleepiness", 100 - cowToCheck.self.getSleepiness());
                cowToCheck.self.setSleepiness(100);

                Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
            }
        );
    }

    @NotNull
    static Movement createChild(@NotNull Cow cowToCheck) {
        return returnAction(cowToCheck, CowHandler.findHalfwayPoint(cowToCheck, NaturalSelection.getMostFitAndFertile(cowToCheck)), "Going Home",
            () -> {
                BirthEvent.createChild(cowToCheck, NaturalSelection.getMostFitAndFertile(cowToCheck));

                EventLogger.createLoggedEvent(cowToCheck, "Procreating", 0, "companionship", 10);
                EventLogger.createLoggedEvent(cowToCheck, "Procreating", 0, "sleepiness", -10);
                cowToCheck.self.setCompanionship(10);
                cowToCheck.self.setSleepiness(-10);

                Movement.pauseMovement((int) (SimState.getDeltaTime() * 1000), cowToCheck);
            }
        );
    }

    @Nullable
    static Movement goSpin(@NotNull Cow cowToCheck) {
        cowToCheck.currentAction = "Spinning";
        cowToCheck.setRotate(random.nextInt(360 + 1 + 360) - 360);
        cowToCheck.alreadyMoving = false;
        StaticUI.updateActionText();
        return null;
    }
}