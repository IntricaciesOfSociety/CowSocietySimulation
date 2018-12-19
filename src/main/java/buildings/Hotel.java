package buildings;

import cowParts.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import menus.MenuCreation;
import menus.MenuHandler;
import metaControl.SimState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import resourcesManagement.ResourcesHandler;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles the creation of buildings. Called only if building prerequisites have been fulfilled (resources and technology).
 */
public class Hotel extends Building {

    //TODO: Implement
    Point2D buildingEntrance;

    // TODO:Implement
    private int maximumCapacity = 10;

    Image buildingSprite;

    private boolean inhabitantsMenuOpened = false;
    private MenuCreation inhabitantsMenu;

    private String streetAddress;

    private ArrayList<Cow> currentInhabitants = new ArrayList<>();

    private ResourceRequirement buildingRequirement;
    private boolean isConstructed = false;

    /**
     * Calls for the creation of a building given an image.
     * @param buildingSprite The image to create a building from
     * @param tileToBuildOn The tile that the building will be built on
     */
    public Hotel(Image buildingSprite, Tile tileToBuildOn) {
        constructBuilding(buildingSprite, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructBuilding(Image buildingSprite, @NotNull Tile tileToBuildOn) {
        this.buildingSprite = buildingSprite;
        this.setImage(BuildingHandler.largeUnderConstructionSprite);

        int tileSize = (buildingSprite.getWidth() <= 400) ? 1 : 4;
        streetAddress = random.nextInt(500) + " Cow Drive";

        buildingRequirement = new ResourceRequirement(0, 5, 1);

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);

        if (tileToBuildOn.tieToObject(this, tileSize))
            BuildingHandler.buildingsList.add(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void contributeResource(String resourceContribution, int amountToBeUsed) {
        ResourcesHandler.repurposeResource(buildingRequirement, resourceContribution, amountToBeUsed);

        if (this.buildingRequirement.passesRequirements())
            finishConstruction();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void finishConstruction() {
        this.setImage(buildingSprite);
        isConstructed = true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addInhabitant(Cow inhabitant) {
        currentInhabitants.add(inhabitant);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeInhabitant(Cow inhabitant) {
        currentInhabitants.remove(inhabitant);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Cow> getCurrentInhabitants() {
        return currentInhabitants;
    }

    /**
     * @inheritDoc
     */
    @Override
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

    /**
     * @inheritDoc
     */
    @Contract(pure = true)
    @Override
    public String getStreetAddress() {
        return this.streetAddress;
    }

    /**
     * @inheritDoc
     */
    @Override
    public Tile getBuildingAsBuildingTile() {
        return this;
    }

    /**
     * @inheritDoc
     */
    @Override
    public ResourceRequirement getResourceRequirement() {
        return buildingRequirement;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConstructed() {
        return isConstructed;
    }
}
