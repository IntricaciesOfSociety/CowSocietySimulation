package cowParts.cowMovement;

import buildings.SmallBuilding;
import cowParts.Cow;
import metaControl.LoadConfiguration;
import metaEnvironment.AssetLoading;
import metaEnvironment.logging.EventLogger;
import terrain.Tile;

class PassiveActions {

    static void buyHouse(Cow cowToCheck) {
        //If there is space available
        if (Tile.getRandomNotFullTile(Tile.getSize(AssetLoading.basicSmallBuilding)) != null) {
            cowToCheck.setLivingSpace(new SmallBuilding(AssetLoading.basicSmallBuilding, LoadConfiguration.getBasicSmallDwelling(), Tile.getRandomNotFullTile(Tile.getSize(AssetLoading.basicSmallBuilding))));
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
