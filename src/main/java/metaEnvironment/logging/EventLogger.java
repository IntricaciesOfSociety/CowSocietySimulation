package metaEnvironment.logging;

import cowParts.Cow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Logs every action for each individual cow. Also auto-generates a city-wide log for important events.
 * TODO: Switch implementation to an markup file structure. (XML?)
 */
public class EventLogger {

    private ArrayList<String> eventLog = new ArrayList<>();
    private ArrayList<String> effectedEmotions = new ArrayList<>();

    private int nameLength;

    /**
     * TODO: Switch implementation to writing to files
     * Logs the given event to the given cow's log.
     * @param cowToLogTo The cow that the event is being logged to. Cannot be null
     * @param event The event to be logged as a string.
     * @param importance A scale of 0 - 2 in order of least to greatest importance used for if the event is to be logged
     *                   to the city-wide log or not.
     * @param cognitiveEffecting The emotion that is being affected by the event if any.
     */
    public static void createLoggedEvent(@NotNull Cow cowToLogTo, String event, int importance, String cognitiveEffecting, int effectAmount) {
        cowToLogTo.logger.nameLength = cowToLogTo.getId().length();
        cowToLogTo.logger.eventLog.add(cowToLogTo.getId() + " " + event + ": " + cognitiveEffecting + effectAmount + "\n");
        cowToLogTo.logger.effectedEmotions.add(cognitiveEffecting);
        cowToLogTo.logger.logEvent(importance);
    }

    /**
     * TODO: Switch implementation to writing to files
     * @param importance A scale of 0 - 2 in order of least to greatest importance used for if the event is to be logged
     *                   to the city-wide log or not.
     */
    private void logEvent(int importance) {
        
    }

    /**
     * Gets the events that contain the given emotion if that emotion has any events connected to it.
     * @param emotion The emotion to use to find an event
     * @return The events found with the given emotion
     */
    public String getEventsFromEmotion(String emotion) {

        StringBuilder eventList = new StringBuilder();

        for (int i = 0; i < effectedEmotions.size(); i++) {
            if (effectedEmotions.get(i) != null && effectedEmotions.get(i).equals(emotion))
                eventList.append(eventLog.get(i).substring(nameLength + 1));
        }
        return eventList.toString();
    }
}
