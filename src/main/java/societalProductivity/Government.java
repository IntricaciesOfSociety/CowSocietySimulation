package societalProductivity;

import cowParts.Cow;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * TODO: Implement me!
 * Creates and handles the structure for division and enforcement of political and economical power within the city.
 */
public class Government {

    private static boolean electionRunning = false;
    private static Cow leader;

    private static Platform cityPlatform = new Platform();

    private static void setNewPlatform() {
        cityPlatform.setNewLeaderPlatform(leader);
    }

    @Contract(pure = true)
    public static Platform getCityPlatform() {
        return cityPlatform;
    }

    @Contract(pure = true)
    public static Cow getLeader() {
        return leader;
    }

    static void setLeader(@NotNull Cow cowToBeLeader) {
        leader = cowToBeLeader;
        cowToBeLeader.setImage(AssetLoading.loadCowRole("SnowmanCow"));
        setNewPlatform();
    }

    @Contract(pure = true)
    static boolean hasLeader() {
        return leader != null;
    }

    @Contract(pure = true)
    public static boolean isElectionRunning() {
        return electionRunning;
    }

    public static void startElection() {
        electionRunning = true;
    }

    public static void stopElection() {
        electionRunning = false;
    }
}
