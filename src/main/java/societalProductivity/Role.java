package societalProductivity;

import buildings.Building;
import buildings.BuildingHandler;
import cowParts.Cow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import resourcesManagement.Resource;
import resourcesManagement.ResourcesHandler;
import resourcesManagement.RockSource;
import resourcesManagement.WoodSource;
import societalProductivity.government.Government;

import java.util.Random;

/**
 * TODO: Implement me!
 * Assigns the job to a cow. If no new job is assigned, the default job is to spin.
 */
public class Role {

    Random random = new Random();

    /**
     * Assigns a role based off of random chance.
     */
    public Role(Cow cowToCheck) {
        if (random.nextInt(100) < 10)
            cowToCheck.setJob("woodworking");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("choppingWood");
        else if (random.nextInt(100) < 10)
            cowToCheck.setJob("miningRock");

        if (cowToCheck.getJob().equals("spinning"))
            new Role(cowToCheck);

        if (!Government.hasLeader())
            Government.setLeader(cowToCheck);
    }

    @Nullable
    public static Object getRoleDestination(@NotNull Cow cowToCheck) {
        switch (cowToCheck.getJob()) {
            case "woodworking":
                cowToCheck.currentAction = "Wood Constructing";
                return BuildingHandler.nextHouseToConstruct();
            case "choppingWood":
                cowToCheck.currentAction = "Chopping Wood";
                return WoodSource.getClosestResource(cowToCheck);
            case "miningRock":
                cowToCheck.currentAction = "Mining Rock";
                return RockSource.getClosestResource(cowToCheck);
        }
        return null;
    }


    public static void getRoleCompletionBehavior(@NotNull Cow cowToCheck) {
        switch (cowToCheck.getJob()) {
            case "woodworking":
                if ( !((Building) cowToCheck.getDestination()).isConstructed() ) {
                    ((Building) cowToCheck.getDestination()).contributeResource("wood", 5);
                    ((Building) cowToCheck.getDestination()).contributeResource("power", 1);
                }
                break;
            case "choppingWood":
                if ( !((WoodSource) cowToCheck.getDestination()).isDestroyed() ) {
                    ResourcesHandler.modifyResource("wood", 5);
                    Resource.depleteResource((WoodSource) cowToCheck.getDestination(), 5);
                }
                break;
            case "miningRock":
                if ( !((RockSource) cowToCheck.getDestination()).isDestroyed() ) {
                    ResourcesHandler.modifyResource("rock", 5);
                    Resource.depleteResource((RockSource) cowToCheck.getDestination(), 5);
                }
                break;
        }
    }
}
