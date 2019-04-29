package cowParts.actionSystem.actionTypes;

import cowParts.CowHandler;
import cowParts.actionSystem.action.EndAction;
import cowParts.actionSystem.action.GenericAction;
import cowParts.cowAI.NaturalSelection;
import cowParts.creation.BirthEvent;
import cowParts.creation.Cow;
import infrastructure.buildings.BuildingHandler;
import infrastructure.buildings.buildingTypes.GenericBuilding;
import metaControl.main.SimState;
import metaControl.metaEnvironment.logging.EventLogger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.ResourcesHandler;
import societyProduction.government.Economy;
import metaControl.menus.userInterface.playgroundUI.StaticUI;

import java.util.Random;

public class ActiveActions {

    private static Random random = new Random();

    @Contract("_, _, _, _ -> new")
    private static GenericAction returnAction(@NotNull Cow cowToMakeMovement, Object destination, String currentAction, EndAction endBehavior) {
        if (currentAction != null)
            cowToMakeMovement.setCurrentBehavior(currentAction);

        StaticUI.updateActionText();

        cowToMakeMovement.setDestination(destination);

        if (destination != null)
            return new GenericAction(() -> destination, () -> new Movement(cowToMakeMovement, endBehavior).getCompleteMovement().play());
        else
            return null;
    }

    public static GenericAction getVitalAction(@NotNull Cow cowToCheck) {
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

    private static GenericAction getWater(Cow cowToCheck) {
        return returnAction(cowToCheck, ResourcesHandler.getClosestWaterSource(cowToCheck), "Getting Water",
            () -> {
                EventLogger.createLoggedEvent(cowToCheck, "Getting water", 0, "thirst", 100 - cowToCheck.self.getThirst());
                cowToCheck.self.setThirst(100);
                Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
            }
        );
    }

    private static GenericAction getFood(Cow cowToCheck) {
        return returnAction(cowToCheck, BuildingHandler.getClosestGroceryStore(cowToCheck), "Getting Food",
            () -> {
                EventLogger.createLoggedEvent(cowToCheck, "Getting food", 0, "hunger", 100 - cowToCheck.self.getHunger());
                cowToCheck.self.setHunger(100);
                Movement.pauseMovement(((int) (SimState.getDeltaTime() * 1000)), cowToCheck);
            }
        );
    }

    public static GenericAction goWork(@NotNull Cow cowToCheck) {
        cowToCheck.setImage(cowToCheck.getJob().getJobSprite());
        return returnAction(cowToCheck, cowToCheck.getJob().generateJobDestination(), cowToCheck.getJob().getJobActionText(),
            () -> {
                cowToCheck.getJob().completeJob();

                EventLogger.createLoggedEvent(cowToCheck, "Working", 0, "sleepiness", -50);
                cowToCheck.self.setSleepiness(-50);
                Economy.giveMoney(cowToCheck, 10);

                Movement.pauseMovement( ((int) (SimState.getDeltaTime() * 50000)), cowToCheck);
            }
        );
    }

    public static GenericAction goVote(@NotNull Cow cowToCheck) {
        cowToCheck.setHasVoted(true);
        return returnAction(cowToCheck, BuildingHandler.getClosestVotingArea(cowToCheck), "Voting",
            () -> {
                BuildingHandler.enterBuilding(cowToCheck, (GenericBuilding) cowToCheck.getDestination());
                //GovernmentExecution.vote(CowHandler.liveCowList.get(random.nextInt(CowHandler.liveCowList.size())));

                EventLogger.createLoggedEvent(cowToCheck, "Voting", 0, "trust", 10);
                cowToCheck.self.setTrust(10);

                Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
            }
        );
    }

    @Contract("_ -> new")
    public static GenericAction goHome(@NotNull Cow cowToCheck) {
        return returnAction(cowToCheck, cowToCheck.getLivingSpace(), "Going Home",
            () -> {
                BuildingHandler.enterBuilding(cowToCheck, (GenericBuilding) cowToCheck.getDestination());

                EventLogger.createLoggedEvent(cowToCheck, "Going home", 0, "sleepiness", 100 - cowToCheck.self.getSleepiness());
                cowToCheck.self.setSleepiness(100);

                Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
            }
        );
    }

    //TODO: Implement
    @Contract("_ -> new")
    public static GenericAction converse(@NotNull Cow cowToCheck) {
        return returnAction(cowToCheck, cowToCheck.getLivingSpace(), "Going Home",
                () -> {
                    BuildingHandler.enterBuilding(cowToCheck, (GenericBuilding) cowToCheck.getDestination());

                    EventLogger.createLoggedEvent(cowToCheck, "Going home", 0, "sleepiness", 100 - cowToCheck.self.getSleepiness());
                    cowToCheck.self.setSleepiness(100);

                    Movement.pauseMovement((int) (SimState.getDeltaTime() * 5000), cowToCheck);
                }
        );
    }

    @Nullable
    public static GenericAction createChild(@NotNull Cow cowToCheck) {
        if (NaturalSelection.getMostFitAndFertile(cowToCheck) != null) {
            return returnAction(cowToCheck, CowHandler.findHalfwayPoint(cowToCheck, NaturalSelection.getMostFitAndFertile(cowToCheck)), "Creating Offspring",
                    () -> {
                        if (NaturalSelection.getMostFitAndFertile(cowToCheck) != null) {
                            BirthEvent.createChild(cowToCheck, NaturalSelection.getMostFitAndFertile(cowToCheck));

                            EventLogger.createLoggedEvent(cowToCheck, "Procreating", 0, "companionship", 10);
                            EventLogger.createLoggedEvent(cowToCheck, "Procreating", 0, "sleepiness", -10);
                            cowToCheck.self.setCompanionship(10);
                            cowToCheck.self.setSleepiness(-10);

                            Movement.pauseMovement((int) (SimState.getDeltaTime() * 1000), cowToCheck);
                        }
                    }
            );
        }
        else
            return null;
    }

    @Nullable
    public static GenericAction goSpin(@NotNull Cow cowToCheck) {
        cowToCheck.setCurrentBehavior("Spinning");
        cowToCheck.setRotate(random.nextInt(360 + 1 + 360) - 360);
        cowToCheck.self.setBoredom(1);
        EventLogger.createLoggedEvent(cowToCheck, "Spinning", 0, "boredom", 1);
        cowToCheck.alreadyMoving = false;
        StaticUI.updateActionText();
        return null;
    }
}