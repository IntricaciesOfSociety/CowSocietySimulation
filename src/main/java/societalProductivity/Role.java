package societalProductivity;

import infrastructure.buildingTypes.GenericBuilding;
import cowParts.Cow;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.resourceTypes.GenericResource;
import resourcesManagement.ResourcesHandler;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WoodSource;
import societalProductivity.government.Government;

import java.util.Random;

/**
 * TODO: Implement me!
 * Assigns the job to a cow. If no new job is assigned, the default job is to spin.
 */
public class Role {

    private Random random = new Random();

    /**
     * Assigns a role based off of random chance.
     */
    public Role(Cow cowToCheck) {
        if (random.nextInt(100) < 10)
            cowToCheck.setJob("Carpenter");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("Mason");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("Lumberjack");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("Miner");

        if (cowToCheck.getJob().equals("Spinning"))
            new Role(cowToCheck);

        if (!Government.hasLeader())
            Government.setLeader(cowToCheck);
    }

    @Nullable
    public static Object getRoleDestination(@NotNull Cow cowToCheck) {
        switch (cowToCheck.getJob()) {
            case "Carpenter":
                cowToCheck.currentAction = "Wood Constructing";
                cowToCheck.setImage(AssetLoading.loadCowRole("ConstructionCow"));
                return cowToCheck.getRegionIn().getNextToConstruct();
            case "Mason":
                cowToCheck.currentAction = "Rock Constructing";
                cowToCheck.setImage(AssetLoading.loadCowRole("ConstructionCow"));
                return cowToCheck.getRegionIn().getNextToConstruct();
            case "Lumberjack":
                cowToCheck.currentAction = "Chopping Wood";
                cowToCheck.setImage(AssetLoading.loadCowRole("LumberJackCow"));
                return ResourcesHandler.getClosestWoodSource(cowToCheck, cowToCheck.getRegionIn().getBinId());
            case "Miner":
                cowToCheck.currentAction = "Mining Rock";
                cowToCheck.setImage(AssetLoading.loadCowRole("MinerCow"));
                return ResourcesHandler.getClosestRockSource(cowToCheck, cowToCheck.getRegionIn().getBinId());
        }
        return null;
    }

    public static void getRoleCompletionBehavior(@NotNull Cow cowToCheck) {
        switch (cowToCheck.getJob()) {
            case "Carpenter":
                if ( !((GenericBuilding) cowToCheck.getDestination()).isConstructed() ) {
                    ((GenericBuilding) cowToCheck.getDestination()).contributeResource("wood", 5);
                    ((GenericBuilding) cowToCheck.getDestination()).contributeResource("power", 1);
                }
                break;
            case "Mason":
                if ( !((GenericBuilding) cowToCheck.getDestination()).isConstructed() ) {
                    ((GenericBuilding) cowToCheck.getDestination()).contributeResource("rock", 5);
                    ((GenericBuilding) cowToCheck.getDestination()).contributeResource("power", 1);
                }
                break;
            case "Lumberjack":
                if ( !((WoodSource) cowToCheck.getDestination()).isDestroyed() ) {
                    ResourcesHandler.modifyResource("wood", 5);
                    GenericResource.depleteResource((WoodSource) cowToCheck.getDestination(), 5);
                }
                break;
            case "Miner":
                if ( !((RockSource) cowToCheck.getDestination()).isDestroyed() ) {
                    ResourcesHandler.modifyResource("rock", 5);
                    GenericResource.depleteResource((RockSource) cowToCheck.getDestination(), 5);
                }
                break;
        }
    }
}