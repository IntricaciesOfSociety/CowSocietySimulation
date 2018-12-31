package societalProductivity;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

/** TODO: Implement me!
 * Creates and handles political and social issues within the society.
 */
public class Issue {

    private static ArrayList<Issue> currentIssues = new ArrayList<>();

    Issue(String name, String ... biases) {

    }

    public static void createCurrentIssue(Issue newIssue) {

    }

    @Contract(pure = true)
    static ArrayList<Issue> getCurrentIssues() {
        return currentIssues;
    }
}
