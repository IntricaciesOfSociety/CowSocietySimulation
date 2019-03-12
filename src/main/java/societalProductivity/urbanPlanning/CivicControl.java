package societalProductivity.urbanPlanning;

import metaEnvironment.LoadConfiguration;
import metaControl.main.SimState;
import metaControl.timeControl.Time;
import org.jetbrains.annotations.Contract;
import org.slf4j.MDC;
import societalProductivity.urbanPlanning.zoning.CivicSector;

import java.util.ArrayList;

public class CivicControl {

    private static String cityName = LoadConfiguration.getCityName();

    private static ArrayList<CivicSector> allSectors = new ArrayList<>();
    public static void init() {
        cityName = LoadConfiguration.getCityName();
        createCityLog();

        //allSectors.add(new CivicSector(BuildingHandler.get));
    }

    private static void createCityLog() {
        MDC.put("logName", cityName);
        SimState.logger.info(Time.getTime() + " " + cityName + " created!");
    }

    @Contract(pure = true)
    public static String getCityName() {
        return cityName;
    }
}
