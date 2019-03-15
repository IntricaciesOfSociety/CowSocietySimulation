package cowParts.actionSystem.action;

public class GenericAction {

    private ActionDestination actionDestination;
    private StartAction startAction;

    public GenericAction(ActionDestination actionDestination, StartAction startAction) {
        this.actionDestination = actionDestination;
        this.startAction = startAction;
    }

    void executeAction() {
        actionDestination.startBehavior();
        startAction.executeDuringBehavior();
    }
}
