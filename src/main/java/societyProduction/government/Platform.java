package societyProduction.government;

import cowParts.creation.Cow;
import societyProduction.Opinion;

import java.util.ArrayList;

/** TODO: Implement me!
 *  A platform is a collection of opinions on issues. Used in a political context.
 */
class Platform {

    private ArrayList allIssueOpinions = new ArrayList<Opinion>();
    private ArrayList primaryFocuses = new ArrayList<Opinion>();

    void setNewLeaderPlatform(Cow leader) {
        allIssueOpinions = Opinion.getCurrentIssueOpinions(leader);
        primaryFocuses.add(allIssueOpinions.get(0));
    }

    public ArrayList getPrimaryFocuses() {
        return primaryFocuses;
    }
}
