package metaEnvironment.Regioning;

import metaEnvironment.Playground;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class BinRegionHandler {

    public static ConcurrentHashMap<Integer, BinRegion> binRegionMap = new ConcurrentHashMap<>();
    private static ArrayList<BinRegion> activeRegions = new ArrayList<>();

    public static int newestRegionId = 0;

    public static ArrayList<BinRegion> getActiveRegions() {
        return activeRegions;
    }

    public static BinRegion createNewRegion() {
        BinRegion newRegion = new BinRegion(Integer.toString(newestRegionId));
        binRegionMap.put(newestRegionId, newRegion);
        newestRegionId++;
        Playground.playground.getChildren().add(newRegion);
        return newRegion;
    }
}