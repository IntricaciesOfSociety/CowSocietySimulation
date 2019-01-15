package buildings;

import cowParts.Cow;
import javafx.scene.image.Image;
import menus.MenuHandler;
import metaControl.SimState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import resourcesManagement.ResourcesHandler;
import terrain.Tile;

import java.util.ArrayList;

/**
 * Handles the creation of small dwelling buildings. Called only if building prerequisites have been fulfilled
 * (resources and technology).
 */
public class SmallDwelling extends Building {

    /**
     * Calls for the creation of a building given an image.
     * @param buildingSprite The image to create a building from
     * @param name The name of the building
     * @param tileToBuildOn The tile that the building will be built on
     */
    public SmallDwelling(Image buildingSprite, String name, Tile tileToBuildOn) {
        constructBuilding(buildingSprite, name, tileToBuildOn);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void constructBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        this.setId(buildingName);
        this.buildingSprite = buildingSprite;
        this.setImage(BuildingHandler.smallUnderConstructionSprite);

        this.streetAddress = random.nextInt(500) + " Cow Drive";

        this.buildingRequirement = new ResourceRequirement(0, 5, 1);

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);

        if (tileToBuildOn.tieToObject(this, Tile.getSize(buildingSprite)))
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
        this.isConstructed = true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addInhabitant(Cow inhabitant) {
        this.currentInhabitants.add(inhabitant);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void removeInhabitant(Cow inhabitant) {
        this.currentInhabitants.remove(inhabitant);
    }

    /**
     * @inheritDoc
     */
    @Override
    public ArrayList<Cow> getCurrentInhabitants() {
        return this.currentInhabitants;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void toggleInhabitantsMenu() {
        if (this.inhabitantsMenuOpened) {
            MenuHandler.closeMenu(this.inhabitantsMenu);
            this.inhabitantsMenuOpened = false;
        }
        else {
            this.inhabitantsMenu = MenuHandler.createInhabitantsMenu(this);
            this.inhabitantsMenuOpened = true;
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
        return this.buildingRequirement;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isConstructed() {
        return this.isConstructed;
    }

    @Override
    boolean isVotingPlace() {
        return Building.checkIfVotingPlace(this.getId());
    }
}
