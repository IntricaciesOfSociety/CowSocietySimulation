package societalProductivity;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

/** TODO: Implement me!
 * Creates and handles political and social issues within the society.
 */
public class Issue {

    private static ArrayList<Issue> currentIssues = new ArrayList<>();

    private String issueName;

    private Issue(String name) {
        issueName = name;
    }

    public static void init() {
        createCurrentIssue(new Issue("Too many ducks"));
    }

    private static void createCurrentIssue(Issue newIssue) {
        currentIssues.add(newIssue);
    }

    @Contract(pure = true)
    static ArrayList<Issue> getCurrentIssues() {
        return currentIssues;
    }

    public String getIssueName() {
        return issueName;
    }
}
