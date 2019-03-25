package metaEnvironment.logging;

import cowParts.Cow;
import metaControl.main.SimState;
import metaControl.timeControl.Time;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import societalProductivity.urbanPlanning.CivicControl;

import java.io.*;

/**
 * Logs every action for each individual cow. Also auto-generates a city-wide log for important events.
 */
public class EventLogger {

    /**
     * Logs the given event to the given cow's log.
     * @param cowToLogTo The cow that the event is being logged to. Cannot be null
     * @param event The event to be logged as a string.
     * @param importance A scale of 0 - 2 in order of least to greatest importance used for if the event is to be logged
     *                   to the city-wide log or not.
     * @param cognitiveEffecting The emotion that is being affected by the event if any.
     */
    public static void createLoggedEvent(@NotNull Cow cowToLogTo, String event, int importance, String cognitiveEffecting, int effectAmount) {
        StringBuilder logMessage = new StringBuilder();

        logMessage.append(cognitiveEffecting).append((effectAmount >= 0) ? "+" + effectAmount : effectAmount).append(" ");
        logMessage.append(Time.getTime()).append(" ");
        logMessage.append(event);

        logEvent(logMessage.toString(), cowToLogTo.getId());

        if (importance >= 1) {
            logMessage.insert(0, cowToLogTo.getId() + ": ");
            logEvent(logMessage.toString(), CivicControl.getCityName());
        }
    }

    public static void clearLogs() {
        try {
            FileUtils.cleanDirectory(new File("src/main/logs/session"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logEvent(String message, String fileTarget) {
        MDC.put("logName", fileTarget);
        SimState.logger.info(message);
    }

    @NotNull
    public static String getCowStatLog(@NotNull Cow firstCow, String stat) {
        StringBuilder content = new StringBuilder();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/logs/session/" + firstCow.getId() + ".log")));

            String currentLine = reader.readLine();
            while (currentLine != null) {

                if (currentLine.contains(stat))
                    content.append(currentLine).append("\n");

                currentLine = reader.readLine();
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return content.toString();
    }

    @NotNull
    public static String getEntireCowLog(@NotNull Cow firstCow) {
        StringBuilder content = new StringBuilder();

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/main/logs/session/" + firstCow.getId() + ".log")));

            String currentLine = reader.readLine();
            while (currentLine != null) {
                content.append(currentLine).append("\n");
                currentLine = reader.readLine();
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return content.toString();
    }
}
