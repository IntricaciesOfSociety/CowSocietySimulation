package societalProductivity.cityPlanning;

import infrastructure.BuildingHandler;
import metaEnvironment.LoadConfiguration;
import metaControl.main.SimState;
import metaControl.Time;
import org.jetbrains.annotations.Contract;
import org.slf4j.MDC;

import java.util.ArrayList;

public class CityControl {

    private static String cityName = LoadConfiguration.getCityName();

    private static ArrayList<CitySector> allSectors = new ArrayList<>();
    public static void init() {
        cityName = LoadConfiguration.getCityName();
        createCityLog();

        allSectors.add(new CitySector(BuildingHandler.getDefaultBuilding()));
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
