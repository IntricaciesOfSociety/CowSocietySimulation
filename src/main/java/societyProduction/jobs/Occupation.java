package societyProduction.jobs;

import cowParts.creation.Cow;
import cowParts.actionSystem.action.ActionDestination;
import cowParts.actionSystem.action.EndAction;
import javafx.scene.image.Image;

public class Occupation {

    private String jobTitle;
    private Image jobSprite;
    private String jobActionText;

    private ActionDestination getDestination;
    private EndAction finishBehavior;

    Occupation(Cow cowToAssign, String actionText, String jobTitle, Image jobSprite, ActionDestination getDestination, EndAction finishBehavior) {
        this.finishBehavior = finishBehavior;
        this.getDestination = getDestination;
        this.jobSprite = jobSprite;
        this.jobTitle = jobTitle;
        this.jobActionText = actionText;
        setCowActionText(cowToAssign);
    }

    private void setCowActionText(Cow cowToAssign) {
        cowToAssign.setCurrentBehavior(jobActionText);
    }

    public String getJobActionText() {
        return jobActionText;
    }

    public Image getJobSprite() {
        return jobSprite;
    }

    public String getJobName() {
        return jobTitle;
    }

    public void completeJob() {
        finishBehavior.executeBehavior();
    }

    public Object generateJobDestination() {
        return getDestination.startBehavior();
    }
}
