package buildings;

import cowParts.Cow;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Random;

public interface Building {

    /**
     * The random variable to be used to generate random street numbers.
     */
    Random random = new Random();

    /**
     * Creates a Building with the given image and ties that Building to a tile.
     * @param buildingSprite The image to be used for an ImageView relation to a tile
     * @param tileToBuildOn The tile that the building will be built on
     */
    void constructBuilding(Image buildingSprite, @NotNull Tile tileToBuildOn);

    void contributeResource(String resourceContribution, int amountToBeUsed);

    void finishConstruction();

    /**
     * Adds as an inhabitant by adding the cow to the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    void addInhabitant(Cow inhabitant);

    /**
     * Removes an inhabitant by removing the cow from the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    void removeInhabitant(Cow inhabitant);

    /**
     * Returns the list of all inhabitants within this building.
     * @return The list of all inhabitants
     */
    ArrayList<Cow> getCurrentInhabitants();

    /**
     * Calls for the creation or the destruction of this building's inhabitants menu based off the last state of the
     * inhabitants menu.
     */
    void toggleInhabitantsMenu();

    /**
     * Returns the street address of the given building.
     * @return The address found
     */
    @Contract(pure = true)
    String getStreetAddress();

    /**
     * @return The building as the tile type that it is instead of as the Building type
     */
    Tile getBuildingAsBuildingTile();

    ResourceRequirement getResourceRequirement();
}
