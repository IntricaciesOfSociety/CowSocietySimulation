package cowParts.actionSystem.actionTypes;

import cowParts.creation.Cow;
import infrastructure.buildings.BuildingCreation;
import metaControl.metaEnvironment.LoadConfiguration;
import metaControl.metaEnvironment.AssetLoading;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;
import metaControl.metaEnvironment.logging.EventLogger;
import infrastructure.terrain.TileHandler;
import societyProduction.government.GovernmentHandler;

public class PassiveActions {

    public static void buyHouse(Cow cowToCheck) {
        //If there is space available
        if (TileHandler.getRandOuterProxTile(TileHandler.getSize(AssetLoading.basicSmallBuilding), GovernmentHandler.getDefaultBuilding()) != null) {
            cowToCheck.setLivingSpace(BuildingCreation.createResidentialBuilding(
                    AssetLoading.basicSmallBuilding, LoadConfiguration.getBasicSmallDwelling(),
                    TileHandler.getRandOuterProxTile(TileHandler.getSize(AssetLoading.basicSmallBuilding), GovernmentHandler.getDefaultBuilding())
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
