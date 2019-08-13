package infrastructure.buildings.buildingTypes;

import cowParts.creation.Cow;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import metaControl.menus.MenuCreation;
import metaControl.menus.MenuHandler;
import metaControl.main.SimState;
import metaControl.metaEnvironment.AssetLoading;
import org.jetbrains.annotations.NotNull;
import resourcesManagement.ResourceRequirement;
import resourcesManagement.ResourcesHandler;
import infrastructure.terrain.Tile;
import infrastructure.terrain.TileHandler;
import societyProduction.government.GovernmentHandler;
import societyProduction.urbanPlanning.zoning.ZoningHandler;

import java.util.ArrayList;

public class AgriculturalBuilding extends GenericBuilding {

    public AgriculturalBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        constructBuilding(buildingSprite, buildingName, tileToBuildOn);

        if (GovernmentHandler.checkJurisdiction(this)) {
            ZoningHandler.zoneBuilding(this);
        }
    }

    @Override
    void constructBuilding(Image buildingSprite, String buildingName, @NotNull Tile tileToBuildOn) {
        int tileSize = TileHandler.getSize(buildingSprite);

        if (tileToBuildOn.tieToObject(this, tileSize)) {
            this.setImage((tileSize < 4) ? AssetLoading.smallUnderConstructionSprite : AssetLoading.largeUnderConstructionSprite);
            this.setId(buildingName);
            this.buildingSprite = buildingSprite;

            this.region = tileToBuildOn.getRegion();
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
        this.region.removeFromConstructionQueue(this);
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
        if (this.inhabitantsMenu != null) {
            MenuHandler.closeMenu(this.inhabitantsMenu);
            inhabitantsMenu = null;
        }
        else if (this.isConstructed)
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
