package cowParts.actionSystem.actionTypes;

import cowParts.creation.Cow;
import infrastructure.buildings.BuildingCreation;
import metaEnvironment.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import metaEnvironment.logging.EventLogger;
import terrain.TileHandler;

public class PassiveActions {

    public static void buyHouse(Cow cowToCheck) {
        //If there is space available
        if (TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.basicSmallBuilding), PlaygroundHandler.getMotion()) != null) {
            cowToCheck.setLivingSpace(BuildingCreation.createResidentialBuilding(
                    AssetLoading.basicSmallBuilding, LoadConfiguration.getBasicSmallDwelling(),
                    TileHandler.getRandRegionTile(TileHandler.getSize(AssetLoading.basicSmallBuilding), PlaygroundHandler.getMotion())
            ));
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "income", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "bills", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "taxes", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "savings", cowToCheck.self.getSavings());
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "debt", 100 - cowToCheck.self.getSavings());
            cowToCheck.self.setDebt(100);
            cowToCheck.self.setSavings(-100);

            if (cowToCheck.hasOffspring()) {
                for (int i = 0; i < cowToCheck.getOffspring().size(); i ++) {
                    cowToCheck.getOffspring().get(i).setLivingSpace(cowToCheck.getLivingSpace());
                }
                cowToCheck.getSpouse().setLivingSpace(cowToCheck.getLivingSpace());
            }
        }
    }

}
