package societalProductivity.government;

import cowParts.Cow;
import cowParts.CowHandler;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

/**
 * TODO: Implement me!
 * Creates and handles the structure for division and enforcement of political and economical power within the city.
 */
public class Government {
    private static Random random = new Random();

    private static boolean electionRunning = false;
    private static Cow leader;

    private static Platform cityPlatform = new Platform();
    private static int lastElectionDay = 1;
    private static int electionQuarterLength = 7;
    private static int pollsOpenLength = 3;

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

    public static void setLeader(@NotNull Cow cowToBeLeader) {
        leader = cowToBeLeader;
        cowToBeLeader.setImage(AssetLoading.loadCowRole("BusinessCow"));
        setNewPlatform();
    }

    @Contract(pure = true)
    public static boolean hasLeader() {
        return leader != null;
    }

    @Contract(pure = true)
    public static boolean isElectionRunning() {
        return electionRunning;
    }

    public static void startElection() {
        lastElectionDay = Time.getDays();
        electionRunning = true;
    }

    public static void stopElection() {
        electionRunning = false;

        ArrayList<Cow> possibleLeaders = new ArrayList<>();
        int cowVote;
        int biggestVote = 0;
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            cowVote = CowHandler.liveCowList.get(i).getVotes();

            if (cowVote > biggestVote) {
                possibleLeaders.clear();
                possibleLeaders.add(CowHandler.liveCowList.get(i));
            }
            else if (cowVote == biggestVote)
                possibleLeaders.add(CowHandler.liveCowList.get(i));

            CowHandler.liveCowList.get(i).setVotes(0);
            CowHandler.liveCowList.get(i).setHasVoted(false);
        }

        if (possibleLeaders.size() > 0)
            setLeader(possibleLeaders.get(random.nextInt(possibleLeaders.size())));
    }

    public static void vote(@NotNull Cow cow) {
        cow.increaseVote();
    }

    @Contract(pure = true)
    public static int nextElectionDay() {
        int electionDay = lastElectionDay + electionQuarterLength;
        return (electionDay > 31) ? electionDay - 31 : electionDay;
    }

    @Contract(pure = true)
    public static int getPollsOpenLength() {
        return pollsOpenLength;
    }

    @Contract(pure = true)
    public static int getPreviousElectionDay() {
        return lastElectionDay;
    }
}
