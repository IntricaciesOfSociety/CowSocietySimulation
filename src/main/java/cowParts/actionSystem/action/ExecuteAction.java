package cowParts.actionSystem.action;

import cowParts.Cow;
import cowParts.CowHandler;
import javafx.scene.shape.Rectangle;
import userInterface.playgroundUI.StaticUI;

public class ExecuteAction {

     public static void execute(GenericAction newAction) {
         if (newAction != null)
            newAction.executeAction();
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