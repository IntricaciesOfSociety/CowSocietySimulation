package metaEnvironment.Regioning;

import metaEnvironment.LoadConfiguration;
import resourcesManagement.resourceTypes.RockSource;
import resourcesManagement.resourceTypes.WaterSource;
import resourcesManagement.resourceTypes.WoodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BinRegionHandler {

    public static ConcurrentHashMap<Integer, BinRegion> binRegionMap = new ConcurrentHashMap<>();
    private static ArrayList<BinRegion> activeRegions = new ArrayList<>();

    public static int newestRegionId = 0;

    public static ArrayList<BinRegion> getActiveRegions() {
        return activeRegions;
    }

    public static BinRegion createNewRegion() {
        BinRegion newRegion = new BinRegion(newestRegionId);
        binRegionMap.put(newestRegionId, newRegion);
        newestRegionId++;
        return newRegion;
    }

    public static List getAdjacentRegionWaterSources(int regionId) {
        ArrayList<Integer> regionIds = getAdjacentRegionIds(regionId);
        List<WaterSource> sources = new ArrayList<>();

        for (Integer searchIds : regionIds)
            if (searchIds != null)
                sources.addAll(binRegionMap.get(searchIds).getAllWaterSources());
        return sources;
    }

    public static List getAdjacentRegionWoodSources(int regionId) {
        ArrayList<Integer> regionIds = getAdjacentRegionIds(regionId);
        List<WoodSource> sources = new ArrayList<>();

        for (Integer searchIds : regionIds) {
            if (searchIds != null)
                sources.addAll(binRegionMap.get(searchIds).getAllWoodSources());
        }
        return sources;
    }

    public static List getAdjacentRegionRockSources(int regionId) {
        ArrayList<Integer> regionIds = getAdjacentRegionIds(regionId);
        List<RockSource> sources = new ArrayList<>();

        for (Integer searchIds : regionIds)
            if (searchIds != null)
                sources.addAll(binRegionMap.get(searchIds).getAllRockSources());
        return sources;
    }

    private static ArrayList<Integer> getAdjacentRegionIds(int regionId) {
        ArrayList<Integer> regionIds = new ArrayList<>();

        // given, above, below
        regionIds.add(regionId);
        regionIds.add((regionId >= LoadConfiguration.getHorizontalRegions()) ? regionId - LoadConfiguration.getHorizontalRegions() : null);
        regionIds.add((regionId <= (newestRegionId - 1) - LoadConfiguration.getHorizontalRegions()) ? regionId + LoadConfiguration.getHorizontalRegions() : null);

        // right, left
        regionIds.add((regionId % LoadConfiguration.getHorizontalRegions()) != (LoadConfiguration.getHorizontalRegions() - 1) ? regionId + 1 : null);
        regionIds.add((regionId % LoadConfiguration.getHorizontalRegions() != 0) ? regionId - 1 : null);

        // diagUpLeft diagUpRight
        regionIds.add(((regionIds.get(4) != null) && regionId >= LoadConfiguration.getHorizontalRegions()) ? ((regionId - 1) - LoadConfiguration.getHorizontalRegions()) : null);
        regionIds.add(((regionIds.get(3) != null) && (regionId >= LoadConfiguration.getHorizontalRegions())) ? ((regionId + 1) - LoadConfiguration.getHorizontalRegions()) : null);

        // diagDownLeft diagDownRight
        regionIds.add(((regionIds.get(4) != null) && (regionId <= ((newestRegionId - 1) - LoadConfiguration.getHorizontalRegions())) ? ((regionId - 1) + LoadConfiguration.getHorizontalRegions()) : null));
        regionIds.add(((regionIds.get(3) != null) && (regionId <= ((newestRegionId - 1) - LoadConfiguration.getHorizontalRegions())) ? ((regionId + 1) + LoadConfiguration.getHorizontalRegions()) : null));

        return regionIds;
    }
}