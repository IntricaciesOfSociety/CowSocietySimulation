package cowMovement;

import javafx.animation.Animation;

import java.util.ArrayList;

/**
 * Defines the behavior, creation, and execution of an action that a cow is to perform. Each action includes prerequisites,
 * an execution priority, a starting behavior, a during behavior, and an ending behavior.
 * TODO: Implement. Move from DecideActions.java
 */
abstract class Action {

    ArrayList<Animation> actionAnimation = new ArrayList<>();
}
