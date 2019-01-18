package metaControl;

import org.ini4j.Ini;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 * Parses the configuration file and loads all variables into fields to be accessed by other classes.
 */
public class LoadConfiguration {

    //[SituationConfig]
    private static String primaryEra = "";
    private static int initialPopulation;
    private static int startingSize;

    //[Tiles]
    private static String basicTileName;

    //[Buildings]
    private static String basicSmallDwelling;
    private static String basicLargeDwelling;

    //[Resources]
    private static int initialSmallRocks;
    private static int initialSmallTrees;
    private static int initialWaterSources;
    private static int initialLargeRocks;
    private static int initialLargeTrees;

    /**
     * Loads the configuration file
     */
    static void loadConfigurationFile() {
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
        primaryEra = ini.get("SituationConfig", "era");
        initialPopulation = Integer.parseInt(ini.get("SituationConfig", "population"));
        startingSize = Integer.parseInt(ini.get("SituationConfig", "startingsize"));

        basicTileName = ini.get("Tiles", "basictile");

        basicSmallDwelling = ini.get("Buildings", "basicsmalldwelling");
        basicLargeDwelling = ini.get("Buildings", "basiclargedwelling");

        initialSmallRocks = Integer.parseInt(ini.get("Resources", "smallRocks"));
        initialSmallTrees = Integer.parseInt(ini.get("Resources", "smallTrees"));
        initialLargeRocks = Integer.parseInt(ini.get("Resources", "largeRocks"));
        initialLargeTrees = Integer.parseInt(ini.get("Resources", "largeTrees"));
        initialWaterSources = Integer.parseInt(ini.get("Resources", "watersources"));
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
    public static String getPrimaryEra() {
        return primaryEra;
    }

    @Contract(pure = true)
    public static String getBasicTileName() {
        return basicTileName;
    }

    @Contract(pure = true)
    public static String getBasicSmallDwelling() {
        return basicSmallDwelling;
    }

    @Contract(pure = true)
    public static String getBasicLargeDwelling() {
        return basicLargeDwelling;
    }

    @Contract(pure = true)
    public static int getStartingSize() {
        return startingSize;
    }

    public static int getInitialLargeRocks() {
        return initialLargeRocks;
    }

    public static int getInitialLargeTrees() {
        return initialLargeTrees;
    }
}
