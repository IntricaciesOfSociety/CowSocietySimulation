package environment;

import org.jetbrains.annotations.NotNull;

/**
 * Logs every action for each individual cow. Also auto-generates a city-wide log for important events.
 * TODO: Switch implementation to an XML file structure.
 */
class EventLogger {

    private String eventLog = "";
    /**
     * TODO: Switch implementation to writing to files
     * Logs the given event to the given cow's log.
     * @param cowToLogTo The cow that the event is being logged to. Cannot be null
     * @param event The event to be logged as a string.
     * @param importance A scale of 0 - 2 in order of least to greatest importance used for if the event is to be logged
     *                   to the city-wide log or not.
     */
    static void createLoggedEvent(@NotNull Cow cowToLogTo, String event, int importance) {
        cowToLogTo.logger.eventLog.concat( cowToLogTo.getId() + " " + event + "\n");
        cowToLogTo.logger.logEvent(importance);
    }

    /**
     * TODO: Switch implementation to writing to files
     * @param importance A scale of 0 - 2 in order of least to greatest importance used for if the event is to be logged
     *                   to the city-wide log or not.
     */
    private void logEvent(int importance) {
        System.out.println("Logged" + importance);
    }
}
