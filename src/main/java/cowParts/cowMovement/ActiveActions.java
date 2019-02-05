package cowParts.cowMovement;

import buildings.Building;
import buildings.BuildingHandler;
import cowParts.Cow;
import cowParts.CowHandler;
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

    private static Finish finishBehavior;
    private static Object destination;
    private static Cow cowToMakeMovement;

    private static Random random = new Random();

    @NotNull
    static Movement getWater(Cow cowToCheck) {
        destination = WaterSource.getClosestResource(cowToCheck);
        cowToCheck.currentAction = "Getting Water";

        finishBehavior = () -> {
            EventLogger.createLoggedEvent(cowToCheck, "Getting water", 0, "thirst", 100 - cowToCheck.self.getThirst());
            cowToCheck.self.setThirst(100);
            Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
        };
        cowToMakeMovement = cowToCheck;
        StaticUI.updateActionText();
        return createMovement();
    }

    public static Movement getFood(Cow cowToCheck) {
        destination = BuildingHandler.getClosestGroceryStore(cowToCheck);
        cowToCheck.currentAction = "Getting Food";

        finishBehavior = () -> {
            EventLogger.createLoggedEvent(cowToCheck, "Getting food", 0, "hunger", 100 - cowToCheck.self.getHunger());
            cowToCheck.self.setHunger(100);
            Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
        };
        cowToMakeMovement = cowToCheck;
        StaticUI.updateActionText();
        return createMovement();
    }

    @NotNull
    static Movement goWork(Cow cowToCheck) {
        destination = Role.getRoleDestination(cowToCheck);

        finishBehavior = () -> {
            Role.getRoleCompletionBehavior(cowToCheck);

            EventLogger.createLoggedEvent(cowToCheck, "Working", 0, "sleepiness", -50);
            cowToCheck.self.setSleepiness(-50);
            Economy.giveMoney(cowToCheck, 10);

            Movement.pauseMovement( ((int) (SimState.getDeltaTime() * 5000)), cowToCheck);
        };
        cowToMakeMovement = cowToCheck;
        StaticUI.updateActionText();
        return createMovement();
    }

    @NotNull
    static Movement goVote(@NotNull Cow cowToCheck) {
        cowToCheck.setHasVoted(true);
        destination = BuildingHandler.getClosestVotingArea(cowToCheck);
        cowToCheck.currentAction = "Voting";

        finishBehavior = () -> {
            Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());
            Government.vote(CowHandler.liveCowList.get(random.nextInt(CowHandler.liveCowList.size())));

            EventLogger.createLoggedEvent(cowToCheck, "Voting", 0, "trust", 10);
            cowToCheck.self.setTrust(10);

            Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
        };
        cowToMakeMovement = cowToCheck;
        StaticUI.updateActionText();
        return createMovement();
    }

    @NotNull
    static Movement goHome(@NotNull Cow cowToCheck) {
        destination = cowToCheck.getLivingSpace();
        cowToCheck.currentAction = "Going Home";

        finishBehavior = () -> {
            Building.enterBuilding(cowToCheck, (Building) cowToCheck.getDestination());

            EventLogger.createLoggedEvent(cowToCheck, "Going home", 0, "sleepiness", 100 - cowToCheck.self.getSleepiness());
            cowToCheck.self.setSleepiness(100);

            Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
        };
        cowToMakeMovement = cowToCheck;
        StaticUI.updateActionText();
        return createMovement();
    }

    @Nullable
    static Movement goSpin(@NotNull Cow cowToCheck) {
        cowToCheck.currentAction = "Spinning";
        cowToCheck.setRotate(random.nextInt(360 + 1 + 360) - 360);
        cowToCheck.alreadyMoving = false;
        StaticUI.updateActionText();
        return null;
    }

    @NotNull
    @Contract(" -> new")
    private static Movement createMovement() {
        return (new Movement(
                () -> destination,
                finishBehavior,
                cowToMakeMovement));
    }
}