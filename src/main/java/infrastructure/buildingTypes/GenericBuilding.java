package infrastructure.buildingTypes;

import cowParts.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import menus.GenericMenu;
import menus.MenuCreation;
import metaEnvironment.Regioning.BinRegion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import terrain.Tile;

import java.util.ArrayList;
import java.util.Random;

/**
 * Creates the blueprint for all infrastructure. Makes sure that all infrastructure have standard interactions and that all infrastructure
 * are tracked properly.
 */
public abstract class GenericBuilding extends Tile {

    //The random variable to be used to generate random street numbers.
    Random random = new Random();

    //TODO: Implement
    Point2D buildingEntrance;

    // TODO:Implement
    private int maximumCapacity = 10;

    // The sprite of the given building
    Image buildingSprite;

    GenericMenu inhabitantsMenu;

    String streetAddress;

    //The list that contains all of the cows that are in the given building
    ArrayList<Cow> currentInhabitants = new ArrayList<>();

    ResourceRequirement buildingRequirement;
    boolean isConstructed = false;

    /**
     * Creates a GenericBuilding with the given image and ties that GenericBuilding to a tile.
     * @param buildingSprite The image to be used for an ImageView relation to a tile
     * @param buildingName The name to be used as the ID for the building. Should be the same as the image name
     * @param tileToBuildOn The tile that the building will be built on
     */
    abstract void constructBuilding(Image buildingSprite, String buildingName,  @NotNull Tile tileToBuildOn);

    /**
     * Calls the move of resources from the city pool to the building that this was called upon.
     * @param resourceContribution The type of resource to contribute
     * @param amountToBeUsed The amount of the given resource to contribute
     */
    public abstract void contributeResource(String resourceContribution, int amountToBeUsed);

    /**
     * Handles the change of sprites and resource requirements whenever a building is finished.
     */
    abstract void finishConstruction();

    /**
     * Adds as an inhabitant by adding the cow to the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    public abstract void addInhabitant(Cow inhabitant);

    /**
     * Removes an inhabitant by removing the cow from the inhabitants list.
     * @param inhabitant The cow to be added as an inhabitant
     */
    public abstract void removeInhabitant(Cow inhabitant);

    /**
     * Returns the list of all inhabitants within this building.
     * @return The list of all inhabitants
     */
    public abstract ArrayList<Cow> getCurrentInhabitants();

    /**
     * Calls for the creation or the destruction of this building's inhabitants menu based off the last state of the
     * inhabitants menu.
     */
    public abstract void toggleInhabitantsMenu();

    /**
     * Returns the street address of the given building.
     * @return The address found
     */
    @Contract(pure = true)
    public abstract String getStreetAddress();

    /**
     * @return The resource requirement for the building that this method is called on.
     */
    public abstract ResourceRequirement getResourceRequirement();

    /**
     * @return If the building this method is called upon is constructed or not.
     */
    public abstract boolean isConstructed();
}
