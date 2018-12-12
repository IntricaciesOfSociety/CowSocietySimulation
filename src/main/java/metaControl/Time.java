package metaControl;

import cowParts.Cow;
import userInterface.StaticUI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Time {

    private static Random random = new Random();

    private static Integer timeInDay = new Random().nextInt(2400);

    static void updateTime() {
        timeInDay += ((timeInDay < 2401) ? 1 : -timeInDay);
        if (timeInDay == 2400)
            newDayEvent();

        StaticUI.updateTimeOfDayText(timeInDay);
    }

    private static void newDayEvent() {
        for (int i = 0; i < Cow.cowList.size(); i++) {
            Cow.cowList.get(i).self.setAge(Cow.cowList.get(i).self.getAge() + 1);
            Cow.cowList.get(i).birth.updateFertility();

            if (Cow.cowList.get(i).self.getAge() > random.nextInt((100 - 50) + 1) + 50) {
                System.out.println(Cow.cowList.get(i).getId());
                Cow.cowList.get(i).kill();
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
