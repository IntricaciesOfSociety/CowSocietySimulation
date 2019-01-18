package cowParts.cowMovement;

import buildings.SmallDwelling;
import cowParts.Cow;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.logging.EventLogger;
import terrain.Tile;

public class PassiveActions {

    private static void buyHouse(Cow cowToCheck) {
        //If there is space available
        if (Tile.getRandomNotFullTile(Tile.getSize(AssetLoading.basicSmallBuilding)) != null) {
            cowToCheck.setLivingSpace(new SmallDwelling(AssetLoading.basicSmallBuilding, LoadConfiguration.getBasicSmallDwelling(), Tile.getRandomNotFullTile(Tile.getSize(AssetLoading.basicSmallBuilding))));
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "income", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "bills", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "taxes", 0);
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "savings", cowToCheck.self.getSavings());
            EventLogger.createLoggedEvent(cowToCheck, "Bought a House", 1, "debt", 100 - cowToCheck.self.getSavings());
            cowToCheck.self.setDebt(100);
            cowToCheck.self.setSavings(-100);

            if (cowToCheck.hasOffspring()) {
                cowToCheck.getOffspring().setLivingSpace(cowToCheck.getLivingSpace());
                cowToCheck.getSpouse().setLivingSpace(cowToCheck.getLivingSpace());
            }
        }
    }

}
