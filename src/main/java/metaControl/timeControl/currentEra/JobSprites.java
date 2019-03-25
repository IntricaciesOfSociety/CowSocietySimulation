package metaControl.timeControl.currentEra;

import javafx.scene.image.Image;
import metaControl.timeControl.Era;
import metaEnvironment.AssetLoading;

public class JobSprites {

    //Unskilled labor
    private static Image contructionWorker;
    private static Image miner;
    private static Image lumberjack;
    private static Image cropFarmer;

    //Skilled labor
    private static Image primaryGovernmentOfficial;
    private static Image secondaryGovernmentOfficial;

    //TODO: Remove temp. Implement read from file
    public static void loadEraSprites(Era eraToLoadSprites) {
        contructionWorker = AssetLoading.loadCowRole("ConstructionCow");
        miner = AssetLoading.loadCowRole("MinerCow");
        lumberjack = AssetLoading.loadCowRole("LumberJackCow");
    }

    public static Image getConstructionWorkerSprite() {
        return contructionWorker;
    }

    public static Image getMinerSprite() {
        return miner;
    }

    public static Image getLumberjackSprite() {
        return lumberjack;
    }
}
