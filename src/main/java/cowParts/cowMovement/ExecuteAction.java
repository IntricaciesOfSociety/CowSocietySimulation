package cowParts.cowMovement;

import cowParts.Cow;
import cowParts.CowHandler;
import javafx.animation.PathTransition;
import javafx.scene.shape.Rectangle;
import userInterface.StaticUI;

public class ExecuteAction {

     static void execute(Action newAction) {
         if (newAction != null && newAction.completeAction != null) {
             if (newAction instanceof Movement)
                 ((PathTransition)((Movement) newAction).completeAction).play();
             else
                 newAction.execute();
         }
    }

    /**
     * Checks for collision between all cows and the given dragBox
     * @param dragBox The dragBox to check for collisions against
     */
    public static void dragBoxSelectionUpdate(Rectangle dragBox) {
        Cow possibleCollide;
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            possibleCollide = CowHandler.liveCowList.get(i);
            if (possibleCollide.getBoundsInParent().intersects(dragBox.getBoundsInParent())) {
                possibleCollide.openMenu();
                StaticUI.cowClickEvent();
            }
        }
    }

    /**
     * Pauses any running cow animation.
     */
    public static void pauseAllAnimation() {
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            try {
                CowHandler.liveCowList.get(i).animation.pause();
            }
            catch (NullPointerException ignored){}
        }
    }

    /**
     * Resumes any running cow animation.
     */
    public static void startAllAnimation() {
        for (int i = 0; i < CowHandler.liveCowList.size(); i++) {
            try {
                CowHandler.liveCowList.get(i).animation.play();
            }
            catch (NullPointerException ignored) {}
        }
    }
}