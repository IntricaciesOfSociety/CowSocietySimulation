package societyProduction.urbanPlanning;

import metaControl.metaEnvironment.LoadConfiguration;
import metaControl.main.SimState;
import metaControl.timeControl.Time;
import org.jetbrains.annotations.Contract;
import org.slf4j.MDC;

public class CivicControl {

    private static String cityName;

    public static void init() {
        cityName = LoadConfiguration.getCityName();
        createCityLog();
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