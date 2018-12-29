package metaControl;

import cowParts.Cow;
import cowParts.CowHandler;
import javafx.scene.effect.ColorAdjust;
import userInterface.StaticUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Handles the progression and reading of the sim time. Handles new day events.
 */
public class Time {

    private static Random random = new Random();

    private static Integer timeInDay = new Random().nextInt(2400);

    //The brightness of the whole sim.
    public static ColorAdjust dayNightCycle = new ColorAdjust();

    //0.2 max brightness, -0.7 max darkness
    private static double brightnessValue = 0;

    /**
     * Updates the sim time according to the main loop.
     */
    static void updateTime() {
        timeInDay += ((timeInDay < 2401) ? 1 : -timeInDay);
        if (timeInDay == 2400)
            newDayEvent();

        StaticUI.updateTimeOfDayText(timeInDay);

        updateBrightness();
        dayNightCycle.setBrightness(brightnessValue);
    }

    /**TODO: Implement
     * Updates the brightness of the sim based off of the time of day.
     */
    private static void updateBrightness() {
        if (timeInDay == 800)
            brightnessValue = 0;

        if (timeInDay == 2000)
            brightnessValue = -0.7;
    }

    /**
     * Progresses the age and fertility of every cow on a new day event.
     */
    private static void newDayEvent() {
        Cow cowLife;
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            cowLife = CowHandler.liveCowList.get(i);
            cowLife.self.setAge(1);
            cowLife.birth.updateFertility();

            if (cowLife.self.getAge() == 5) {
                cowLife.setScaleX(2.25);
                cowLife.setScaleY(2.25);
            }
            else if (cowLife.self.getAge() == 10) {
                cowLife.setScaleX(3);
                cowLife.setScaleY(3);
            }

            if (cowLife.self.getAge() > random.nextInt((100 - 50) + 1) + 50) {
                System.out.println(CowHandler.liveCowList.get(i).getId());
                cowLife.kill();
            }
        }
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

        timeAsString.insert(2, ':');

        Date date = null;
        try {
            date = new SimpleDateFormat("HH:mm").parse(timeAsString.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
