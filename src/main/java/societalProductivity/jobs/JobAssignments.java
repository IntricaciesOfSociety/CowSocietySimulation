package societalProductivity.jobs;

import cowParts.creation.Cow;
import infrastructure.buildings.buildingTypes.GenericBuilding;
import metaControl.timeControl.currentEra.JobSprites;
import resourcesManagement.ResourcesHandler;
import resourcesManagement.resourceTypes.GenericResource;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WoodSource;

class JobAssignments {

    static Occupation assignCarpenter(Cow cowToAssignTo) {
        return new Occupation(cowToAssignTo, "Wood Constructing", "Carpenter", JobSprites.getConstructionWorkerSprite(),
                () -> cowToAssignTo.getRegionIn().getNextToConstruct(),
                () -> {
                    if ( !((GenericBuilding) cowToAssignTo.getDestination()).isConstructed() ) {
                        ((GenericBuilding) cowToAssignTo.getDestination()).contributeResource("wood", 5);
                        ((GenericBuilding) cowToAssignTo.getDestination()).contributeResource("power", 1);
                    }
                });
    }

    static Occupation assignMason(Cow cowToAssignTo) {
        return new Occupation(cowToAssignTo, "Rock Constructing", "Mason", JobSprites.getConstructionWorkerSprite(),
                () -> cowToAssignTo.getRegionIn().getNextToConstruct(),
                () -> {
                    if ( !((GenericBuilding) cowToAssignTo.getDestination()).isConstructed() ) {
                        ((GenericBuilding) cowToAssignTo.getDestination()).contributeResource("rock", 5);
                        ((GenericBuilding) cowToAssignTo.getDestination()).contributeResource("power", 1);
                    }
                });
    }

    static Occupation assignLumberjack(Cow cowToAssignTo) {
        return new Occupation(cowToAssignTo, "Chopping Wood", "Lumberjack", JobSprites.getLumberjackSprite(),
                () -> ResourcesHandler.getClosestWoodSource(cowToAssignTo),
                () -> {
                    if ( !((WoodSource) cowToAssignTo.getDestination()).isDestroyed() ) {
                        ResourcesHandler.modifyResource("wood", 5);
                        GenericResource.depleteResource((WoodSource) cowToAssignTo.getDestination(), 5);
                    }
                });
    }

    static Occupation assignMiner(Cow cowToAssignTo) {
        return new Occupation(cowToAssignTo, "Mining", "Miner", JobSprites.getMinerSprite(),
                () -> ResourcesHandler.getClosestRockSource(cowToAssignTo),
                () -> {
                    if ( !((RockSource) cowToAssignTo.getDestination()).isDestroyed() ) {
                        ResourcesHandler.modifyResource("rock", 5);
                        GenericResource.depleteResource((RockSource) cowToAssignTo.getDestination(), 5);
                    }
                });
    }
}