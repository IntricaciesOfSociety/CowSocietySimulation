package metaControl.metaEnvironment;

import org.ini4j.Ini;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Parses the configuration file and loads all variables into fields to be accessed by other classes.
 */
public class LoadConfiguration {

    //[Meta]
    private static boolean isFullscreen;
    private static int binRegionSize;

    //[SituationConfig]
    private static String primaryEra = "";
    private static int initialPopulation;

    private static boolean startGovernment;
    private static String startGovernmentType;

    //[Tiles]
    private static boolean worldSquare;
    private static int worldVRegions;
    private static int worldHRegions;
    private static String basicWorldTile;
    private static int mountainBiomes;
    private static int desertBiomes;

    //[Mines]
    private static boolean mineSquare;
    private static int mineHRegions;
    private static int mineVRegions;
    private static String basicMineTile;

    //[Buildings]
    private static String basicSmallDwelling;
    private static String basicLargeDwelling;
    private static String basicGroceryStore;
    private static int groceryStores;
    private static String basicMine;
    private static int numberOfMines;

    //[Resources]
    private static int initialSmallRocks;
    private static int initialSmallTrees;
    private static int initialWaterSources;
    private static int initialLargeRocks;
    private static int initialLargeTrees;
    private static int initialClay;
    private static int initialCopper;
    private static int initialIron;

    //[Cows]
    //[City]
    private static String cityName;

    /**
     * Loads the configuration file
     */
    public static void loadConfigurationFile() {
        Ini ini = null;
        try {
            ini = new Ini(new File("src/main/configuration.ini"));
        }
        catch (IOException e) {
            System.out.println("Cannot find src/main/configuration.ini " + e);
        }
        setConfiguration(ini);
    }

    /**
     * Reads the configuration file and parses values into variables.
     * @param ini The init file to read
     */
    private static void setConfiguration(@NotNull Ini ini) {
        isFullscreen = Boolean.parseBoolean(ini.get("Meta", "fullscreen"));
        binRegionSize = Integer.parseInt(ini.get("Meta", "binRegionSize"));

        primaryEra = ini.get("SituationConfig", "era");
        initialPopulation = Integer.parseInt(ini.get("SituationConfig", "population"));
        startGovernment = Boolean.parseBoolean(ini.get("SituationConfig", "startGovernment"));
        startGovernmentType = ini.get("SituationConfig", "startGovernmentType");

        worldSquare = Boolean.parseBoolean(ini.get("Overworld", "squareRegionSet"));
        worldHRegions = Integer.parseInt(ini.get("Overworld", "horizontalRegions"));
        worldVRegions = Integer.parseInt(ini.get("Overworld", "verticalRegions"));
        basicWorldTile = ini.get("Overworld", "basictile");
        mountainBiomes = Integer.parseInt(ini.get("Overworld", "mountainBiomes"));
        desertBiomes = Integer.parseInt(ini.get("Overworld", "desertBiomes"));

        mineSquare =  Boolean.parseBoolean(ini.get("Mines", "squareRegionSet"));
        mineHRegions = Integer.parseInt(ini.get("Mines", "horizontalRegions"));
        mineVRegions = Integer.parseInt(ini.get("Mines", "verticalRegions"));
        basicMineTile = ini.get("Mines", "basictile");

        basicSmallDwelling = ini.get("Buildings", "basicsmalldwelling");
        basicLargeDwelling = ini.get("Buildings", "basiclargedwelling");
        basicGroceryStore = ini.get("Buildings", "basicGroceryStoreBuilding");
        groceryStores = Integer.parseInt(ini.get("Buildings", "numberOfGroceryStores"));
        basicMine = ini.get("Buildings", "basicMineBuliding");
        numberOfMines = Integer.parseInt(ini.get("Buildings", "numberOfMines"));

        initialSmallRocks = Integer.parseInt(ini.get("Resources", "smallRocks"));
        initialSmallTrees = Integer.parseInt(ini.get("Resources", "smallTrees"));
        initialLargeRocks = Integer.parseInt(ini.get("Resources", "largeRocks"));
        initialLargeTrees = Integer.parseInt(ini.get("Resources", "largeTrees"));
        initialWaterSources = Integer.parseInt(ini.get("Resources", "watersources"));
        initialClay = Integer.parseInt(ini.get("Resources", "clay"));
        initialCopper = Integer.parseInt(ini.get("Resources", "copper"));
        initialIron = Integer.parseInt(ini.get("Resources", "iron"));

        cityName = ini.get("City", "cityName");
    }

    @Contract(pure = true)
    public static int getInitialPopulation() {
        return initialPopulation;
    }

    @Contract(pure = true)
    public static int getInitialSmallRocks() {
        return initialSmallRocks;
    }

    @Contract(pure = true)
    public static int getInitialSmallTrees() {
        return initialSmallTrees;
    }

    @Contract(pure = true)
    public static int getInitialWaterSources() {
        return initialWaterSources;
    }

    @Contract(pure = true)
    public static int getInitialClay() {
        return initialClay;
    }

    @Contract(pure = true)
    public static int getInitialCopper() {
        return initialCopper;
    }

    @Contract(pure = true)
    public static int getInitialIron() {
        return initialIron;
    }

    @Contract(pure = true)
    public static String getPrimaryEra() {
        return primaryEra;
    }

    @Contract(pure = true)
    static String getBasicWorldTile() {
        return basicWorldTile;
    }

    @Contract(pure = true)
    public static String getBasicSmallDwelling() {
        return basicSmallDwelling;
    }

    @Contract(pure = true)
    public static String getBasicLargeDwelling() {
        return basicLargeDwelling;
    }

    public static int getInitialLargeRocks() {
        return initialLargeRocks;
    }

    public static int getInitialLargeTrees() {
        return initialLargeTrees;
    }

    public static int getMountainBiomes() {
        return mountainBiomes;
    }

    public static int getDesertBiomes() {
        return desertBiomes;
    }

    public static String getBasicGroceryStore() {
        return basicGroceryStore;
    }

    public static int getGroceryStores() {
        return groceryStores;
    }

    public static String getBasicMine() {
        return basicMine;
    }

    public static String getCityName() {
        return cityName;
    }

    public static boolean getFullscreen() {
        return isFullscreen;
    }

    public static int getBinRegionSize() {
        return binRegionSize;
    }

    public static boolean isWorldSquare() {
        return worldSquare;
    }

    public static int getWorldVRegions() {
        return worldVRegions;
    }

    public static int getWorldHRegions() {
        return worldHRegions;
    }

    public static boolean isMineSquare() {
        return mineSquare;
    }

    public static int getMineHRegions() {
        return mineHRegions;
    }

    public static int getMineVRegions() {
        return mineVRegions;
    }

    public static String getBasicMineTile() {
        return basicMineTile;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static boolean isStartGovernment() {
        return startGovernment;
    }

    public static String getStartGovernmentType() {
        return startGovernmentType;
    }
}
