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

    private static Cow leader;

    private static Platform cityPlatform = new Platform();

    private static void setNewPlatform() {
        cityPlatform.addAllIssueOpinions(Opinion.getCurrentIssueOpinions(leader));
    }

    @Contract(pure = true)
    public static Platform getCityPlatform() {
        return cityPlatform;
    }

    @Contract(pure = true)
    public static Cow getLeader() {
        return leader;
    }

    public static void setLeader(@NotNull Cow cowToBeLeader) {
        leader = cowToBeLeader;
        cowToBeLeader.setImage(AssetLoading.loadCowRole("SnowmanCow"));
        setNewPlatform();
    }

    @Contract(pure = true)
    public static boolean hasLeader() {
        return leader != null;
    }
}
