package cowParts.cowMovement;

import javafx.animation.Animation;

interface Start {
    Object startBehavior();
}

interface Execution {
    void executionBehavior();
}

interface End {
    void endBehavior();
}

/**
 * Defines the behavior, creation, and execution of an action that a cow is to perform. Each action includes prerequisites,
 * an execution priority, a starting behavior, a during behavior, and an ending behavior.
 * TODO: Implement. Move from DecideActions.java
 */
class Action {

    Object completeAction;
    Animation[] actionContent = new Animation[3];

    public void execute() {
    }
}
