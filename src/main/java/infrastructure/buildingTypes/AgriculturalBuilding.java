package infrastructure.buildingTypes;

import cowParts.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import menus.MenuCreation;
import menus.MenuHandler;
import metaControl.main.SimState;
import metaEnvironment.AssetLoading;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import resourcesManagement.ResourcesHandler;
import terrain.Tile;
import terrain.TileHandler;

import java.util.ArrayList;

public class AgriculturalBuilding extends GenericBuilding {

    public AgriculturalBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        constructBuilding(buildingSprite, buildingName, tileToBuildOn);
    }

    @Override
    void constructBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        int tileSize = TileHandler.getSize(buildingSprite);

        if (tileToBuildOn.tieToObject(this, tileSize)) {
            this.setImage((tileSize < 4) ? AssetLoading.smallUnderConstructionSprite : AssetLoading.largeUnderConstructionSprite);
            this.setId(buildingName);
            this.buildingSprite = buildingSprite;

            this.regionIn = tileToBuildOn.getRegion();
            this.streetAddress = random.nextInt(500) + " Cow Drive";

            this.buildingRequirement = new ResourceRequirement(0, 10, 1);

            if (SimState.getSimState().equals("TileView"))
                this.setOpacity(0.5);

            tileToBuildOn.getRegion().addAgriculturalBuilding(this);
            buildingEntrance = new Point2D(this.getLayoutX() + buildingSprite.getWidth(), this.getLayoutY() + (buildingSprite.getHeight() / 2));

            tileToBuildOn.getRegion().addToBuildQueue(this);
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
        this.regionIn.removeFromConstructionQueue(this);
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
        if (this.inhabitantsMenu != null) {
            MenuHandler.closeMenu(this.inhabitantsMenu);
            inhabitantsMenu = null;
        }
        else
            this.inhabitantsMenu = MenuCreation.createInhabitantsMenu(this);
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
