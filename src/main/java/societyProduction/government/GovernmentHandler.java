package societyProduction.government;

import infrastructure.terrain.Tile;
import metaControl.metaEnvironment.Regioning.regionContainers.PlaygroundHandler;

public class GovernmentHandler {

    public static boolean checkJurisdiction(Tile tileToCheck) {
        return true;
    }

    /**
     * Change to getting government default building
     * @return
     */
    public static Tile getDefaultBuilding() {
        return PlaygroundHandler.getMotion().getDefaultBuilding();
    }
}