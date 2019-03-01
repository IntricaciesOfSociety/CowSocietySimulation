package metaEnvironment.Regioning;

import java.util.ArrayList;

public class BinRegionHandler {

    private static ArrayList<BinRegion> activeRegions = new ArrayList<>();

    public static ArrayList<BinRegion> getActiveRegions() {
        return activeRegions;
    }
}
