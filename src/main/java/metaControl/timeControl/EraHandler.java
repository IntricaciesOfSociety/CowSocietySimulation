package metaControl.timeControl;

import metaControl.timeControl.currentEra.JobSprites;

public class EraHandler {

    private static Era currentEra;

    //TODO: Implement
    public static void loadEra(String primaryEra) {
        JobSprites.loadEraSprites(new Era());
    }
}
