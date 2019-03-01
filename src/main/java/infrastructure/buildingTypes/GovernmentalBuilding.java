package infrastructure.buildingTypes;

import cowParts.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import menus.MenuHandler;
import metaControl.main.SimState;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import resourcesManagement.ResourcesHandler;
import terrain.Tile;

import java.util.ArrayList;

public class GovernmentalBuilding extends GenericBuilding {

    /**
     * @inheritDoc
     */
    @Override
    void constructBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        int tileSize = Tile.getSize(buildingSprite);

        this.setImage((tileSize < 4) ? AssetLoading.smallUnderConstructionSprite : AssetLoading.largeUnderConstructionSprite);
        this.setId(buildingName);
        this.buildingSprite = buildingSprite;

        this.streetAddress = random.nextInt(500) + " Cow Drive";

        this.buildingRequirement = new ResourceRequirement(0, 10, 1);

        if (SimState.getSimState().equals("TileView"))
            this.setOpacity(0.5);

        if (tileToBuildOn.tieToObject(this, tileSize)) {
            tileToBuildOn.getRegion().addGovernmentalBuilding(this);
            buildingEntrance = new Point2D(this.getLayoutX() + buildingSprite.getWidth(), this.getLayoutY() + (buildingSprite.getHeight() / 2));
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void contributeResource(String resourceContribution, int amountToBeUsed) {
        ResourcesHandler.repurposeResource(this.buildingRequirement, resourceContribution, amountToBeUsed);

        if (this.buildingRequirement.passesRequirements())
            finishConstruction();
    }

    /**
     * @inheritDoc
     */
    @Override
    void finishConstruction() {
        this.setImage(this.buildingSprite);
        this.isConstructed = true;
    }

    /**
     * @inheritDoc
     */
    @Override
    void addInhabitant(Cow inhabitant) {
        this.currentInhabitants.add(inhabitant);
    }

    /**
     * @inheritDoc
     */
    @Override
    void removeInhabitant(Cow inhabitant) {
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
    @Override
    public String getStreetAddress() {
        return this.streetAddress;
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
}