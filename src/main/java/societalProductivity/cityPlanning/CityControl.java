package societalProductivity.cityPlanning;

import metaControl.LoadConfiguration;
import metaControl.SimState;
import metaControl.Time;
import org.jetbrains.annotations.Contract;
import org.slf4j.MDC;

public class CityControl {

    private static String cityName = LoadConfiguration.getCityName();

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
