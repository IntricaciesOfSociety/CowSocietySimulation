package societalProductivity;

import cowParts.creation.Cow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * An opinion is a representation of a cow's personal beliefs on a matter. Opinions can be anything from preferences
 * to political issues.
 */
public class Opinion {

    private static Opinion getOpinionOnIssue(@NotNull Cow cowToGetOpinionFrom, Issue issue) {
        return cowToGetOpinionFrom.views.getOpinionOnIssue(issue);
    }

    public static ArrayList getCurrentIssueOpinions(Cow leader) {
        ArrayList<Opinion> allCurrentIssueOpinions = new ArrayList<>();

        for (int i = 0; i < Issue.getCurrentIssues().size(); i++) {
            allCurrentIssueOpinions.add(getOpinionOnIssue(leader, Issue.getCurrentIssues().get(i)));
        }

        return allCurrentIssueOpinions;
    }
}
