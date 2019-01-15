package cowParts;

import buildings.BuildingHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import metaControl.LoadConfiguration;
import metaControl.Time;
import metaEnvironment.AssetLoading;
import metaEnvironment.EventLogger;
import metaEnvironment.Playground;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import societalProductivity.Role;
import userInterface.PlaygroundUI;
import userInterface.StaticUI;

import java.util.ArrayList;
import java.util.Random;

import static cowParts.BirthEvent.random;

/**
 * Handles the creation and initialization of cows.
 */
public class CowHandler {

    //List that holds every created cow
    public static ArrayList<Cow> liveCowList = new ArrayList<>();

    /**
     * Creates the amount of cows given by the configuration file.
     */
    public static void init() {
        for (int i = 0; i < LoadConfiguration.getInitialPopulation(); i++) {
            createCow();
        }
    }

    /**
     * TEMP
     * Draws a cow to the screen for testing purposes. Moves the cow to a random location then creates and saves a link
     * for the cow to be used in PlaygroundUI.
     */
    static Cow createCow() {
        Image cowSprite = AssetLoading.basicCows.get(random.nextInt(AssetLoading.basicCows.size()));

        Cow newCow = new Cow();
        newCow.setImage(cowSprite);
        newCow.setColor(new ColorAdjust());

        newCow.setId("Big Beefy" + new Random().nextInt(1000));
        newCow.setTranslateX(random.nextInt( (int) Playground.playground.getPrefWidth()));
        newCow.setTranslateY(random.nextInt( (int) Playground.playground.getPrefHeight()));
        newCow.setEffect(newCow.getColor());
        newCow.setScaleX(1.5);
        newCow.setScaleY(1.5);
        newCow.setSmooth(false);

        //TODO: Switch to an actual date
        newCow.birth.setBirthday(Time.getTime());

        newCow.setLivingSpace(BuildingHandler.getBuildingAssignment(newCow.getId()));

        new Role(newCow);

        newCow.setCowLink(StaticUI.cowCreationEvent(newCow.getId()));
        EventLogger.createLoggedEvent(newCow, "creation", 2, "age", 0);

        Playground.playground.getChildren().add(newCow);
        liveCowList.add(newCow);

        return newCow;
    }

    /**
     * Searches for the cow matching the given id and returns the match (null if there was no match).
     * @param givenId The id of the cow that is being searched for
     * @return The cow with id matching givenId if a cow is found. Else null
     */
    @Nullable
    public static Cow findCow(String givenId) {
        for (Cow aCowList : liveCowList)
            if (aCowList.getId().equals(givenId))
                return aCowList;
        return null;
    }

    /**
     * Diseases all cows from the list given.
     * @param diseaseList The list of cows to disease
     */
    public static void diseaseAll(@NotNull ArrayList<Cow> diseaseList) {
        for (Cow cowToDisease : diseaseList) {
            cowToDisease.disease();
        }
    }

    /**
     * Kills a whole list of cows
     * @param killList The list of cows to kill
     */
    public static void killAll(@NotNull ArrayList<Cow> killList) {
        for (Cow cowToKill : killList)
            cowToKill.kill();
    }
}
