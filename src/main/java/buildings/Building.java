package buildings;

import cowParts.Cow;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuCreation;
import menus.MenuHandler;
import metaControl.SimState;
import metaEnvironment.Playground;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles the creation of buildings. Called only if building prerequisites have been fulfilled (resources and technology).
 */
public class Building extends ImageView {

    private boolean inhabitantsMenuOpened = false;
    private MenuCreation inhabitantsMenu;

    //TODO: Implement
    Point2D buildingEntrance;

    // TODO:Implement
    private int maximumCapacity = 10;

    private String streetAddress;

    private AnimationTimer delayTimer;
    private ArrayList<Cow> currentInhabitants = new ArrayList<>();

    /**
     * Calls for the creation of a building given an image.
     * @param buildingSprite The image to create a building from
     * @param tileToBuildOn The tile that the building will be built on
     */
    Building(Image buildingSprite, ImageView tileToBuildOn) {
        constructBuilding(buildingSprite, tileToBuildOn);
    }

    /**
     * Creates a Building with the given image and ties that Building to a tile.
     * @param buildingSprite The image to be used for an ImageView relation to a tile.
     * @param tileToBuildOn The tile that the building will be built on
     */
    private void constructBuilding(Image buildingSprite, ImageView tileToBuildOn) {
        this.setImage(buildingSprite);

        int tileSize = (buildingSprite.getWidth() <= 400) ? 1 : 4;
        streetAddress = Tile.tieToBuilding(this, tileToBuildOn, tileSize);
        Playground.playground.getChildren().add(this);

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);
    }

    /**
     * Adds as an inhabitant by adding the cow to the inhabitants list. Removes the cow when its stay is up.
     * @param inhabitant The cow to be added as an inhabitant
     */
    public void addInhabitant(Cow inhabitant) {
        currentInhabitants.add(inhabitant);
    }

    /**
     * Adds as an inhabitant by adding the cow to the inhabitants list. Removes the cow when its stay is up.
     * @param inhabitant The cow to be added as an inhabitant
     */
    public void removeInhabitant(Cow inhabitant) {
        currentInhabitants.remove(inhabitant);
    }


    /**
     * Stops the delay loop from executing further. If the loop
     */
    private void stopDelayLoop() {
        delayTimer.stop();
    }

    /**
     * Returns the list of all inhabitants within this building.
     * @return The list of all inhabitants
     */
    public ArrayList<Cow> getCurrentInhabitants() {
        return currentInhabitants;
    }

    /**
     * Calls for the creation or the destruction of this building's inhabitants menu based off the last state of the
     * inhabitants menu.
     */
    public void toggleInhabitantsMenu() {
        if (inhabitantsMenuOpened) {
            MenuHandler.closeMenu(inhabitantsMenu);
            inhabitantsMenuOpened = false;
        }
        else {
            inhabitantsMenu = MenuHandler.createInhabitantsMenu(this);
            inhabitantsMenuOpened = true;
        }
    }

    /**TODO: Implement
     * Returns the street address of the given building.
     * @param buildingToFindAddress The building whose address is being found
     * @return The address found
     */
    @Contract(pure = true)
    public static String getStreetAddress(@NotNull Building buildingToFindAddress) {
        System.out.println(buildingToFindAddress);
        return buildingToFindAddress.streetAddress;
    }
}
