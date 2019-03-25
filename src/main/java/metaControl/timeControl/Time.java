package metaControl.timeControl;

import cowParts.Cow;
import cowParts.CowHandler;
import cowParts.cowAI.NaturalSelection;
import javafx.scene.effect.ColorAdjust;
import metaEnvironment.LoadConfiguration;
import org.jetbrains.annotations.Contract;
import resourcesManagement.resourceTypes.WoodSource;
import societalProductivity.government.Government;
import userInterface.playgroundUI.StaticUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Handles the progression and reading of the sim time. Handles new day events.
 */
public class Time {

    public static boolean timeHasStarted = false;

    Date date;

    private static Random random = new Random();

    private static Integer timeInDay = 0;

    private static int hours = 0;
    private static int days = 1;
    private static int months = 1;
    private static int years = 1;

    //The brightness of the whole sim.
    public static ColorAdjust dayNightCycle = new ColorAdjust();

    //0.2 max brightness, -0.7 max darkness
    private static double brightnessValue = 0;

    /**
     * Updates the sim time according to the main loop.
     */
    public static void updateTime() {
        timeInDay += ((timeInDay < 2401) ? 1 : -timeInDay);
        if (timeInDay == 2400)
            newDayEvent();

        StaticUI.updateTimeOfDayText(timeInDay);
        NaturalSelection.rankFitness();

        updateBrightness();
        dayNightCycle.setBrightness(brightnessValue);
    }

    /**TODO: Implement
     * Updates the brightness of the sim based off of the time of day.
     */
    private static void updateBrightness() {
        if (timeInDay <= 800 || timeInDay >= 2000)
            brightnessValue = -0.7;
        else
            brightnessValue = 0;
    }

    /**
     * Progresses the age and fertility of every cow on a new day event.
     */
    private static void newDayEvent() {
        days++;

        if (days == 31) {
            days = 1;
            months++;

            if (months == 13) {
                months = 1;
                years++;
            }
        }

        if (days == Government.nextElectionDay()) {
            Government.startElection();
            System.out.println("Started Election");
        }

        if (Government.getPreviousElectionDay() + Government.getPollsOpenLength() == days) {
            Government.stopElection();
            System.out.println("Ended Election");
        }

        Cow cowLife;
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            cowLife = CowHandler.liveCowList.get(i);
            cowLife.self.setAge(1);
            cowLife.birth.updateFertility();

            if (cowLife.self.getAge() == 5) {
                cowLife.setScaleX(1.5);
                cowLife.setScaleY(1.5);
            }
            else if (cowLife.self.getAge() == 10) {
                cowLife.setScaleX(2);
                cowLife.setScaleY(2);
            }

            if (cowLife.self.getAge() > random.nextInt((100 - 50) + 1) + 50) {
                cowLife.kill();
            }
        }

        repopulateResources();
    }

    private static void repopulateResources() {
        WoodSource.repopulate();
    }

    /**
     * Converts the int representation of the time into a readable 24-hour date object.
     * @return The date object that contains the current time of day
     */
    public static Date getTime() {
        // Assures that minute only goes to 59
        if ((timeInDay - ((timeInDay / 100) * 100)) != 0 && ((timeInDay - ((timeInDay / 100) * 100)) % 59) == 0)
            timeInDay += 40;

        StringBuilder timeAsString = new StringBuilder(Integer.toString(timeInDay));

        while (timeAsString.length() != 4)
            timeAsString.insert(0, "0");

        hours = Integer.parseInt(timeAsString.substring(0, 2));

        timeAsString.insert(2, ':');
        timeAsString.insert(0, years + "/" + months + "/" + days + "/");

        Date date = null;
        try {
            date = new SimpleDateFormat("y/m/d/HH:mm").parse(timeAsString.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Contract(pure = true)
    public static int getDays() {
        return days;
    }

    @Contract(pure = true)
    public static int getHours() {
        return hours;
    }

    public static boolean hasStarted() {
        return timeHasStarted;
    }
}