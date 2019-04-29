package societyProduction.government;

import org.jetbrains.annotations.Contract;

public class Election {

    private static boolean electionRunning = false;
    private static int lastElectionDay = 1;
    private static int electionQuarterLength = 7;
    private static int pollsOpenLength = 3;

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
