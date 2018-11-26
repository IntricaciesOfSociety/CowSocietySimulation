package buildings;

import cowParts.Cow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import menus.MenuCreation;
import menus.MenuHandler;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles the creation of buildings. Called only if building prerequisites have been fulfilled (resources and technology).
 */
public class Building extends ImageView {

    private boolean inhabitantsMenuOpened = false;
    private MenuCreation inhabitantsMenu;

    private ArrayList<Cow> currentInhabitants = new ArrayList<>();

    /**
     * Calls for the creation of a building given an image.
     * @param buildingSprite The image to create a building from
     */
    Building(Image buildingSprite) {
        constructBuilding(buildingSprite);
    }

    /**
     * Creates a Building with the given image and ties that Building to a tile.
     * @param buildingSprite The image to be used for an ImageView relation to a tile.
     */
    private void constructBuilding(Image buildingSprite) {
        this.setImage(buildingSprite);
        this.setScaleX(20);
        this.setScaleY(40);
        int tileSize = (buildingSprite.getWidth() > 10) ? 1 : 4;
        Tile.tieToBuilding(this, tileSize);
    }

    /**
     * Adds the given cow to the inhabitants list
     * @param inhabitant The cow to be added as an inhabitant
     */
    public void addInhabitant(Cow inhabitant) {
        currentInhabitants.add(inhabitant);
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
}
